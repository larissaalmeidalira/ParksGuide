package br.sp.parksguide.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.sp.parksguide.annotation.Publico;

@Component
public class AppInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// VARIÁVEL PARA DESCOBRIR PRA ONDE ESTÃO TENTANDO IR
		String uri = request.getRequestURI();
		System.out.println(uri);
		
		if(handler instanceof HandlerMethod) {
			// LIBERA O ACESSO À PÁGINA INICIAL
			if(uri.endsWith("/error")) {
				return true;
			}
			
			// FAZER O CASTING PARA O HandlerMethod
			HandlerMethod metodoChamado = (HandlerMethod) handler;
			
			if(uri.startsWith("/api")) {
				
				return true;
				
			}else {
				
			// SE O MÉTODO FOR PÚBLICO, LIBERA
			if(metodoChamado.getMethodAnnotation(Publico.class) != null) {
				return true;
			}
			// VERIFICA SE EXISTE UM USUÁRIO LOGADO
			if(request.getSession().getAttribute("usuarioLogado") != null) {
				return true;
			}else {
				// REDIRECIONA PARA A PÁGINA INICIAL
				response.sendRedirect("/");
				return false;
			}
			
		}}
		
		return true;
	}

}
