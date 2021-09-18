package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoIdInput {
	
	@ApiModelProperty(example = "1", required = true) /* Biblioteca BeanValidatorPluginsConfiguration.class importada em SpringFoxConfig não pega o required automaticamente do Bean Validator, então por isso ativamos o required aqui na anotação do Api Model Property para a documentação ficar correta */
	@NotNull
	private Long id;
}
