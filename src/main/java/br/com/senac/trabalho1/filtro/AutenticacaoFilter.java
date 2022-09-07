package br.com.senac.trabalho1.filtro;

import br.com.senac.trabalho1.entity.Usuario;
import br.com.senac.trabalho1.security.Encriptador;
import br.com.senac.trabalho1.service.LoginService;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebFilter(filterName = "AutenticacaoFilter", urlPatterns = "/*")
public class AutenticacaoFilter implements Filter {

    private final static String AUTH_PATH = "AutenticacaoServlet";
    private final static String LOGIN_PATH = "login.html";
    private final static String METHOD_GET = "GET";
    private final static String COOKIE_NAME = "auth";

    LoginService loginService;

    public void init(FilterConfig config) throws ServletException {
        System.out.println("Iniciando o filtro...");
        loginService = new LoginService();
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("Processando...");

        HttpServletRequest httpReq = (HttpServletRequest) request;

        HttpServletResponse httpRes = (HttpServletResponse) response;

        String path = ((HttpServletRequest) request).getServletPath();

        Cookie[] cookies = httpReq.getCookies();

        if(cookies != null && Arrays.stream(cookies).anyMatch(c -> c.getName().equals(COOKIE_NAME))){
            System.out.println("Verificando o cookie...");

            Cookie cookie = Arrays.stream(cookies).filter(c -> c.getName().equals(COOKIE_NAME)).findFirst().get();

            if(cookieEhValido(cookie)){
                request.getRequestDispatcher(AUTH_PATH).forward(request, response);
            }else{
                cookie.setMaxAge(0);
                retornaPaginaPrincipal(httpRes);
            }
        }else{
            if(path.equals("/"+AUTH_PATH) && httpReq.getMethod().equals(METHOD_GET)){
                retornaPaginaPrincipal(httpRes);
            }else{
                chain.doFilter(request, response);
            }
        }

    }

    private void retornaPaginaPrincipal(HttpServletResponse httpRes) throws IOException {
        System.out.println("Tentando acessar a página restrita sem permissão...");
        httpRes.sendRedirect(LOGIN_PATH);
    }

    private boolean cookieEhValido(Cookie cookie){
        String informacaoCookie = cookie.getValue();

        if(!informacaoCookie.contains("-"))
            return false;

        try{
            Encriptador.desencriptaConteudo(informacaoCookie);
            return true;
        }catch (Exception e){
            System.out.println("Cookie falso...");
            return false;
        }
    }
}
