package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


//@ApiModel(value = "CidadeModel" ,description = "Representa uma cidade")
@Setter
@Getter
@Relation(collectionRelation = "cidades")
public class CidadeModel extends RepresentationModel<CidadeModel> {
	
//	@ApiModelProperty(value = "ID da cidade", example = "1")
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Uberlândia")
	private String nome;
	
	private EstadoModel estado;
}
