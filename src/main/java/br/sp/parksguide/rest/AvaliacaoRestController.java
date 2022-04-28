package br.sp.parksguide.rest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.sp.parksguide.annotation.Privado;
import br.sp.parksguide.annotation.Publico;
import br.sp.parksguide.model.Avaliacao;
import br.sp.parksguide.repository.AvaliacaoRepository;

@RestController
@RequestMapping("/api/avaliacao")
public class AvaliacaoRestController {
	@Autowired
	private AvaliacaoRepository repository;
	
	@Privado
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Avaliacao> criarAvalicao(@RequestBody Avaliacao avaliacao){
		repository.save(avaliacao);
		return ResponseEntity.created(URI.create("/avaliacao"+avaliacao.getId())).body(avaliacao);
	}
	
	@Publico
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Avaliacao getAvaliacao(Long id) {
		
		return repository.findById(id).get();
	}
	
	@Publico
	@RequestMapping(value = "/parque/{id}", method = RequestMethod.GET)
	public List<Avaliacao> listarPorParque(@PathVariable("id") Long id) {
		return repository.findByParqueId(id);
	}
	
	
	
}
