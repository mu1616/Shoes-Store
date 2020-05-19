package com.example.project_01.service.mail;

import javax.mail.Address;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	@Autowired
	JavaMailSender mailSender;
	public void sendMail(String to, String subject, String text) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
		message.addRecipient(RecipientType.TO, new InternetAddress(to));
		message.setSubject(subject);
		message.setText(text, "UTF-8", "html");
		mailSender.send(message);	
		}catch(MessagingException e) {
			e.printStackTrace();
		}
	}
	
}
