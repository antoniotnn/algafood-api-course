package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {
	
	@ApiOperation("Lista os grupos")
	public List<GrupoModel> listar();
	
	@ApiOperation("Busca um grupo por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "400", description = "ID do grupo inválido", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))		
	})
	public GrupoModel buscar(@ApiParam(value = "ID de um Grupo", required = true) Long grupoId);
	
	@ApiOperation("Cadastra um grupo")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Grupo cadastrado")	
	})
	public GrupoModel adicionar(GrupoInput grupoInput);
	
	@ApiOperation("Atualiza um grupo por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Grupo atualizado"),
		@ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))		
	})
	public GrupoModel atualizar(@ApiParam(value = "ID de um Grupo", required = true) Long grupoId, GrupoInput grupoInput);
	
	@ApiOperation("Exclui um grupo por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Grupo excluído"),
		@ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))		
	})
	public void remover(@ApiParam(value = "ID de um Grupo", required = true) Long grupoId);
	
}
