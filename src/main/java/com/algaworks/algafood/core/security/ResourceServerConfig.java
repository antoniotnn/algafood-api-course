package com.algaworks.algafood.core.security;

import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration //opcional, quando usada a anotação EnableWebSecurity
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/v1/cozinhas/**").hasAuthority("EDITAR_COZINHAS")
				.antMatchers(HttpMethod.PUT, "/v1/cozinhas/**").hasAuthority("EDITAR_COZINHAS")
				.antMatchers(HttpMethod.GET, "/v1/cozinhas/**").authenticated()
				.anyRequest().denyAll()
//				.anyRequest().authenticated()
			.and()
			.cors().and()
//			.oauth2ResourceServer().opaqueToken();  para opaqueToken
			.oauth2ResourceServer()
				.jwt()    // para JWT
				.jwtAuthenticationConverter(jwtAuthenticationConverter());
	}
	
	private JwtAuthenticationConverter jwtAuthenticationConverter() {
		var jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
			var authorities = jwt.getClaimAsStringList("authorities");
			
			if (authorities == null) {
				authorities = Collections.emptyList();
			}
			
			return authorities.stream()
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
		});
		
		return jwtAuthenticationConverter;
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
