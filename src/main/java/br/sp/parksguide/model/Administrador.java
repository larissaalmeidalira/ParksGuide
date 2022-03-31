package br.sp.parksguide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.sp.parksguide.util.HashUtil;
import lombok.Data;

// PARA MAPEAR COMO UMA ENTIDADE JPA
@Entity
// PARA GERAR OS GET E O SET
@Data
public class Administrador {
	// CHAVE PRIMÁRIA E AUTO-INCREMENT
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String nome;
	// PARA INDICAR QUE O EMAIL É ÚNICO E NÃO PODE TER OUTRO IGUAL
	@Column(unique = true)
	@Email
	private String email;
	@NotEmpty
	private String senha;
	
	// MÉTODO PARA "SETAR" A SENHA APLICANDO HASH
	public void setSenha(String senha) {
		// APLICA O HASH E "SETA" A SENHA NO OBJETO
		this.senha = HashUtil.hash256(senha);
	}
	
	// MÉTODO PARA "SETAR" A SENHA SEM APLICAR O HASH
	public void setSenhaComHash(String hash) {
		// "SETA" O HASH NA SENHA
		this.senha = hash;
		
	}
}
