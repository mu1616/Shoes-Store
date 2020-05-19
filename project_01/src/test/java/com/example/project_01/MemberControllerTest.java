package com.example.project_01;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.project_01.controller.member.MemberController;
import com.example.project_01.model.member.dto.MemberDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {
	@Autowired
	private MockMvc mockMvc;

	
	public void joinTest() throws Exception {
		MemberDTO memberDto = new MemberDTO();
		memberDto.setMem_id("mu1616");
		mockMvc.perform(post("/member/join")
					.param("mem_addr1","주소")
					.param("mem_addr2","상세주소")
					.param("mem_postcode","16407")
					.param("mem_name","홍길동")
					.param("mem_birth","19970816")
					.param("mem_phone","01047156000")
					.param("mem_id","test")
					.param("mem_pw", "martial!!3!"))					
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(redirectedUrl("/member/welcome"))
				.andDo(print());
	}

}
