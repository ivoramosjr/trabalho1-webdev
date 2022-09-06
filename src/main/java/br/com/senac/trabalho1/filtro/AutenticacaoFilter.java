package br.com.senac.trabalho1.filtro;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter(filterName = "AutenticacaoFilter", urlPatterns = "/*")
public class AutenticacaoFilter implements Filter {

    private final static String AUTH_PATH = "/AutenticacaoServlet";

    public void init(FilterConfig config) throws ServletException {
        System.out.println("Entrou no filtro :D");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("Processando...");

        HttpServletRequest httpReq=(HttpServletRequest) request;

        HttpServletResponse httpRes = (HttpServletResponse) response;

        String path = ((HttpServletRequest) request).getServletPath();

        Cookie[] cookies = httpReq.getCookies();

        if(cookies != null){
            //TODO finalizar
        }else{
            if(path.equals(AUTH_PATH) && httpReq.getMethod().equals("GET")){
                System.out.println("Tentando acessar a página restrita sem acesso...");
                httpRes.sendRedirect("login.html");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
