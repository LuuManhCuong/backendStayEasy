package com.backend.stayEasy.sevice;

import com.backend.stayEasy.dto.BookingDTO;
import com.backend.stayEasy.entity.Mail;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

<<<<<<< HEAD
import java.nio.charset.StandardCharsets;
=======
import com.backend.stayEasy.entity.Mail;
import com.backend.stayEasy.sevice.impl.IMailService;

import jakarta.mail.internet.MimeMessage;
>>>>>>> origin/namhh-update-infor

@Service
public class MailService implements IMailService {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private TemplateEngine templateEngine;

	@Override
	public void sendBook(Mail mail) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
		Context context = new Context();
		context.setVariable("username", "An");
		String htmlContent = templateEngine.process("email_book", context);
		try {
			helper.setText(htmlContent, true);
			helper.setTo(mail.getRecipient());
			helper.setSubject(mail.getSubject());
			mailSender.send(message);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Override
	public void sendCancel(Mail mail) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
		Context context = new Context();
		context.setVariable("username", "An");
		String htmlContent = templateEngine.process("email_cancel", context);
		try {
			helper.setText(htmlContent, true);
			helper.setTo(mail.getRecipient());
			helper.setSubject(mail.getSubject());
			mailSender.send(message);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Override
	public void sendEmailPayment(Mail mail, BookingDTO bookingDTO , String paymentLink)  {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
		Context context = new Context();
		context.setVariable("bookingDTO", bookingDTO);
		context.setVariable("paymentLink", paymentLink);
		String htmlContent = templateEngine.process("email-payment", context);
		try{
			helper.setText(htmlContent, true);
			helper.setTo(mail.getRecipient());
			helper.setSubject(mail.getSubject());
			mailSender.send(message);
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}

