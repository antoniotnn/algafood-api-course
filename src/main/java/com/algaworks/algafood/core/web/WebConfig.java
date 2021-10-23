package com.algaworks.algafood.core.web;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedMethods("*"); // padrões, os simples: "GET", "HEAD", "POST", se não especificado
//			.allowedOrigins("http://localhost:8000") , padrão é *
//			.maxAge(30);
		
	}
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(AlgaMediaTypes.V2_APPLICATION_JSON);
	}
	
	@Bean
	public Filter shallowEtagHeaderFilter() {  // gerador de ETag 
		return new ShallowEtagHeaderFilter();
	}
	
}
