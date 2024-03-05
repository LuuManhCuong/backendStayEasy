package com.backend.stayEasy.api;

import com.backend.stayEasy.convertor.BookingConverter;

import com.backend.stayEasy.dto.BookingDTO;
import com.backend.stayEasy.dto.PaymentDTO;
import com.backend.stayEasy.repository.BookingRepository;
import com.backend.stayEasy.sevice.BookingService;
import com.backend.stayEasy.sevice.PaymentBillService;
import com.backend.stayEasy.sevice.PaypalService;
import com.backend.stayEasy.sevice.PropertyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/stayeasy/booking")
public class BookingAPI {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingConverter bookingConverter;
    @Autowired
    PaypalService service;
    @Autowired
    PaymentBillService paymentService;
    public static final String SUCCESS_URL = "/paypal/success";
    public static final String CANCEL_URL = "/paypal/cancel";
    private UUID bookingId;
    @GetMapping(value = "")
    public ResponseEntity<List<BookingDTO>> returnMyActiveBookings(){
        return ResponseEntity.ok().body(bookingService.findAll());
    }

    @GetMapping("/traveler/{id}")
    public ResponseEntity<List<BookingDTO>> getBookingById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok().body(bookingService.returnMyBookings(id));
    }
    @GetMapping("/listing/{id}")
    public ResponseEntity<List<BookingDTO>> getBookingOfListing(@PathVariable("id") UUID id) {
        return ResponseEntity.ok().body(bookingService.returnListingBookings(id));
    }
//    @PutMapping("/changer-booking/{id}")
//    public Booking updateBooking(@PathVariable("id") UUID id, @RequestBody Booking booking) {
//        return bookingService.updateBooking(id, booking);
//    }

    @DeleteMapping("/traveler-cancel/{id}")
    public void deleteBooking(@PathVariable("id") UUID id) {
        bookingService.deleteBooking(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createBooking(@RequestBody BookingDTO bookingParam) throws JsonProcessingException {

        if (bookingService.isRoomAvailable(bookingParam.getPropertyId(), bookingParam.getCheckIn(), bookingParam.getCheckOut())) {
            BookingDTO bookingDTO = bookingService.newBooking(bookingParam);
            // Lưu booking vào cơ sở dữ liệu
            BookingDTO savedBooking = bookingService.crateBooking(bookingConverter.toEntity(bookingDTO)); //  phương thức saveBooking
            if(savedBooking != null) {
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
    public String payment(BookingDTO bookingDTO) {
        try {
            Payment payment = service.createPayment(bookingDTO.getPrice(), bookingDTO.getCurrency(), bookingDTO.getIntent(),
                    bookingDTO.getMethod(), bookingDTO.getDescription(), "http://localhost:3000/payment" + CANCEL_URL,
                    "http://localhost:3000/payment" + SUCCESS_URL);
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

    @GetMapping(value = SUCCESS_URL)
    public  ResponseEntity <List<PaymentDTO>> successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) throws JsonProcessingException {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                // Update thong tin booking thanh true
                bookingService.updateBookingStatus(bookingId , true);
                paymentService.savePayment(payment, bookingId);
                // Lưu thông tin payment
                return ResponseEntity.ok().body(paymentService.findByPaymentId(paymentId));
            }
        } catch (PayPalRESTException e) {
            bookingService.deleteBooking(bookingId);
//            String message = " Thanh toán không thành công ! ";
//            return  ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(message));
            System.out.println(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
            }

    @GetMapping("/lookup-transaction")
    public ResponseEntity <List<PaymentDTO>>  LookupTrans (@RequestParam("paymentId") String paymentId) {
        System.out.println("running");
        return ResponseEntity.ok().body(paymentService.findByPaymentId(paymentId));
    }
}


