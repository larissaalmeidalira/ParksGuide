package br.sp.parksguide.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.sp.parksguide.annotation.Publico;
import br.sp.parksguide.model.Parque;

import br.sp.parksguide.repository.ParqueRepository;


@RequestMapping("/api/parque")
@RestController
public class ParqueRestController {
	
	@Autowired
	private ParqueRepository repository;
	
	@Publico
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Parque> getParques(){
		return repository.findAll();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Parque> findParque(@PathVariable("id") Long idParque){
		// BUSCA O PARQUE
		Optional<Parque> parque = repository.findById(idParque);
		if(parque.isPresent()) {
			return ResponseEntity.ok(parque.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@RequestMapping(value = "/tipo/{id}", method = RequestMethod.GET)
	public List<Parque> getParqueByTipo(@PathVariable("id") Long idTipo){
		
		return repository.findByTipoId(idTipo);
		
	}
	
	@RequestMapping(value = "/espacoInfantil/{espacoInfantil}", method = RequestMethod.GET)
	public List<Parque> getParqueByEspacoInfantil(@PathVariable("espacoInfantil") boolean espacoInfantil){
		
		return repository.findByEspacoInfantil(espacoInfantil);
	}
	
	@RequestMapping(value = "/pracaAlimentacao/{pracaAlimentacao}", method = RequestMethod.GET)
	public List<Parque> getParqueByPracaAlimentacao(@PathVariable("pracaAlimentacao") boolean pracaAlimentacao){
		
		return repository.findByPracaAlimentacao(pracaAlimentacao);
	}
	
	@RequestMapping(value = "/estado/{estado}", method = RequestMethod.GET)
	public List<Parque> getParqueByEstado(@PathVariable("estado") String estado){
		
		return repository.findByEstado(estado);
	}

}
