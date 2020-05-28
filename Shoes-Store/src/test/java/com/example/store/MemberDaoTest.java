package com.example.store;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.store.model.member.dao.MemberDAO;
import com.example.store.model.member.dto.MemberDTO;
import com.example.store.model.member.dto.SearchMemberDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class MemberDaoTest {
	
	@Autowired
	MemberDAO memberDao;
	
	void contextLoads() {
		System.out.println(memberDao.findById("asdf"));
	}
	
	public void makeMember() {
		int a = 0;
		int b = 1111;
		
		for(int i = 0; i < 200; i++) {
			System.out.println("INSERT INTO `shoesfactory`.`member` (`mem_name`, `mem_birth`, `mem_id`, `mem_pw`, `mem_phone`, `mem_addr1`, `mem_addr2`, `mem_regdate`) VALUES ('테스트', '1997-08-16', '"+a+"', '$2a$10$h9zb76xiKsEABDya36aD2.nPAlPxCrr6HY.5bcsJpHLCXWmQGhaPe', '0100000"+b+"', '제주특별자치도 서귀포시 동홍동 355-1', '506호', '2020-01-18 03:18:32');");
			a++;
			b++;
		}
	}

	public void selectMember() {
		SearchMemberDTO dto = new SearchMemberDTO();
		List<MemberDTO> list = memberDao.selectMember(1,10,dto);
		for(MemberDTO mdto: list) {
			System.out.println(mdto);
		}
	}
	
	public void countMember() {
		SearchMemberDTO dto = new SearchMemberDTO();
		System.out.println(memberDao.countMember(dto));
	}
	@Test
	public void selectAuthMailInfo() {
		HashMap<String, String> map = memberDao.selectAuthMailInfo("mu16161");
		System.out.println(map);
	}
}
