package com.example.project_01.controller.member;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project_01.model.member.dao.MemberDAO;
import com.example.project_01.model.member.dto.MemberDTO;
import com.example.project_01.model.member.dto.RoleDTO;
import com.example.project_01.service.member.MemberService;
import com.example.project_01.validation.MemberValidator;

@Controller
public class MemberController {
	@Autowired
	MemberDAO memberDao;
	@Autowired
	BCryptPasswordEncoder encoder;
	@Autowired
	MemberService memberService;
	
	//로그인 페이지
	@RequestMapping(value = "/member/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		// url에 주소를 쳐서 요청하는 경우 referer가 안생기고
		// 페이지 내에서 클릭을 통해서(혹은 js를 통해) 이동하는 경우 referer가 생긴다
		String referer = request.getHeader("Referer");
		request.getSession().setAttribute("prevPage", referer);
		return "member/login";
	}
	
	//로그인 실패시 처리
	@RequestMapping("member/login/fail")
	public String loginFail(Model model) {
		model.addAttribute("loginFail", "로그인에 실패하였습니다.");
		return "member/login";
	}
	
	//로그아웃 
	@RequestMapping("/member/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "home";
	}
	
	//회원가입 페이지
	@RequestMapping(value = "/member/join", method = RequestMethod.GET)
	public String join() {
		return "member/join";
	}
	
	//회원가입 완료시 처리
	@RequestMapping(value = "/member/join", method = RequestMethod.POST)
	public String joinOk(@ModelAttribute MemberDTO memberDto, Errors errors, BindingResult bindingResult)
			throws BindException {
		new MemberValidator().validate(memberDto, errors);
		if (errors.hasErrors()) {   //데이터 유효성 검증
			throw new BindException(bindingResult);
		}
		memberDto.setMem_pw(encoder.encode(memberDto.getMem_pw()));
		//동일정보 (동일한 핸드폰번호, 동일한 아이디) 있을 시 예외 처리
		try {
			memberDao.join(memberDto);
		} catch (DataIntegrityViolationException e) {
			return "error/joinError";
		}

		return "member/welcome";
	}
	
	//아이디 중복검사
	@ResponseBody
	@RequestMapping("/member/join/idcheck")
	public int idCheck(String mem_id) {
		if (memberDao.findById(mem_id) == null) {
			return 0;
		} else {
			return 1;
		}
	}
	
	//회원등급 페이지
	@RequestMapping("/member/role/state")
	public String role(Principal principal, Model model) {
		String mem_id = principal.getName();
		MemberDTO memberDto = memberDao.findById(mem_id);
		List<RoleDTO> roleList = memberDao.selectRole();
		roleList.remove(0);
		model.addAttribute("memberDto",memberDto);
		model.addAttribute("roleList",roleList);
		return "member/role";
	}
	
	//회원정보수정 페이지 -> 패스워드 재입력 요청페이지
	@RequestMapping(value = "/member/info", method = RequestMethod.GET)
	public String checkMember() {
		return "/member/checkMember";
	}
	
	//회원정보수정 페이지
	@RequestMapping(value = "/member/info", method = RequestMethod.POST)
	public String memberInfo(Principal principal, String mem_pw, Model model) {
		String mem_id = principal.getName();
		//인증 성공 시
		if(memberService.checkPw(mem_id, mem_pw)) {
			MemberDTO memberDto = memberDao.findById(mem_id);
			if(memberDto.getMem_mail()==null) memberDto.setMem_mail("등록된 이메일이 없습니다.");			
			model.addAttribute("memberDto",memberDto);
			return "/member/memberInfo";
		}
		//인증 실패 시
		return "/member/fail";
	}
	
	//이메일 변경,등록시 인증메일 전송
	@ResponseBody
	@RequestMapping("/member/info/authMail")
	public void authMail(Principal principal, String mail) {
		String mem_id = principal.getName();
		memberService.authMail(mem_id, mail);
	}
	
	//이메일 변경
	@ResponseBody
	@RequestMapping("/member/info/changeMail")
	public int changeMail(Principal principal, String auth_key) {
		String mem_id = principal.getName();
		//이메일 인증 성공 시
		if(memberService.changeMail(mem_id, auth_key)) {
			return 1;
		}else {  //이메일 인증 실패 시
			return 0;
		}
	}
	
	//회원정보 수정요청 시  처리
	@ResponseBody
	@RequestMapping("/member/info/modify")
	public void modify(Principal principal, @RequestBody MemberDTO memberDto, Errors errors, 
			BindingResult bindingResult) throws BindException {
			//데이터 유효성 검증
			MemberValidator validator = new MemberValidator();
			validator.regExpCheck("mem_phone", memberDto.getMem_phone(), validator.phoneRegExp, errors);
			validator.regExpCheck("mem_postcode", memberDto.getMem_postcode(), validator.postcodeRegExp, errors);
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mem_addr1", "required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mem_addr2", "required");
			if (errors.hasErrors()) { 
				throw new BindException(bindingResult);
			}
			//
			memberDao.updateMemberInfo(principal.getName(), memberDto.getMem_phone(), memberDto.getMem_postcode(), 
					memberDto.getMem_addr1(), memberDto.getMem_addr2());
	}
	
	//패스워드 변경요청 시 처리
	@ResponseBody
	@RequestMapping("/member/info/changePassword")
	public int changePassword(Principal principal, @RequestBody HashMap<String, String> map, Errors errors, 
			BindingResult bindingResult) throws BindException {
		String mem_id = principal.getName();
		if(!memberService.checkPw(mem_id, map.get("mem_pw"))) { //기존 비밀번호 확인
			return 0;
		}else {
			String mem_pw = map.get("newPassword");
			//데이터 유효성 검증
			MemberValidator validator = new MemberValidator();
			if (!validator.regExpCheck(mem_pw, validator.pwRegExp)) { 
				throw new BindException(bindingResult);
			}
			//
			memberDao.updatePassword(mem_id, encoder.encode(mem_pw));
			return 1;
		}
	}

}