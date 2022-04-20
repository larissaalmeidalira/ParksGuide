package br.sp.parksguide.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.sp.parksguide.annotation.Publico;
import br.sp.parksguide.model.Administrador;
import br.sp.parksguide.repository.AdminRepository;
import br.sp.parksguide.util.HashUtil;

@Controller
public class AdmController {
	
	@Autowired
	private AdminRepository repository;
	
	@Publico
	@RequestMapping("administrador")
	public String formAdm() {
		return "adm/formadm";
	}
	
	@RequestMapping(value = "salvarAdministrador", method = RequestMethod.POST)
	public String salvarAdmin(@Valid Administrador admin, BindingResult result, RedirectAttributes attr) {
		if(result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "Verifique os campos!");
			return "redirect:administrador";
		}
		
		// VERIFICA SE ESTÁ SENDO FEITA UMA ALTERAÇÃO AO INVÉS DE UMA INSERÇÃO
		boolean alteracao = admin.getId() != null ? true : false;
		
		// VERIFICA SE A SENHA ESTÁ VAZIA
		if(admin.getSenha().equals(HashUtil.hash256(""))) {
			// SE NÃO FOR ALTERAÇÃO, EU DEFINO A PRIMEIRA PARTE DO E-MAIL COMO A SENHA
			if(!alteracao) {
				// EXTRAI A PARTE DO E-MAIL ANTES DO @
				String parte = admin.getEmail().substring(0, admin.getEmail().indexOf("@"));
				// DEFINE A SENHA DO ADMIN
				admin.setSenha(parte);
			}else {
				// BUSCA A SENHA ATUAL
				String hash = repository.findById(admin.getId()).get().getSenha();
				admin.setSenhaComHash(hash);
			}
		}
		
		try {
			repository.save(admin);
			attr.addFlashAttribute("mensagemSucesso", "Administrador cadastrado com sucesso! ID - "+admin.getId() 
				+"  Caso a senha não tenha sido informada, a senha será a parte do e-mail antes do @!");
			return "redirect:administrador";
		} catch (Exception e) {
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar o Administrador: "+e.getMessage());
		}
		
		return "redirect:administrador";
		
	}
	
	// REQUEST MAPPING PARA LISTAR, INFORMANDO A PÁGINA DESEJADA
	@RequestMapping("listarAdm/{page}")
	public String listaAdm(Model model, @PathVariable("page") int page) {
		
		// CRIA UM PAGEABLE COM 6 ELEMENTOS POR PÁGINA, ORDENANDO OS OBJETOS
		PageRequest pageable = PageRequest.of(page-1, 10, Sort.by(Sort.Direction.ASC, "nome"));
		// CRIA A PÁGINA ATUAL ATRAVÉS DO REPOSITORY
		Page<Administrador> pagina = repository.findAll(pageable);
		// DESCOBRIR O TOTAL DE PÁGINAS
		int totalPages = pagina.getTotalPages();
		// CRIA UMA LISTA DE INTEIROS PARA REPRESENTAR AS PÁGINAS
		List<Integer> pageNumbers = new ArrayList<Integer>();
		// PREENCHER A LISTA COM AS PÁGINAS
		for(int i = 0; i < totalPages; i++) {
			pageNumbers.add(i+1);
		}
		// ADICIONA AS VARIÁVEIS NA MODEL
		model.addAttribute("admins", pagina.getContent());
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalPages);
		model.addAttribute("numPaginas", pageNumbers);
		
		
		return "adm/listaadm";
	}
	
	@Publico
	@RequestMapping("login")
	public String loing() {
		return "adm/login";
	}
	
	@Publico
	@RequestMapping("loginAdm")
	public String loginAdm(Administrador admLogin, RedirectAttributes attr, HttpSession session) {
		// BUSCAR O ADMINISTRADOR NO BANCO DE DADOS ATRAVÉS DO E-MAIL E SENHA
		Administrador admin = repository.findByEmailAndSenha(admLogin.getEmail(), admLogin.getSenha());
		// VERIFICA SE EXISTE O ADMIN
		if(admin == null) {
			// AVISA AO USUÁRIO
			attr.addFlashAttribute("mensagemErro", "Login e/ou senha inválido");
			return "redirect:login";
		}else {
			// SE NÃO FOR NULO, SALVA NA SESSÃO E ACESSA O SISTEMA
			session.setAttribute("usuarioLogado", admin);
			return "redirect:listarParques/1";
		}
		
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:login";
	}
	
	@RequestMapping("alterarAdm")
	public String alterar(Model model, Long id) {
		Administrador adm = repository.findById(id).get();
		model.addAttribute("admin", adm);
		return "forward:administrador";
	}
	
	@RequestMapping("excluirAdm")
	public String excluir(Long id) {
		repository.deleteById(id);
		return "redirect:listarAdm/1";
	}

}
