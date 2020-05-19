package com.example.project_01;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.project_01.service.mail.MailService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MailServiceTest {
	@Autowired
	MailService mailService;
	@Test
	public void sendMail() {
		String to = "mu1616@naver.com";
		String subject = "mail test";
		String text = "testing....";
		mailService.sendMail(to, subject, text);
	}
}
