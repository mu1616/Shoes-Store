package com.example.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import com.example.store.security.LoginSuccessHandler;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() // antMatchers() -> 앞에꺼 우선 적용
				.antMatchers("/product/qna/qnaShow", "/product/review/show").permitAll()
				.antMatchers("/product/qna/**", "/cart/**", "/order/**", "/product/review/**", "/member/info/**").authenticated()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/**", "/static/**").permitAll()
				.anyRequest().authenticated();

		http.csrf().disable();
		
		http.formLogin()
				.loginPage("/member/login")
				.loginProcessingUrl("/loginProcess")
				.defaultSuccessUrl("/")
				.failureUrl("/member/login/fail")
				.successHandler(new LoginSuccessHandler("/"))
				.permitAll()
				.and();
		
		http.logout()
				.logoutUrl("/member/logout")
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true);

		// 로그인중이긴 한데(role은 있는데) 인가되지 않는 role 일때
		http.exceptionHandling().accessDeniedPage("/access-denied");
		http.headers().frameOptions().sameOrigin();

	}
}
