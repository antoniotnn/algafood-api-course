package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.api.model.input.ProdutoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {
	
	@ApiOperation("Lista os produtos de um restaurante")
	@ApiResponses({
			@ApiResponse(responseCode = "400", description = "ID do restaurante inválido", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))		
	})
	List<ProdutoModel> listar(@ApiParam(value = "ID do restaurante", required = true) Long restauranteId, @ApiParam(value = "Indica se deve ou não incluir produtos inativos no resultado da listagem", defaultValue = "false") boolean incluirInativos);

	@ApiOperation("Busca um produto de um restaurante")
	@ApiResponses({
			@ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))		
	})
	ProdutoModel buscar(@ApiParam(value = "ID do restaurante", required = true) Long restauranteId, @ApiParam(value = "ID do produto", required = true) Long produtoId);
	
	@ApiOperation("Cadastra um produto de um restaurante")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Produto cadastrado"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))		
	})
	ProdutoModel adicionar(@ApiParam(value = "ID do restaurante", required = true) Long restauranteId, ProdutoInput produtoInput);
	
	@ApiOperation("Atualiza um produto de um restaurante")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Produto atualizado"),
			@ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))		
	})
	ProdutoModel atualizar(@ApiParam(value = "ID do restaurante", required = true) Long restauranteId, @ApiParam(value = "ID do produto", required = true) Long produtoId, ProdutoInput produtoInput);
	
}
