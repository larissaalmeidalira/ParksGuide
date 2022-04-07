package br.sp.parksguide.controller;

import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;





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
	public String salvarTipos(TipoParques parques) {
		tipoRepository.save(parques);
					
		return "redirect:TipoParques";
	}
	
	@RequestMapping("listarTipoParques/{page}")
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
	
	@RequestMapping("alterarTipoParque")
	public String alterar(Model model, Long id) {
		TipoParques p = tipoRepository.findById(id).get();
		model.addAttribute("parque", p);
		return "forward:TipoParques";
	}
	
	@RequestMapping("excluirTipoParque")
	public String excluir(Long id) {
		tipoRepository.deleteById(id);
		return "redirect:listarTipoParques/1";
	}
	
	@RequestMapping("/buscarTipoParque")
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
