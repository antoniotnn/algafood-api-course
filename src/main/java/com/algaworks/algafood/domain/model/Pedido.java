package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pedido {
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private BigDecimal subtotal;
	
	@Column(nullable = false)
	private BigDecimal taxaFrete;
	
	@Column(nullable = false)
	private BigDecimal valorTotal;
	
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime dataCriacao;
	
	@Column(nullable = true)
	private LocalDateTime dataConfirmacao;
	
	@Column(nullable = true)
	private LocalDateTime dataCancelamento;
	
	@Column(nullable = true)
	private LocalDateTime dataEntrega;
	
	@Embedded
	private Endereco enderecoEntrega;
	
	@Column
	private StatusPedido status;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private FormaPagamento formaPagamento;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurante restaurante;
	
	@ManyToOne
	@JoinColumn(name = "usuario_cliente_id" , nullable = false)
	private Usuario cliente;
	
	private List<ItemPedido> itens = new ArrayList<>();
	

	
	
}
