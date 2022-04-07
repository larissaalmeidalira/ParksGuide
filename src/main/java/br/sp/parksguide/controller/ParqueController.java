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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



import br.sp.parksguide.model.Parque;

import br.sp.parksguide.repository.ParqueRepository;
import br.sp.parksguide.repository.TpParquesRepository;


@Controller
public class ParqueController {
	
	@Autowired
	private TpParquesRepository repTipo;
	@Autowired
	private ParqueRepository repParque;
	
	@RequestMapping("cadastroParque")
	public String parque(Model model) {
		model.addAttribute("tipos", repTipo.findAll());
		return "parques/parque";
	}
	
	@RequestMapping("salvarParque")
	public String salvar(Parque parque, @RequestParam("fileFotos") MultipartFile[] fileFotos) {
		
		System.out.println(fileFotos.length);
		//repParque.save(parque);
	
		return "redirect:cadastroParque";
	}
	
	@RequestMapping("listarParques/{page}")
	public String listar(Model model, @PathVariable("page") int page) {
		
		PageRequest pageable = PageRequest.of(page-1, 10, Sort.by(Sort.Direction.ASC, "nome"));
		Page<Parque> pagina = repParque.findAll(pageable);
		int totalPages = pagina.getTotalPages();
		List<Integer> pageNumbers = new ArrayList<Integer>();
		for(int i = 0; i < totalPages; i++) {
			pageNumbers.add(i+1);
		}

		model.addAttribute("parque", pagina.getContent());
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalPages);
		model.addAttribute("numPaginas", pageNumbers);
		
		return "parques/lista";
	}
	
	@RequestMapping("alterarParque")
	public String alterar(Model model, Long id) {
		Parque p = repParque.findById(id).get();
		model.addAttribute("parques", p);
		return "forward:cadastroParque";
	}
	
	@RequestMapping("excluirParque")
	public String excluir(Long id) {
		repParque.deleteById(id);
		return "redirect:listarParques/1";
	}

}
