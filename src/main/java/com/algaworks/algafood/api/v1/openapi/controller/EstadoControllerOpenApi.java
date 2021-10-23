package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {
	
	@ApiOperation("Lista os Estados")
	CollectionModel<EstadoModel> listar();
	
	@ApiOperation("Busca um estado por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "400", description = "ID do estado inválido", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))		
	})
	EstadoModel buscar(@ApiParam(value = "ID de um estado", required = true) Long estadoId);
	
	@ApiOperation("Cadastra um estado")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Estado cadastrado")	
	})
	EstadoModel adicionar(EstadoInput estadoInput);
	
	
	@ApiOperation("Atualiza um Estado por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Estado atualizado"),
		@ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))		
	})
	EstadoModel atualizar(@ApiParam(value = "ID de um estado", required = true) Long estadoId, EstadoInput estadoInput);
	
	@ApiOperation("Exclui um estado por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Estado excluído"),
		@ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))		
	})
	void remover(@ApiParam(value = "ID de um estado", required = true) Long estadoId);
}
