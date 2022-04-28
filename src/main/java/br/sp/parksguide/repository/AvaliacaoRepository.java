package br.sp.parksguide.repository;


import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;


import br.sp.parksguide.model.Avaliacao;

public interface AvaliacaoRepository extends PagingAndSortingRepository<Avaliacao, Long>{
	
	public List<Avaliacao> findByParqueId(Long id);

}
