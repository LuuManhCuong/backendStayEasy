package com.backend.stayEasy.sevice.impl;

import com.backend.stayEasy.dto.BookingDTO;
import com.backend.stayEasy.entity.Mail;

public interface IMailService {
	void sendBook(Mail mail);

	void sendCancel(Mail mail);

	void sendEmailPayment(Mail mail, BookingDTO bookingDTO, String paymentLink);

	void sendEmailVerify(Mail mail, String Code);
}
