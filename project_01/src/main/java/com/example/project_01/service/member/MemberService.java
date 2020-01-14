package com.example.project_01.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.project_01.model.member.dao.MemberDAO;
import com.example.project_01.model.member.dto.MemberDTO;

@Service
public class MemberService {
	@Autowired
	MemberDAO memberDao;
	@Autowired
	BCryptPasswordEncoder encoder;
	
	public MemberDTO login(MemberDTO memberDto) {
		MemberDTO memberDto_select = memberDao.idCheck(memberDto.getMem_id());
		if (memberDto_select == null) {
			return null;
		} 
		if (!encoder.matches(memberDto.getMem_pw(),memberDto_select.getMem_pw())) {
			return null;
		}
		return memberDto_select;
	}
}
