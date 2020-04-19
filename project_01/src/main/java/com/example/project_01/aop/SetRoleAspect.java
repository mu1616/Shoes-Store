package com.example.project_01.aop;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.project_01.model.member.dao.MemberDAO;
import com.example.project_01.model.member.dto.MemberDTO;
import com.example.project_01.model.member.dto.RoleDTO;

@Aspect
@Component
public class SetRoleAspect {
	@Autowired
	MemberDAO memberDao;

	// 현재 Role에서 총 구매가격에 상응하는 Role로 변경, 반드시 첫번째 인자로 MemberDTO가 와야함!!
	@Around("execution(* com.example.project_01..OrderService.insertOrder(..)) || "
			+ "execution(* com.example.project_01..OrderService.orderCancel(..)) || "
			+ "execution(* com.example.project_01..ManageMemberService.depriveAdmin(..))")
	public Object updateRole(ProceedingJoinPoint joinPoint) throws Throwable {
		// before
		Object result = joinPoint.proceed();
		// after
		Object[] args = joinPoint.getArgs();
		MemberDTO memberDto = (MemberDTO) args[0]; // 중요!
		memberDto = memberDao.findById(memberDto.getMem_id());
		if (memberDto.getMem_role().equals("ADMIN"))
			return result;
		List<RoleDTO> roleList = memberDao.selectRole();
		int role = 0;
		for (int i = 0; i < roleList.size(); i++) {
			if (roleList.get(i).getRole_name().equals(memberDto.getMem_role())) {
				role = i;
			}
		}
		setRole(memberDto, roleList, role);
		return result;
	}

	public void setRole(MemberDTO memberDto, List<RoleDTO> roleList, int role) {
		RoleDTO nextRole = null;
		RoleDTO beforeRole = null;
		// role_idx == 1:ADMIN, 2:USER -> ... ->n ADMIN을 제외하고 숫자 올라갈수록 등급 높음
		// Role down
		for (int i = role; i > 1; i--) {
			RoleDTO currentRole = roleList.get(i);
			if (memberDto.getMem_total() < currentRole.getRole_price()) {
				role--;
			} else {
				break;
			}
		}
		// Role up
		for (int i = role + 1; i < roleList.size(); i++) {
			nextRole = roleList.get(i);
			if (memberDto.getMem_total() >= nextRole.getRole_price()) {
				role++;
			} else {
				break;
			}
		}
		RoleDTO myRole = roleList.get(role);
		memberDao.updateRole(memberDto.getMem_idx(), myRole.getRole_idx());
	}
}
