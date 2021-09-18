package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {
	
	@ApiModelProperty(example = "Uberlândia", required = true) /* Biblioteca BeanValidatorPluginsConfiguration.class importada em SpringFoxConfig não pega o required automaticamente do Bean Validator, então por isso ativamos o required aqui na anotação do Api Model Property para a documentação ficar correta */
	@NotBlank
	private String nome;
	
	//Aqui a anotação acima com o comentário do lado não é necessária pois o SpringFox pega o required do Bean Validation e atribui a documentação
	@Valid
	@NotNull 
	private EstadoIdInput estado;
	
}
