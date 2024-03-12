package com.backend.stayEasy.api;

import com.backend.stayEasy.convertor.BookingConverter;
import com.backend.stayEasy.dto.BookingDTO;
import com.backend.stayEasy.dto.PaymentDTO;
import com.backend.stayEasy.entity.Mail;
import com.backend.stayEasy.sevice.BookingService;
import com.backend.stayEasy.sevice.PaymentBillService;
import com.backend.stayEasy.sevice.PaypalService;
import com.backend.stayEasy.sevice.impl.IMailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/stayeasy/booking")
public class BookingAPI {
    public static final String SUCCESS_URL = "/paypal/success";
    public static final String CANCEL_URL = "/paypal/cancel";
    @Autowired
    PaypalService service;
    @Autowired
    PaymentBillService paymentService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private IMailService mailService;
    @Autowired
    private BookingConverter bookingConverter;
    private UUID bookingId;
    private boolean emailSent = false;

    // chi admin moi xem duoc
    @GetMapping(value = "")
    public ResponseEntity<List<BookingDTO>> returnMyActiveBookings() {
        return ResponseEntity.ok().body(bookingService.findAll());
    }

    // get danh sach booking user
    @GetMapping("/traveler/{id}")
    public ResponseEntity<List<BookingDTO>> getBookingById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok().body(bookingService.returnMyBookings(id));
    }

    // get danh sach booking cho tung listing
    @GetMapping("/listing/{id}")
    public ResponseEntity<List<BookingDTO>> getBookingOfListing(@PathVariable("id") UUID id) {
        return ResponseEntity.ok().body(bookingService.returnListingBookings(id));
    }
    /*  @PutMapping("/changer-booking/{id}")
        public Booking updateBooking(@PathVariable("id") UUID id, @RequestBody Booking booking) {
           return bookingService.updateBooking(id, booking);
       } */


    // huy booking (check ngay truoc checkin 1 ngay  , return payment ,them vao bang paymnet bill , cap nhat trang thai vot  booking)
    // refund payment (lay stk cua user da thanh toan va refund)
    @DeleteMapping("/traveler-cancel/{id}")
    public void deleteBooking(@PathVariable("id") UUID id) {
        bookingService.deleteBooking(id);
    }

    // create payment and update booking (check id  user isn't host )
    @PostMapping("/create")
    public ResponseEntity<String> createBooking(@RequestBody BookingDTO bookingParam) throws JsonProcessingException {
        if (bookingService.isRoomAvailable(bookingParam.getPropertyId(), bookingParam.getCheckIn(), bookingParam.getCheckOut())) {
            BookingDTO bookingDTO = bookingService.newBooking(bookingParam);
            // Lưu booking vào cơ sở dữ liệu
            BookingDTO savedBooking = bookingService.crateBooking(bookingConverter.toEntity(bookingDTO)); //  phương thức saveBooking
            if (savedBooking != null) {
                bookingId = savedBooking.getBookingId();
                // Chuyển hướng đến PayPal để thanh town
                String approvalUrl = payment(bookingParam);
                System.out.println(approvalUrl);
                Map<String, String> response = new HashMap<>();
                response.put("approvalUrl", approvalUrl);
                return ResponseEntity.ok(new ObjectMapper().writeValueAsString(response));
            } else {
                // Nếu không thể lưu booking vào cơ sở dữ liệu
                return new ResponseEntity<>("Không thể lưu booking vào cơ sở dữ liệu", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Phòng chưa thể đặt trong thời gian này", HttpStatus.BAD_REQUEST);
        }
    }

    // payment with paypal
    public String payment(BookingDTO bookingDTO) {
        try {
            Payment payment = service.createPayment(bookingDTO.getPrice(), bookingDTO.getCurrency(), bookingDTO.getIntent(), bookingDTO.getMethod(), bookingDTO.getDescription(), "http://localhost:3000/payment" + CANCEL_URL, "http://localhost:3000/payment" + SUCCESS_URL);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    System.out.println("link" + link.getRel());
                    return link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "";
    }

    // return success payment
    @GetMapping(value = SUCCESS_URL)
    public ResponseEntity<List<PaymentDTO>> successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId)  {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            String data = payment.toJSON();
            if ("approved".equals(payment.getState())) {
                // tranh null exception
                bookingService.updateBookingStatus(bookingId, true);
                paymentService.savePayment(payment, bookingId);
                System.out.println(data);
                // Lưu thông tin payment
                return ResponseEntity.ok().body(paymentService.findByPaymentId(paymentId));
            }

        } catch (PayPalRESTException e) {
            sendEmailBooking(); // Send email notification about the cancellation
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // return when cancel payment
    @GetMapping(value = CANCEL_URL)
    public ResponseEntity<String> cancelPay()  {
        if (!emailSent) {
            // Gửi email nhắc thanh toán
            sendEmailBooking();
            emailSent = true;
            String message = "Đã hủy thanh toán và gửi email nhắc thanh toán thành công.";
            return ResponseEntity.ok(message);
        } else {
            String message = "Email đã được gửi đi trước đó.";
            return ResponseEntity.ok(message);
        }
    }

    // can code in booking service
    private void sendEmailBooking() {
        BookingDTO bookingDTO = bookingService.getBookingById(bookingId);
        String paymentLinkTemplate = "http://localhost:3000/booking/%s?checkin=%s&checkout=%s&numGuest=%d";
        System.out.println(bookingDTO.getNumOfGuest());
        String paymentLink = String.format(paymentLinkTemplate, bookingDTO.getPropertyDTOS().getPropertyId(), bookingDTO.getCheckIn(), bookingDTO.getCheckOut(), bookingDTO.getNumOfGuest());
        String subject = "Nhắc nhở thanh toán đặt phòng của bạn tại " + bookingDTO.getPropertyName();
        String content = "test mail";
        // set new mail
        Mail mail = new Mail();
        mail.setRecipient(bookingDTO.getUserDTOS().getEmail());
        mail.setSubject(subject);
        mail.setContent(content);
        mailService.sendEmailPayment(mail, bookingDTO, paymentLink);
//        mailService.sendBook(mail);
    }

    // get transaction detail
    @GetMapping("/lookup-transaction")
    public ResponseEntity<List<PaymentDTO>> LookupTrans(@RequestParam("paymentId") String paymentId) {
        System.out.println("running");
        return ResponseEntity.ok().body(paymentService.findByPaymentId(paymentId));
    }

    @GetMapping("/payment/captures/{capture_id}")
    public ResponseEntity<String> lookupCaptures(@PathVariable("capture_id") String captureId) {
        // Ideally, fetch your token from a secure place
        String response;
        HttpURLConnection httpConn = null;
        System.out.println("Start");
        try {
            URL url = new URL("https://api-m.sandbox.paypal.com/v2/payments/captures/" + captureId);

            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setRequestProperty("Authorization", "Bearer A21AAI6vLeTkQlNKRhgVxYk_0FPurpCC58Ed7RmX2Xmf7JBH-lU2H1ZLsLrdNaw6XdrojnRnozQgrlUIPm7DRkbzXiQ0LJJEQ");
            System.out.println(url);
            try (InputStream responseStream = httpConn.getResponseCode() / 100 == 2 ? httpConn.getInputStream() : httpConn.getErrorStream();
                 Scanner scanner = new Scanner(responseStream).useDelimiter("\\A")) {
                System.out.println(scanner.next());
                response = scanner.hasNext() ? scanner.next() : "";
            }
        } catch (IOException e) {
            System.out.println("Lỗi rồi");
            return ResponseEntity.internalServerError().body("An error occurred while making the request: " + e.getMessage());
        } finally {
            if (httpConn != null) {
                System.out.println("End");
                httpConn.disconnect();
            }
        }

        return ResponseEntity.ok(response);
    }
    // Method payment host when traveler checkout ( khi user checkout thi AUTO PAYMENT  cho host theo stk đã dky trong user account )
    @PostMapping("/payment/{capture_id}/refund")
    public ResponseEntity<String> refundTransaction(@PathVariable("capture_id") String captureId) throws IOException {
        URL url = new URL("https://api-m.sandbox.paypal.com/v2/payments/captures/" + captureId + "/refund");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("Content-Type", "application/json");
        httpConn.setRequestProperty("Authorization", "Bearer A21AAI6vLeTkQlNKRhgVxYk_0FPurpCC58Ed7RmX2Xmf7JBH-lU2H1ZLsLrdNaw6XdrojnRnozQgrlUIPm7DRkbzXiQ0LJJEQ");

        // Set optional headers if needed
        httpConn.setRequestProperty("PayPal-Request-Id", "7dc122d5-7da4-4627-a8ee-a7ae5b65acef");
        httpConn.setRequestProperty("Prefer", "return=representation");

        httpConn.setDoOutput(true);

        // Construct the request body for the refund
        String requestBody = "{"
                + "\"amount\": { \"value\": \"9.00\", \"currency_code\": \"USD\" },"
                + "\"invoice_id\": \"INVOICE-123\","
                + "\"note_to_payer\": \"DefectiveProduct\""
                + "}";

        try (OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream())) {
            writer.write(requestBody);
        }

        // Handle response
        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();

        try (Scanner scanner = new Scanner(responseStream).useDelimiter("\\A")) {
            String response = scanner.hasNext() ? scanner.next() : "";
            System.out.println(response);
            return ResponseEntity.ok(response);
        } finally {
            httpConn.disconnect();
        }
    }
}






