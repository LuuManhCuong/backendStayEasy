package com.backend.stayEasy.sevice;

import com.backend.stayEasy.convertor.PaymentConverter;
import com.backend.stayEasy.dto.PaymentDTO;
import com.backend.stayEasy.entity.Booking;
import com.backend.stayEasy.entity.PaymentBill;
import com.backend.stayEasy.repository.BookingRepository;
import com.backend.stayEasy.repository.PaymentRepository;
import com.paypal.api.payments.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentBillService {
    @Autowired
    private PaymentRepository PaymentRepo;
    @Autowired
    private BookingRepository bookingRepository;
    public PaymentDTO  savePayment (Payment paymentParams, UUID bookingId ) {
        Optional<Booking> booking ;
        booking = bookingRepository.findById(bookingId);
        System.out.println(booking.get().getProperty().getPropertyName());
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setMethod(paymentParams.getPayer().getPaymentMethod());
        paymentDTO.setStatus(paymentParams.getState());
        paymentDTO.setCreateTime(paymentParams.getCreateTime());
        paymentDTO.setAccountType(paymentParams.getPayer().getPayerInfo().getPayerId());
        paymentDTO.setAmount(Float.parseFloat(paymentParams.getTransactions().get(0).getAmount().getTotal())); // Assuming first transaction holds relevant amount
        paymentDTO.setPaymentId(paymentParams.getId());
        paymentDTO.setBookingId(booking.get().getBookingId());
        PaymentBill paymentBill = PaymentConverter.convert(paymentDTO);
        PaymentRepo.save(paymentBill);
        return  PaymentConverter.convertToDTO(paymentBill);
    }
    private PaymentDTO extractPaymentDetails(Payment paymentParams) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setMethod(paymentParams.getPayer().getPaymentMethod());
        paymentDTO.setStatus(paymentParams.getState());
        paymentDTO.setCreateTime(paymentParams.getCreateTime());
        paymentDTO.setAccountType(paymentParams.getPayer().getPayerInfo().getPayerId());
        paymentDTO.setAmount(Float.parseFloat(paymentParams.getTransactions().get(0).getAmount().getTotal())); // Assuming first transaction holds relevant amount
        paymentDTO.setPaymentId(paymentParams.getId());
        return paymentDTO;
    }

    private void logPaymentDetails(PaymentBill paymentBill) {
        // Implement logging mechanism here (e.g., using a logger)
        System.out.println("Payment details logged: " + paymentBill);
    }

    private void notifyBookingConfirmation(Booking booking) {
        // Implement notification logic here (e.g., email or SMS)
        System.out.println("Booking confirmation sent for booking: " + booking);
    }

}
