package com.algaworks.algafood.core.security;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration //opcional, quando usada a anotação EnableWebSecurity
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.anyRequest().authenticated()
			.and()
			.cors().and()
//			.oauth2ResourceServer().opaqueToken();  para opaqueToken
			.oauth2ResourceServer().jwt();    // para JWT
	}
	/*
	  
	  CHAVE SIMÉTRICA
	 
	@Bean
	public JwtDecoder jwtDecoder() {
		var secretKey = new SecretKeySpec("ladkfjkakflndafadjksfhkajshfjkah981y9138y918yjdashfklh19873".getBytes(), "HmacSHA256");  // se o secret for menor que 32bytes dará erro 401 em uma requisição no Resource Server. O log da aplicação não mostrará isso, somente se ativar o log por debbuging. Para evitar isso, usar uma chave com mais de 32bytes.
		
		return NimbusJwtDecoder.withSecretKey(secretKey).build();
	}
	
	*/
	
	
	
	
}
