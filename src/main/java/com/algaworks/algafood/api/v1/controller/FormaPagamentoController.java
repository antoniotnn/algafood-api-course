package com.algaworks.algafood.api.v1.controller;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.algaworks.algafood.api.v1.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInput;
import com.algaworks.algafood.api.v1.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping(value = "/v1/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE) 
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	
	@Autowired
	private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

	@GetMapping
	@CheckSecurity.FormasPagamento.PodeConsultar
	public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
		
		OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();
		
		if (dataUltimaAtualizacao != null)
		{
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}	
		// aqui j?? temos condi????es de saber se continua ou n??o o processamento
		
		if (request.checkNotModified(eTag)) {
			return null;
		}
		
		CollectionModel<FormaPagamentoModel> formasPagamentoModel = 
				formaPagamentoModelAssembler.toCollectionModel(formaPagamentoRepository.findAll());	
		
		return ResponseEntity.ok()
//				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))       - Se n??o informar nada public (abaixo) ?? o padr??o
//				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
//				.cacheControl(CacheControl.noCache())  //ao fazer cache, valida????o da requisi????o passa a ser obrigat??ria sempre. n??o quer dizer que n??o h?? cache, como se a resposta em cache fosse sempre velha ( stale)
//				.cacheControl(CacheControl.noStore()) // resposta n??o cache??vel, nada ser?? armazenado.
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				.eTag(eTag)
				.body(formasPagamentoModel);
	}

	@GetMapping("/{formaPagamentoId}")
	@CheckSecurity.FormasPagamento.PodeConsultar
	public ResponseEntity<FormaPagamentoModel> buscar(@PathVariable Long formaPagamentoId, ServletWebRequest request) {
		
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
		
		OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataAtualizacaoById(formaPagamentoId);
		
		if (dataUltimaAtualizacao != null)
		{
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}	
		// aqui j?? temos condi????es de saber se continua ou n??o o processamento
		
		if (request.checkNotModified(eTag)) {
			return null;
		}
		
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);
			
		FormaPagamentoModel formaPagamentoModel =  formaPagamentoModelAssembler.toModel(formaPagamento);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.eTag(eTag)
				.body(formaPagamentoModel);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@CheckSecurity.FormasPagamento.PodeEditar
	public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);
			
		return formaPagamentoModelAssembler.toModel(cadastroFormaPagamentoService.salvar(formaPagamento));
	}
	
	@PutMapping("/{formaPagamentoId}")
	@CheckSecurity.FormasPagamento.PodeEditar
	public FormaPagamentoModel atualizar(@PathVariable Long formaPagamentoId, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
						
		FormaPagamento formaPagamentoAtual = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);
			
		formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);
					
		return formaPagamentoModelAssembler.toModel(cadastroFormaPagamentoService.salvar(formaPagamentoAtual));
	
	}
	
	@CheckSecurity.FormasPagamento.PodeEditar
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long formaPagamentoId) {
		cadastroFormaPagamentoService.excluir(formaPagamentoId);
	}
	
}
