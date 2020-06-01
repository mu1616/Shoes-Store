package com.example.store;

import static org.assertj.core.api.Assertions.assertThat;

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
	

	void findById() {
		assertThat(memberDao.findById("admin").getMem_id()).isEqualTo("admin");
	}
	
	public void selectMember() {
		SearchMemberDTO dto = new SearchMemberDTO();
		List<MemberDTO> list = memberDao.selectMember(1,10,dto);
		
		assertThat(list.size()).isEqualTo(10);
	}
	

	
	@Test
	public void selectAuthMailInfo() {
		HashMap<String, String> map = memberDao.selectAuthMailInfo("mu1616");
		
		assertThat(map.get("mem_id")).isEqualTo("mu1616");
	}
}
