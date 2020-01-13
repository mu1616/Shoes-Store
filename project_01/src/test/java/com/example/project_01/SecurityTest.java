package com.example.project_01;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class SecurityTest {
	
	@Autowired 
	PasswordEncoder encoder;
	
	@Test
	public void test() {
		String pw = "martial1!";
		System.out.println(encoder.encode(pw));
		System.out.println(encoder.matches(pw,"$2a$10$.d4mKziRfPjuSOTNxAtHj.xfTVsNS2dpvlkOn3HWfGDQUfvsFf1zK"));
	}
}
