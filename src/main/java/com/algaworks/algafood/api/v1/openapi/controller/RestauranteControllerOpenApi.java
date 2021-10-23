package com.algaworks.algafood.api.v1.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {
	
//	@ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoModelOpenApi.class)
//    @ApiImplicitParams({
//        @ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome",   PARA SWAGGER 3
//                name = "projecao", paramType = "query", type = "string")
//    })
	
//  @JsonView(RestauranteView.Resumo.class)
	@ApiOperation(value = "Lista os restaurantes")
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Lista restaurantes", name = "projecao", dataTypeClass = String.class, paramType = "query", allowableValues = "apenas-nome")
	})
    CollectionModel<RestauranteBasicoModel> listar();
    
	@ApiIgnore
    @ApiOperation(value = "Lista restaurantes", hidden = true)
    CollectionModel<RestauranteApenasNomeModel> listarApenasNomes();
    
    @ApiOperation("Busca um restaurante por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "400", description = "ID do restaurante inválido", content = @Content(schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))		
	})
    RestauranteModel buscar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);
    
    @ApiOperation("Cadastra um restaurante")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Restaurante cadastrado")	
	})
    RestauranteModel adicionar(RestauranteInput restauranteInput);
    
    @ApiOperation("Atualiza um restaurante por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))		
	})
    RestauranteModel atualizar(@ApiParam(value = "ID de um restaurante", required = true) Long restauranteId, RestauranteInput restauranteInput);
    
    @ApiOperation("Ativa um restaurante por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurante ativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> ativar(
            @ApiParam(value = "ID de um restaurante", required = true)
            Long restauranteId);
    
    @ApiOperation("Inativa um restaurante por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> inativar(
            @ApiParam(value = "ID de um restaurante", required = true)
            Long restauranteId);
    
    @ApiOperation("Ativa múltiplos restaurantes")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso")
    })
    void ativarMultiplos(
            @ApiParam(value = "IDs de restaurantes", required = true)
            List<Long> restauranteIds);
    
    @ApiOperation("Inativa múltiplos restaurantes")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurantes inativados com sucesso")
    })
    void inativarMultiplos(
            @ApiParam(value = "IDs de restaurantes", required = true)
            List<Long> restauranteIds);

    @ApiOperation("Abre um restaurante por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurante Aberto com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> abrir(
            @ApiParam(value = "ID de um restaurante", required = true)
            Long restauranteId);
    
    @ApiOperation("Fecha um restaurante por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurante Fechado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> fechar(
            @ApiParam(value = "ID de um restaurante", required = true)
            Long restauranteId);
}
