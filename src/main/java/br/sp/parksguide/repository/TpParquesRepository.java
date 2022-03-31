package br.sp.parksguide.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.sp.parksguide.model.TipoParques;

public interface TpParquesRepository extends PagingAndSortingRepository<TipoParques, Long>{
	
	@Query("SELECT p FROM TipoParques p WHERE p.palavraChave LIKE %:p%")
	public List<TipoParques> procurarPalavraChave(@Param("p") String palavraChave);
	
	@Query("SELECT n FROM TipoParques n WHERE n.nome LIKE %:n%")
	public List<TipoParques> procurarPorNome(@Param("n") String nome);
	
	@Query("SELECT d FROM TipoParques d WHERE d.descricao LIKE %:d%")
	public List<TipoParques> procurarPorDescricao(@Param("d") String descricao);

	
}
