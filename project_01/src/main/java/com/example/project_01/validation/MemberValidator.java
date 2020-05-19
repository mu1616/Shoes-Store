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
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return MemberDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
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

}
