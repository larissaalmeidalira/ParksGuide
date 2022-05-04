package br.sp.parksguide.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.sp.parksguide.annotation.Privado;
import br.sp.parksguide.annotation.Publico;

import br.sp.parksguide.rest.UsuarioRestController;

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
				
				String token = null;
				if(metodoChamado.getMethodAnnotation(Privado.class) != null) {
					try {
						
						token = request.getHeader("Authorization");
						Algorithm algoritmo = Algorithm.HMAC256(UsuarioRestController.SECRET);
						JWTVerifier verifier = JWT.require(algoritmo).withIssuer(UsuarioRestController.EMISSOR).build();
						DecodedJWT jwt = verifier.verify(token);
						
						Map<String, Claim> payload = jwt.getClaims();
						System.out.println(payload.get("nome_usuario"));
						
						return true;
						
					}catch (Exception e){
						if(token == null) {
							response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
						}else {
							response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
						}
						
						return false;
					}
				}
			
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
