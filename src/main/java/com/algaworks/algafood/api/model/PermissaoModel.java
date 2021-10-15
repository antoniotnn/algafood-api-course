package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Relation(collectionRelation = "permissoes")
public class PermissaoModel extends RepresentationModel<PermissaoModel> {
	
	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "CONSULTAR_COZINHAS")
	private String nome;

	@ApiModelProperty(example = "Permite consultar cozinhas")
	private String descricao;
	
}
