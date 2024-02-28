package com.backend.stayEasy.api;

import com.backend.stayEasy.dto.BookingDTO;
import com.backend.stayEasy.dto.Transaction;
import com.backend.stayEasy.sevice.BookingService;
import com.backend.stayEasy.sevice.PaymentBillService;
import com.backend.stayEasy.sevice.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
//@RequestMapping("/api/v1")
public class PaymentAPI {
    @Autowired
    PaypalService service;
    @Autowired
    BookingService bookingService;
    @Autowired
    PaymentBillService paymentService;
    private UUID bookingId;
    public static final String SUCCESS_URL = "paypal/success";
    public static final String CANCEL_URL = "paypal/cancel";
    @GetMapping("/paypal")
    public String home() {
        return "home";
    }
    @PostMapping("/{id}/paypal")
    public String payment(@RequestBody Transaction order, @PathVariable("id") UUID id ) throws Exception {
        bookingId = id;
        System.out.println(bookingId);
        if (bookingId != null) {
            BookingDTO booking =  bookingService.getBookingById(bookingId);
            order.setPrice(booking.getTotal());
            System.out.println(booking.getTotal().doubleValue() );
        }
        try {
            Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getIntent(),
                    order.getMethod(), order.getDescription(), "http://localhost:8080/api/v1/" + CANCEL_URL,
                    "http://localhost:8080/api/v1/" + SUCCESS_URL);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    System.out.println("link" + link.getRel());
                    return link.getHref();
                }
            }

        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
    @GetMapping(value = CANCEL_URL)
    @ResponseBody
    public  String cancelPay () {
        return  CANCEL_URL;
    }
    @GetMapping(value = SUCCESS_URL)
    @ResponseBody
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                String paymentParams = payment.toJSON();
                System.out.println(bookingId);
                System.out.println(payment.getState());
                System.out.println(payment.getCreateTime());
                paymentService.savePayment(payment, bookingId);
                bookingService.updateBookingStatus(bookingId , true);
                return paymentParams;
            } else {
                return payment.getFailedTransactions().toString();
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
