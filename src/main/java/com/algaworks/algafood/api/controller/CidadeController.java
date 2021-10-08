package com.algaworks.algafood.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;


@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;
	
	@GetMapping
	public CollectionModel<CidadeModel> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();
		
		List<CidadeModel> cidadesModel = cidadeModelAssembler.toCollectionModel(todasCidades);
		
		cidadesModel.forEach(cidadeModel -> {
			cidadeModel.add(linkTo(methodOn(CidadeController.class)
					.buscar(cidadeModel.getId())).withSelfRel());
			
			cidadeModel.add(linkTo(methodOn(CidadeController.class)
					.listar()).withRel("cidades"));
			
			cidadeModel.getEstado().add(linkTo(methodOn(EstadoController.class)
					.buscar(cidadeModel.getEstado().getId())).withSelfRel());		
		});
		
		CollectionModel<CidadeModel> cidadesCollectionModel = CollectionModel.of(cidadesModel);
		
		cidadesCollectionModel.add(linkTo(CidadeController.class).withSelfRel());
		
		return cidadesCollectionModel;
	}
	
	@GetMapping(value = "/{cidadeId}")
	public CidadeModel buscar(@PathVariable Long cidadeId){
		Cidade cidade = cadastroCidadeService.buscarOufalhar(cidadeId);
		
		CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);
	
		
//		cidadeModel.add(Link.of("https://localhost:8080/cidades/1"));
		
//		cidadeModel.add(WebMvcLinkBuilder.linkTo(CidadeController.class)
//				.slash(cidadeModel.getId()).withSelfRel());
		cidadeModel.add(linkTo(methodOn(CidadeController.class)
				.buscar(cidadeModel.getId())).withSelfRel());
		
//		cidadeModel.add(Link.of("https://localhost:8080/cidades", "cidades"));
		
//		cidadeModel.add(linkTo(CidadeController.class)
//				.withRel("cidades"));
		
		cidadeModel.add(linkTo(methodOn(CidadeController.class)
				.listar()).withRel("cidades"));
	
//		cidadeModel.getEstado().add(Link.of("https://localhost:8080/estados/1"));
		
//		cidadeModel.add(linkTo(EstadoController.class)
//				.slash(cidadeModel.getEstado().getId()).withSelfRel());
		
		cidadeModel.getEstado().add(linkTo(methodOn(EstadoController.class)
				.buscar(cidadeModel.getEstado().getId())).withSelfRel());

//		cidadeModel.add(Link.of("https://localhost:8080/cidades/1", IanaLinkRelations.SELF));
		
//		cidadeModel.add(Link.of("https://localhost:8080/cidades", IanaLinkRelations.COLLECTION));
		
//		cidadeModel.getEstado().add(Link.of("https://localhost:8080/estados/1"));
		
		return cidadeModel;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput){   
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
			
			cidade =  cadastroCidadeService.salvar(cidade);
			
			CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);
			
			ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());
			
			return cidadeModel;
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping(value = "/{cidadeId}")
	public CidadeModel atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {
		
		try {
			Cidade cidadeAtual = cadastroCidadeService.buscarOufalhar(cidadeId);
			
			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
//			BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		
			return cidadeModelAssembler.toModel(cadastroCidadeService.salvar(cidadeAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}			
	}
	
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId){
		cadastroCidadeService.excluir(cidadeId);
	}		
	
}