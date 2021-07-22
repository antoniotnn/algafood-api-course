package com.algaworks.algafood.domain.model;


import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.algafood.core.validation.Groups;
import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ValorZeroIncluiDescricao
(valorField = "taxaFrete",
descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {
	
	// https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-builtin-constraints
	// Referência validações Bean validation / hibernate
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@NotNull
//	@NotEmpty
	@NotBlank//(message = "nome é obrigatório")//(groups = Groups.CozinhaId.class)
	@Column(nullable = false)
	private String nome;
	
//	@DecimalMin("0")
//	@TaxaFrete
//	@Multiplo(numero = 5)
	@NotNull
	@PositiveOrZero//(message = "{TaxaFrete.invalida}")//(groups = Groups.CozinhaId.class)
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;
	
//	@JsonIgnore
//	@JsonIgnoreProperties("hibernateLazyInitializer")
//	@JoinColumn(name = "cozinha_id")  /* linha opcional , se quiser mudar nome so especificar */
//	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	@ManyToOne //(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@NotNull//(groups = Groups.CozinhaId.class)
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
	private Cozinha cozinha;
	
//	@JsonIgnore
	@Embedded
	private Endereco endereco;
	
//	@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime") // datetime(6) com precisão de 6 casas de Milissegundos
	private OffsetDateTime dataCadastro;
	
//	@JsonIgnore
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;
	
//	@JsonIgnore
	@ManyToMany //(fetch = FetchType.EAGER)
	@JoinTable(name = "restaurante_forma_pagamento",
		joinColumns = @JoinColumn(name = "restaurante_id"),
		inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();
	
//	@JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
	
}
