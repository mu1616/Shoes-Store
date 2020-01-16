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
	
	public MemberDTO login(MemberDTO memberDto) {
		MemberDTO memberDto_select = memberDao.findById(memberDto.getMem_id());
		if (memberDto_select == null) {
			return null;
		} 
		if (!encoder.matches(memberDto.getMem_pw(),memberDto_select.getMem_pw())) {
			return null;
		}
		return memberDto_select;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username);
		MemberDTO memberDto = memberDao.findById(username);
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_"+memberDto.getMem_role()));
		System.out.println(memberDto);
		System.out.println(new User(memberDto.getMem_id(),memberDto.getMem_pw(),authorities));
		return new User(memberDto.getMem_id(),memberDto.getMem_pw(),authorities);
	}
}
