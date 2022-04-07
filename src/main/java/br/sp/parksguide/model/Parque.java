package br.sp.parksguide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Parque {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Column(columnDefinition = "TEXT")
	private String descricao;
	private String cep;
	private String rua;
	private String numero;
	private String bairro;
	private String cidade;
	private String estado;
	@Column(columnDefinition = "TEXT")
	private String fotos;
	private String brinquedos;
	private String telefone;
	private String redesSociais;
	private String site;
	private String ingresso;
	@ManyToOne
	private TipoParques tipo;
	private boolean espacoInfantil;
	private boolean pracaAlimentacao;
	
}
