package br.sp.parksguide.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;


import br.sp.parksguide.model.Parque;

public interface ParqueRepository extends PagingAndSortingRepository<Parque, Long>{
	
	public List<Parque> findByTipoId(Long idTipo);
	
	public List<Parque> findByEspacoInfantil(boolean espacoInfantil);
	
	public List<Parque> findByPracaAlimentacao(boolean pracaAlimentacao);
	
	public List<Parque> findByEstado(String estado);

}
