package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository 
	extends CustomJpaRepository<Restaurante, Long>, RestauranteRespositoryQueries,
	JpaSpecificationExecutor<Restaurante> {
	
//	@Query("from Restaurante r join fetch r.cozinha left join fetch r.formasPagamento")
	@Query("from Restaurante r join fetch r.cozinha")
	List<Restaurante> findAll();
	
	/* se um restaurante não tiver nenhuma forma de pagamento associada a ele, esse restaurante não será
	 * retornado. Para resolver isso temos que usar LEFT JOIN FETCH no lugar de JOIN FETCH
	 */
	
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

//	@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinha);
	
//	List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinhaId);
	
	Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
	
	List<Restaurante> findTop2ByNomeContaining(String nome);
	
	int countByCozinhaId(Long cozinhaId);
	
	boolean existsResponsavel(Long restauranteId, Long usuarioId);
	
}
