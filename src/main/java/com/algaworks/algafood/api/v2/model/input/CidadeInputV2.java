package com.algaworks.algafood.api.v2.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInputV2 {
	
	@ApiModelProperty(example = "Uberlândia", required = true) 
	private String nomeCidade;
	
	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long idEstado;
	
}
