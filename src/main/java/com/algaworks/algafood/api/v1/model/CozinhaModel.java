package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Relation(collectionRelation = "cozinhas")
public class CozinhaModel extends RepresentationModel<CozinhaModel>{
	
//	@JsonView(RestauranteView.Resumo.class)
	@ApiModelProperty(example = "1")
	private Long id;
	
//	@JsonView(RestauranteView.Resumo.class)
	@ApiModelProperty(example = "Brasileira")
	private String nome;
	
}
