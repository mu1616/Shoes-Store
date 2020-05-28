package com.example.store.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RemoveReqCacheAspect {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	@Pointcut("execution(* com.example.store..MemberController.login(..))")
	public void notarget() {}
	//로그인 페이지를 누르지않고, 인증절차에 의해 걸러져서 로그인 페이지로 이동된 경우 savedRequest 생성되고
	//인증에 성공 했을 때 세이브된 request 페이지로 이동하게 되는데 이로인해
	//이후 로그인하지 않은 채로 다른 작업을 하다가  로그인을 하면 세이브된 곳으로 이동하게됨.
	//따라서 로그인 페이지에서 다른 페이지로 이동시에 세이브된 리퀘스트 제거
	@Around("execution(* com.example.store..controller..*(..)) && ! notarget()")
	public Object removeCache(ProceedingJoinPoint joinPoint) throws Throwable{	
		Object result = joinPoint.proceed();
		RequestCache reqCache = new HttpSessionRequestCache();		
		reqCache.removeRequest(request, response);
		return result;
	}
}
