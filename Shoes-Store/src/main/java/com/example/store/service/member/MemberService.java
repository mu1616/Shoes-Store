package com.example.store.service.member;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.transaction.annotation.Transactional;

import com.example.store.model.member.dao.MemberDAO;
import com.example.store.model.member.dto.MemberDTO;
import com.example.store.service.mail.MailService;

@Service
public class MemberService implements UserDetailsService{
	@Autowired
	MemberDAO memberDao;
	@Autowired
	BCryptPasswordEncoder encoder;
	@Autowired
	MailService mailService;
	//패스워드 인증
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
	
	//로그인 시 인증
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberDTO memberDto = memberDao.findById(username);
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_"+memberDto.getMem_role()));
		
		return new User(memberDto.getMem_id(),memberDto.getMem_pw(),authorities);
	}
	
	//이메일 인증 요청시, 메일전송, 인증정보 디비에 저장
	@Transactional
	public void authMail(String mem_id, String mail) {
		String auth_key = getRandomKey();
		HashMap<String, String> map = memberDao.selectAuthMailInfo(mem_id);
		
		//처음 인증 요청했을 때 
		if(map==null) {
			memberDao.insertAuthMailInfo(mem_id, mail, auth_key);
		}else { //이전에 인증 요청을 했었을 때 (DB에 정보 남아있음)
			memberDao.updateAuthMailInfo(mem_id, mail, auth_key);
			String to = mail;
			String subject = "[Shoes Factory] 이메일 인증요청";
			String text = "<h3>"+mem_id+"님</h3>" + "인증번호는 "+auth_key+"입니다.";
			mailService.sendMail(to, subject, text);
		}
	}
	
	//메일 변경
	@Transactional
	public boolean changeMail(String mem_id, String auth_key) {
		HashMap<String ,String> map = memberDao.selectAuthMailInfo(mem_id);
		if(!auth_key.equals(map.get("auth_key"))){ //인증 정보가 틀리다면
			return false;
		}else {  //인증정보가 맞다면
			memberDao.updateMemberMail(mem_id, map.get("mail"));
			return true;
		}
	}
	
	public String getRandomKey() {
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i< 8; i++) {
			int n = (int)(Math.random() *10);
			buffer.append(n);
		}
		return buffer.toString();
	}
}
