package br.sp.parksguide.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



import br.sp.parksguide.model.Usuario;
import br.sp.parksguide.repository.UsuarioRepository;

@RequestMapping("/api/usuario")
@RestController
public class UsuarioRestController {
	@Autowired
	private UsuarioRepository repository;
	
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> criarUsuario(Usuario usuario){
		return null;
	}

}
