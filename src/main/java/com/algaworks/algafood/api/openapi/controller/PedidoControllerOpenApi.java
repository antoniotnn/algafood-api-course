package com.algaworks.algafood.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.filter.PedidoFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {
	
//	@ApiImplicitParams({
//		@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",    para SWAGGER 2
//				name = "campos", paramType = "query", type = "string")
//	})
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula", name = "campos", dataTypeClass = String.class, paramType = "query")
	})
	@ApiOperation("Pesquisa os pedidos")
	Page<PedidoResumoModel> pesquisar(PedidoFilter filtro,Pageable pageable);
	
	@ApiOperation("Registra um pedido")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Pedido registrado")	
	})
	PedidoModel adicionar(PedidoInput pedidoInput);
	
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
				name = "campos", paramType = "query", dataTypeClass = String.class)
	})
	@ApiOperation("Busca um pedido por código")
	@ApiResponses({
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))		
	})
	PedidoModel buscar(@ApiParam(value = "Código de um pedido", required = true) String codigoPedido);

}
