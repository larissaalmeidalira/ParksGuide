package br.sp.parksguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.sp.parksguide.model.Parque;

public interface ParqueRepository extends PagingAndSortingRepository<Parque, Long>{

}
