package com.backend.stayEasy.sevice.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.backend.stayEasy.entity.Message;
import com.backend.stayEasy.entity.Notification;


public interface INotificationService {
	Notification saveNotification(Notification notification);
	List<Notification> getAllNotification();
	List<Notification> getAllNotificationForUser(String token);
	
}
