package br.sp.parksguide.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import br.sp.parksguide.model.TipoParques;
import br.sp.parksguide.repository.TpParquesRepository;

@Controller
public class TipoController {
	
	@Autowired
	private TpParquesRepository tipoRepository;
	
	@RequestMapping("TipoParques")
	public String formAdm() {
		return "parques/formparques";
	}
	
	@RequestMapping("salvarTipos")
	public String salvarTipos(@Valid TipoParques parques, BindingResult result, RedirectAttributes attr) {
		if(result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "Verifique os campos!");
			return "redirect:TipoParques";
		}
		try {
			tipoRepository.save(parques);
		
			attr.addFlashAttribute("mensagemSucesso", "Tipo de parques cadastrado com sucesso! ID - "+parques.getId() );
			return "redirect:administrador";
		} catch (Exception e) {
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar o tipo do parque: "+e.getMessage());
		}
		
		return "redirect:TipoParques";
	}
	
	@RequestMapping("listarParques/{page}")
	public String listaAdm(Model model, @PathVariable("page") int page) {
		
		PageRequest pageable = PageRequest.of(page-1, 10, Sort.by(Sort.Direction.ASC, "nome"));
		Page<TipoParques> pagina = tipoRepository.findAll(pageable);
		int totalPages = pagina.getTotalPages();
		List<Integer> pageNumbers = new ArrayList<Integer>();
		for(int i = 0; i < totalPages; i++) {
			pageNumbers.add(i+1);
		}
		
		model.addAttribute("parques", pagina.getContent());
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalPages);
		model.addAttribute("numPaginas", pageNumbers);
		
		
		return "parques/listaparques";
	}
	
	@RequestMapping("alterarParque")
	public String alterar(Model model, Long id) {
		TipoParques p = tipoRepository.findById(id).get();
		model.addAttribute("parque", p);
		return "forward:TipoParques";
	}
	
	@RequestMapping("excluirParque")
	public String excluir(Long id) {
		tipoRepository.deleteById(id);
		return "redirect:listarParques/1";
	}
	
	@RequestMapping("buscarParque")
	public String buscar(String select, String valorBuscado, Model model) {
		if(select.equals("palavraChave")) {
			model.addAttribute("parques", tipoRepository.procurarPalavraChave(valorBuscado));
			return "parques/listaparques";
			
		}else if(select.equals("nome")) {
			model.addAttribute("parques", tipoRepository.procurarPorNome(valorBuscado));
			return "parques/listaparques";
			
		}else {
			model.addAttribute("parques", tipoRepository.procurarPorDescricao(valorBuscado));
			return "parques/listaparques";
		}
		
		
	}
	
}
