package com.algaworks.algafood.domain.service;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;


public interface EnvioEmailService {
	
	void enviar(Mensagem mensagem);
	
	@Getter
	@Builder
	class Mensagem {
		
		@Singular
		private Set<String> destinatarios;
		
		@NonNull // lombok anotation
		private String assunto;
		
		@NonNull //lombok anotation
		private String corpo;
		
		@Singular("variavel") //especificar o singular separadamente, pois o lombok não entende português
		private Map<String, Object> variaveis;
		
	}
}
