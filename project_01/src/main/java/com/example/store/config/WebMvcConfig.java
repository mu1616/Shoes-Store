package com.example.store.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	//파일업로드 외부경로 매핑
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/shoesfactory/img/**")
        		.addResourceLocations("file:///C:/shoesfactory/img/"); 
        registry.addResourceHandler("/smarteditor/img/**")
                .addResourceLocations("file:///C:/smarteditor/img/"); 

    }
}