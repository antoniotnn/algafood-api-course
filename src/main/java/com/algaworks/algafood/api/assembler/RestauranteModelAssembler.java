package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {
	

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public RestauranteModelAssembler() {
		super(RestauranteController.class, RestauranteModel.class);
	}
	
	@Override
	public RestauranteModel toModel(Restaurante restaurante) {
		RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
		
		modelMapper.map(restaurante, restauranteModel);
		
		restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
		
		restauranteModel.getCozinha().add(
				algaLinks.linkToCozinha(restaurante.getCozinha().getId()));
		
		restauranteModel.getEndereco().getCidade().add(
				algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
		
		restauranteModel.add(
				algaLinks.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento"));
		
		restauranteModel.add(
				algaLinks.linkToRestauranteResponsaveis(restaurante.getId(), "responsaveis"));
		
		return restauranteModel;
	}

	@Override
	public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToRestaurantes());
	}
	
	
	
	/* toInputObject implementado por mim e não pelo curso, para conseguir atender o @PatchMapping */
	public RestauranteInput toInputObject(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteInput.class);
	}
	
//	public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
//		return restaurantes.stream()
//				.map(restaurante -> toModel(restaurante))
//				.collect(Collectors.toList());
//	}
	
	/* toInputObject implementado por mim e não pelo curso, para conseguir atender o @PatchMapping 
	
	public RestauranteInput toInputObject(Restaurante restaurante) {
		RestauranteInput restauranteInput = new RestauranteInput();
		restauranteInput.setNome(restaurante.getNome());
		restauranteInput.setTaxaFrete(restaurante.getTaxaFrete());
		
		CozinhaIdInput cozinhaIdInput = new CozinhaIdInput();
		cozinhaIdInput.setId(restaurante.getCozinha().getId());
		
		restauranteInput.setCozinha(cozinhaIdInput);
		
		return restauranteInput;
		
	}
	*/
}
