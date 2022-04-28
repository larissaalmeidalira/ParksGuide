package br.sp.parksguide.rest;

import java.net.URI;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.sp.parksguide.annotation.Privado;
import br.sp.parksguide.annotation.Publico;
import br.sp.parksguide.model.Erro;
import br.sp.parksguide.model.Usuario;
import br.sp.parksguide.repository.UsuarioRepository;

@RequestMapping("/api/usuario")
@RestController
public class UsuarioRestController {
	@Autowired
	private UsuarioRepository repository;
	
	@Publico
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criarUsuario(@RequestBody Usuario usuario){
		
		try {
			repository.save(usuario);
			return ResponseEntity.created(URI.create("/"+usuario.getIdUsuario())).body(usuario);
			
		}catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			Erro erro = new Erro();
			erro.setStatusCode(500);
			erro.setMensagem("Erro de Constraint: Registro Duplicado");
			erro.setException(e.getClass().getName());
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
			
		} catch (Exception e) {
			e.printStackTrace();
			Erro erro = new Erro();
			erro.setStatusCode(500);
			erro.setMensagem("Erro: "+e.getMessage());
			erro.setException(e.getClass().getName());
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Privado
	@RequestMapping(value = "/{idUsuario}", method = RequestMethod.GET)
	public ResponseEntity<Usuario> findUsuario(@PathVariable("idUsuario") Long idUsuario){
		Optional<Usuario> usuario = repository.findById(idUsuario);
		if(usuario.isPresent()) {
			return ResponseEntity.ok(usuario.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@Privado
	@RequestMapping(value = "/{idUsuario}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizarUsuario(@RequestBody Usuario usuario, @PathVariable("idUsuario") Long idUsuario){
		
		if(idUsuario != usuario.getIdUsuario()) {
			throw new RuntimeException("ID Iválido");
		}
		
		repository.save(usuario);
		
		HttpHeaders header = new HttpHeaders();
		header.setLocation(URI.create("/api/usuario"));
		return new ResponseEntity<Void>(header, HttpStatus.OK);
	}
	
	@Privado
	@RequestMapping(value = "/{idUsuario}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluirUsuario(@PathVariable("idUsuario") Long idUsuario){
		repository.deleteById(idUsuario);
		
		return ResponseEntity.noContent().build();
	}
	
}
