package com.backend.stayEasy.api;

import com.backend.stayEasy.dto.PayoutDTO;
import com.backend.stayEasy.dto.RefundDTO;
import com.backend.stayEasy.sevice.BookingService;
import com.backend.stayEasy.sevice.PaymentBillService;
import com.backend.stayEasy.sevice.PaypalService;
import com.backend.stayEasy.sevice.impl.IMailService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/stayeasy/payment")
public class PaymentAPI {
    @Autowired
    PaypalService service;
    @Autowired
    PaymentBillService paymentService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private IMailService mailService;
    @Autowired PaypalService paypalService;
    private UUID bookingId;
    private boolean emailSent = false;

    // Method payment host when traveler checkout ( khi user checkout thi AUTO PAYMENT  cho host theo stk đã dky trong user account )
    @GetMapping("/captures/{capture_id}")
    public ResponseEntity<Object> lookupCaptures(@PathVariable("capture_id") String captureId) {
        // Ideally, fetch your token from a secure place
        String response;
        String Authorization = "Bearer " + paypalService.getAccessToken();
        HttpURLConnection httpConn = null;
        try {
            URL url = new URL("https://api-m.sandbox.paypal.com/v2/payments/captures/" + captureId);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setRequestProperty("Authorization", Authorization);
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
        return ResponseEntity.ok().body(response);
    }

    // Method payment host when traveler checkout ( khi user checkout thi AUTO PAYMENT  cho host theo stk đã dky trong user account )
    @PostMapping("/refund")
    public ResponseEntity<Map<String, String>> refundTransaction(@RequestBody RefundDTO refundRequest) throws IOException {
        RefundDTO refundDTO = paymentService.createBillToRefund(refundRequest);
        String Authorization = "Bearer " + paypalService.getAccessToken();
        System.out.println(Authorization);
        Map<String, String> responser = new HashMap<>();
        JsonObject jsonObject;
        URL url = new URL("https://api-m.sandbox.paypal.com/v2/payments/captures/" + refundDTO.getCaptureId() + "/refund");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("Content-Type", "application/json");
        httpConn.setRequestProperty("Authorization", Authorization);
        // Set optional headers if needed
        httpConn.setRequestProperty("PayPal-Request-Id", refundDTO.getPaypalRequestId().toString());
        httpConn.setRequestProperty("Prefer", "return=representation");
        httpConn.setDoOutput(true);
        // Construct the request body for the refund
        String requestBody = "{"
                + "\"amount\": { \"value\": \"" + refundDTO.getRefundAmount() + "\", \"currency_code\": \"" + refundDTO.getCurrencyCode() + "\" },"
                + "\"invoice_id\": \"" + refundDTO.getInvoiceId() + "\","
                + "\"note_to_payer\": \"" + refundDTO.getNoteToPayer() + "\""
                + "}";
        System.out.println(requestBody);
        // Lấy một luồng đầu ra từ kết nối HTTP để ghi dữ liệu yêu cầu lên máy chủ
        try (OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream())) {
            writer.write(requestBody);
        }
        int responseCode = httpConn.getResponseCode();
        System.out.println(responseCode);
        // Handle response
        //  Lấy mã trạng thái HTTP của phản hồi từ máy chủ (coi có nằm ngoài phạm vi 2xx không)
        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        // delimiter để đọc toàn bộ nội dung của luồng.
        try (Scanner scanner = new Scanner(responseStream).useDelimiter("\\A")) {
            String response = scanner.hasNext() ? scanner.next() : "";
            Gson gson = new Gson();
            jsonObject = gson.fromJson(response, JsonObject.class);
            System.out.println(jsonObject);
            if ( jsonObject.has("status")) {
                String status = jsonObject.get("status").getAsString();
                if (status.equals("COMPLETED")) {
                    bookingService.updateBookingCancel(refundDTO.getInvoiceId(),true, false);
                    System.out.println("Cập nhật refund in payment bill");
                    responser.put("message", "Hủy booking thành công");
                    return ResponseEntity.ok().body(responser);
                }

                paymentService.updateBillWhenRefund(refundDTO.getPaypalRequestId(), status);
            }
//
        } finally {
            httpConn.disconnect();
        }
        return ResponseEntity.ok().body(responser);
    }

    @PostMapping("/performPayout")
    public ResponseEntity<Object> performPayout(@RequestBody PayoutDTO payoutDTO) {
        String Authorization = "Bearer " + paypalService.getAccessToken();
        System.out.println(Authorization);
        HttpURLConnection httpConn = null;
        try {
            URL url = new URL("https://api-m.sandbox.paypal.com/v1/payments/payouts");
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setRequestProperty("Authorization", Authorization);
            httpConn.setDoOutput(true);

            OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
            writer.write("{ \"sender_batch_header\":" +
                    " { \"sender_batch_id\": \"" + payoutDTO.getSender_batch_id() + "\"," +
                    " \"email_subject\": \"" + payoutDTO.getEmail_subject() + "\", " +
                    "\"email_message\": \"" + payoutDTO.getEmail_message() + "\" }, " +
                    "\"items\": [ { \"recipient_type\": \"EMAIL\", \"amount\": { \"value\": \"" + payoutDTO.getAmount() + "\", \"currency\": \"USD\" }, " +
                    "\"note\": \"Thanks for your patronage!\", \"sender_item_id\": \"" + payoutDTO.getSender_item_id() + "\"," +
                    " \"receiver\": \"" + payoutDTO.getReceiver() + "\", " +
                    "\"recipient_wallet\": \"PAYPAL\" } ] }");
            writer.flush();
            writer.close();

            // Kiểm tra mã trạng thái HTTP của phản hồi
            int responseCode = httpConn.getResponseCode();
            if (responseCode >= 200 && responseCode < 300) {
                // Nếu mã trạng thái là 2xx (thành công), đọc và trả về phản hồi
                InputStream responseStream = httpConn.getInputStream();
                try (Scanner scanner = new Scanner(responseStream).useDelimiter("\\A")) {
                    String response = scanner.hasNext() ? scanner.next() : "";
                    System.out.println(response);
                    return ResponseEntity.ok().body(response);
                }
            } else {
                // Nếu mã trạng thái là lỗi, đọc phản hồi lỗi từ PayPal và xử lý
                InputStream errorStream = httpConn.getErrorStream();
                try (Scanner scanner = new Scanner(errorStream).useDelimiter("\\A")) {
                    String errorMessage = scanner.hasNext() ? scanner.next() : "Unknown error";
                    System.out.println(errorMessage);
                    // Xử lý các lỗi cụ thể
                    switch (responseCode) {
                        case 400:
                            // Xử lý lỗi 400
                            return ResponseEntity.badRequest().body(errorMessage);
                        case 401:
                            // Xử lý lỗi 401
                            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
                        // Thêm các case xử lý lỗi khác nếu cần
                        default:
                            // Mặc định, trả về lỗi 500
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("An error occurred while performing the payout: " + e.getMessage());
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
    }


    // Hàm kiểm tra ngày hủy thanh toán


}
