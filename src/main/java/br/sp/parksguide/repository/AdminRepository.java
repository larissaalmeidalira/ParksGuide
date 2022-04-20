package br.sp.parksguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.sp.parksguide.model.Administrador;

public interface AdminRepository extends PagingAndSortingRepository<Administrador, Long>{
	
	
	public Administrador findByEmailAndSenha(String email, String senha);
	

}
