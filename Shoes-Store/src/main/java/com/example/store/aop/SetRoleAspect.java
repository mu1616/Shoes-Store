package com.example.store.aop;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.store.model.member.dao.MemberDAO;
import com.example.store.model.member.dto.MemberDTO;
import com.example.store.model.member.dto.RoleDTO;

@Aspect
@Component
public class SetRoleAspect {
	@Autowired
	MemberDAO memberDao;

	// 현재 Role에서 총 구매가격에 상응하는 Role로 변경, 반드시 첫번째 인자로 MemberDTO가 와야함!!
	@Around("execution(* com.example.store..OrderService.insertOrder(..)) || "
			+ "execution(* com.example.store..OrderService.orderCancel(..)) || "
			+ "execution(* com.example.store..OrderService.cancelOne(..)) || "
			+ "execution(* com.example.store..ManageMemberService.depriveAdmin(..))")
	public Object updateRole(ProceedingJoinPoint joinPoint) throws Throwable {
		// before
		Object result = joinPoint.proceed();
		// after
		Object[] args = joinPoint.getArgs();
		MemberDTO memberDto = (MemberDTO) args[0]; // 중요!
		memberDto = memberDao.findById(memberDto.getMem_id());
		
		//어드민일 경우 변경 X
		if (memberDto.getMem_role().equals("ADMIN"))
			return result;
		
		memberDao.updateMemberRole(memberDto.getMem_id());	
		return result;
	}

}
