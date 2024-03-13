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

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentBillService {
	@Autowired
	private PaymentRepository PaymentRepo;
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private PaymentConverter paymentConverter;

	public PaymentDTO savePayment(Payment paymentParams, UUID bookingId) {
		// Nhận json tư Payment Paypal sau đó set dữ liệu trong database
		Optional<Booking> booking;
		booking = bookingRepository.findById(bookingId);
		PaymentDTO paymentDTO = new PaymentDTO();
		paymentDTO.setMethod(paymentParams.getPayer().getPaymentMethod());
		paymentDTO.setStatus(paymentParams.getState());
		paymentDTO.setCreateTime(paymentParams.getCreateTime());
		paymentDTO.setAccountType(paymentParams.getPayer().getPayerInfo().getPayerId());
		paymentDTO.setAmount(Float.parseFloat(paymentParams.getTransactions().get(0).getAmount().getTotal()));
		// Assuming first transaction holds relevant amount
		paymentDTO.setPaymentId(paymentParams.getId());
		paymentDTO.setCapturesId(paymentParams.getTransactions().get(0).getRelatedResources().get(0).getSale().getId());
		paymentDTO.setRefundStatus("NO");
		paymentDTO.setBookingId(booking.get().getBookingId());
		PaymentBill paymentBill = paymentConverter.toEntity(paymentDTO);
		PaymentRepo.save(paymentBill);
		return paymentConverter.toDTO(paymentBill);
	}

	public List<PaymentDTO> findByPaymentId(String paymentId) {
		return PaymentRepo.findByPaymentId(paymentId).stream().map(paymentConverter::toDTO)
				.collect(Collectors.toList());
	}
	public void updateBillWhenRefund(UUID paymentId, String status) {
		Optional<PaymentBill> paymentBillOptional = PaymentRepo.findById(paymentId);
		if (paymentBillOptional.isEmpty()) {
			// Handle booking not found (e.g., throw an exception, log an error, etc.)
			throw new RuntimeException("Booking with ID " + paymentId + " not found.");
		}
		PaymentBill paymentBill = paymentBillOptional.get();
		if (!status.equals(paymentBill.getRefundStatus())) {
			paymentBill.setRefundStatus(status);
			PaymentRepo.save(paymentBill);
			// send Email
		}
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
