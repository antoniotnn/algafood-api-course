package com.algaworks.algafood.api.v2.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v2.assembler.CidadeInputDisassemblerV2;
import com.algaworks.algafood.api.v2.assembler.CidadeModelAssemblerV2;
import com.algaworks.algafood.api.v2.model.CidadeModelV2;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
import com.algaworks.algafood.api.v2.openapi.controller.CidadeControllerV2OpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;


@RestController
@RequestMapping(path = "v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 implements CidadeControllerV2OpenApi {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CidadeModelAssemblerV2 cidadeModelAssembler;
	
	@Autowired
	private CidadeInputDisassemblerV2 cidadeInputDisassembler;
	
	@Override
	@GetMapping
	public CollectionModel<CidadeModelV2> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();
		
		return cidadeModelAssembler.toCollectionModel(todasCidades);
	}
	
	@Override
	@GetMapping(value = "/{cidadeId}")
	public CidadeModelV2 buscar(@PathVariable Long cidadeId){
		Cidade cidade = cadastroCidadeService.buscarOufalhar(cidadeId);
		
		return cidadeModelAssembler.toModel(cidade);
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModelV2 adicionar(@RequestBody @Valid CidadeInputV2 cidadeInput){   
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
			
			cidade =  cadastroCidadeService.salvar(cidade);
			
			CidadeModelV2 cidadeModel = cidadeModelAssembler.toModel(cidade);
			
			ResourceUriHelper.addUriInResponseHeader(cidadeModel.getIdCidade());
			
			return cidadeModel;
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@Override
	@PutMapping(value = "/{cidadeId}")
	public CidadeModelV2 atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInputV2 cidadeInput) {
		
		try {
			Cidade cidadeAtual = cadastroCidadeService.buscarOufalhar(cidadeId);
			
			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
		
			return cidadeModelAssembler.toModel(cadastroCidadeService.salvar(cidadeAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}			
	}
	
	@Override
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId){
		cadastroCidadeService.excluir(cidadeId);
	}		
	
}