package br.sp.parksguide.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;


import br.sp.parksguide.model.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>{
	
	public Usuario findByEmailAndSenha(String email, String senha);
	
	public List<Usuario> findByIdUsuario(Long idUsuario);
	
}
