package com.example.store;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JasyptTest {
	@Test
	public void jasyptTest() {
		StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
		jasypt.setPassword("");
		jasypt.setAlgorithm("PBEWithMD5AndDES");
		String encryptedText = jasypt.encrypt("");
		String plainText = jasypt.decrypt(encryptedText);	
		System.out.println(encryptedText);
		System.out.println(plainText);

	}
}
