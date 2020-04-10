package com.example.project_01.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
//인증이 필요한 요청을 했을 때 로그인 창으로 이동했다가 원래 요청했던 페이지로 이동시켜주는 핸들러
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private RequestCache reqCache = new HttpSessionRequestCache();


	public LoginSuccessHandler(String defaultTargetUrl) {
		setDefaultTargetUrl(defaultTargetUrl);
	}
	

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		SavedRequest savedRequest = reqCache.getRequest(request, response);
		//로그인 페이지를 누르지않고, 인증절차에 의해 걸러져서 로그인 페이지로 이동된 경우 savedRequest 생성
		/*
		if(savedRequest!=null)
			System.out.println(savedRequest.getRedirectUrl());
		*/
		if(savedRequest!=null) {
			super.onAuthenticationSuccess(request, response, authentication); //원래 요청한 페이지로 이동
			return;
		}
		//인증 성공 시, refere에 저장된 url로 이동시키게함
		//referer가 null일 경우(주소창을 통한 입력) 원래 요청했던 페이지로 이동
		if(session !=null) {
			String redirectUrl = (String) session.getAttribute("prevPage");
			if(redirectUrl != null && !redirectUrl.contains("member/")) {				
				session.removeAttribute("prevPage");
				getRedirectStrategy().sendRedirect(request, response, redirectUrl);
			} else {
				super.onAuthenticationSuccess(request, response, authentication);
			}
		} else {
			super.onAuthenticationSuccess(request, response, authentication);
		}
		
	}

}


