package com.example.project_01.service.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.project_01.model.member.dao.MemberDAO;
import com.example.project_01.model.member.dto.MemberDTO;

@Service
public class MemberService implements UserDetailsService{
	@Autowired
	MemberDAO memberDao;
	@Autowired
	BCryptPasswordEncoder encoder;
	

	public boolean checkPw(String mem_id, String mem_pw) {
		MemberDTO memberDto = memberDao.findById(mem_id);
		if (memberDto == null) {
			return false;
		} 
		if (encoder.matches(mem_pw,memberDto.getMem_pw())) {
			return true;
		}
		return false;
	}
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberDTO memberDto = memberDao.findById(username);
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_"+memberDto.getMem_role()));
		return new User(memberDto.getMem_id(),memberDto.getMem_pw(),authorities);
	}
}
