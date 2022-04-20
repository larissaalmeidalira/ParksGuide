package br.sp.parksguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.sp.parksguide.model.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>{

}
