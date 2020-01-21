package com.example.project_01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/**","/static/**").permitAll()
                    .anyRequest().authenticated();
        		
        http.csrf().disable();
        http.formLogin()
        		.loginPage("/login")
        		.loginProcessingUrl("/loginProcess")
        		.defaultSuccessUrl("/")
        		.failureUrl("/login/fail")
        		.permitAll()
        		.and();
        http .logout()
        	 .logoutUrl("/logout") 
        	 .logoutSuccessUrl("/") 
        	 .invalidateHttpSession(true);
        
        http.exceptionHandling().accessDeniedPage("/access-denied");
        http
        .headers()
           .frameOptions()
              .sameOrigin();
        

    }
}
