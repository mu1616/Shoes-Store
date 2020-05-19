package com.example.project_01.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.project_01.model.member.dto.MemberDTO;

public class MemberValidator implements Validator {
	public static final String idRegExp = "^[a-zA-Z0-9]{4,16}$";
	public static final String phoneRegExp = "^[0-9]{10,11}$";
	public static final String pwRegExp = "^(?=.*[0-9])(?=.*[~`!@#$%\\\\^&*()-])(?=.*[a-zA-Z]).{8,}$";
	public static final String postcodeRegExp = "^[0-9]{5}$";
	private Pattern pattern;
	
	//컨트롤러에서  error 객체는 반드시 @ModelAttribute, @Valid, @RequestBody 등 vo 뒤에 와야한다.
	//스프링MVC는 VO와 연결된 Errors 객체를 생성해서 파라미터로 전달한다.
	@Override
	public boolean supports(Class<?> clazz) {
		return MemberDTO.class.isAssignableFrom(clazz);  //MemberValidor는 MemberDTO만 검증 가능 
	}

	//회원가입시 유효성 검증
	@Override
	public void validate(Object target, Errors errors) {
		MemberDTO memberDto = (MemberDTO) target;
		regExpCheck("mem_id", memberDto.getMem_id(), idRegExp, errors);
		regExpCheck("mem_phone", memberDto.getMem_phone(), phoneRegExp, errors);
		regExpCheck("mem_pw", memberDto.getMem_pw(), pwRegExp, errors);
		regExpCheck("mem_postcode",memberDto.getMem_postcode(), postcodeRegExp, errors);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mem_addr1", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mem_addr2", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mem_birth", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mem_name", "required");
	}
	
	//error 객체를 이용하여 정규화 검사
	public void regExpCheck(String field, String value, String regExp, Errors errors) {
		if (value == null || value.trim().isEmpty()) {
			errors.rejectValue(field, "required");
		} else {
			pattern = Pattern.compile(regExp);
			Matcher matcher = pattern.matcher(value);
			if (!matcher.matches())
				errors.rejectValue(field, "bad");
		}
		
	}
	
	//true, false 리턴하는 정규화 검사
	public boolean regExpCheck(String value, String regExp) {
		if (value == null || value.trim().isEmpty()) {
			return false;
		} else {
			pattern = Pattern.compile(regExp);
			Matcher matcher = pattern.matcher(value);
			if (!matcher.matches())
				return false;
		}
		return true;
		
	}

}
