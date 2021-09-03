package com.algaworks.algafood.domain.service;

import java.util.Set;

import org.springframework.stereotype.Service;

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
		
	}
}
