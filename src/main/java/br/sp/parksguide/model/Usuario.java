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

@Entity
@Data
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUsuario;
	private String nome;
	@Column(unique = true)
	@Email
	private String email;
	@NotEmpty
	private String senha;
	
	
	public void setSenha(String senha) {
			
		this.senha = HashUtil.hash256(senha);
	}	
}
