package com.backend.stayEasy.sevice.impl;


import com.backend.stayEasy.entity.Mail;

public interface IMailService {
	void sendBook(Mail mail);
	void sendCancel(Mail mail);
}
