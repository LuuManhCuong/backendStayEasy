package com.backend.stayEasy.convertor;


import com.backend.stayEasy.dto.PaymentDTO;
import com.backend.stayEasy.entity.PaymentBill;
import com.backend.stayEasy.sevice.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentConverter {
    @Autowired
    private BookingService bookingService;
    private static  BookingService bookingServiceStatic;
    @Autowired
    public void setStatic(){
        bookingServiceStatic=bookingService;
    }
    public static PaymentDTO convertToDTO (PaymentBill paymentBill){
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentBillId(paymentBill.getPaymentBillId());
        paymentDTO.setAmount(paymentBill.getAmount());
        paymentDTO.setMethod(paymentBill.getMethod());
        paymentDTO.setBookingId(paymentBill.getBooking().getBookingId());
        paymentDTO.setAccountType(paymentBill.getAccountType());
        paymentDTO.setCreateTime(paymentBill.getCreateTime());
        paymentDTO.setPaymentId(paymentBill.getPaymentId());
        paymentDTO.setStatus(paymentBill.getStatus());
        return paymentDTO;
    }
    public static PaymentBill convert (PaymentDTO paymentDTO) {
        PaymentBill paymentBill = new PaymentBill();
        paymentBill.setPaymentBillId(paymentDTO.getPaymentBillId());
        paymentBill.setAmount(paymentDTO.getAmount());
        paymentBill.setMethod(paymentDTO.getMethod());
        paymentBill.setBooking(bookingServiceStatic.findById(paymentDTO.getBookingId()));
        paymentBill.setAccountType(paymentDTO.getAccountType());
        paymentBill.setCreateTime(paymentDTO.getCreateTime());
        paymentBill.setPaymentId(paymentDTO.getPaymentId());
        paymentBill.setStatus(paymentDTO.getStatus());
        return paymentBill;
    }
}
