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

    private static final String AUTH_PATH = "AutenticacaoServlet";
    private static final String RESTRICT_PATH = "/WEB-INF/paginaRestrita.jsp";
    private static final String LOGIN_PATH = "login.html";
    private static final String METHOD_GET = "GET";
    private static final String COOKIE_NAME = "auth";
    private static final String LOGOUT_PATH = "/logout";

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

        if(cookies != null && Arrays.stream(cookies).anyMatch(c -> c.getName().equals(COOKIE_NAME)
                && !c.getValue().isEmpty()) && !LOGOUT_PATH.equals(httpReq.getServletPath())){

            System.out.println("Possui cookie...");

            request.getRequestDispatcher(AUTH_PATH).forward(request, response);

        }else{
            if((path.equals("/"+AUTH_PATH) || path.equals(RESTRICT_PATH)) && httpReq.getMethod().equals(METHOD_GET)){
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
}
