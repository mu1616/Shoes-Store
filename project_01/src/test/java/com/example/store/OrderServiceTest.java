package com.example.store;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.store.service.order.OrderService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderServiceTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	OrderService orderService;
	
	@Test
	public void test() {
		//orderService.paymentComplete();
	}
}
