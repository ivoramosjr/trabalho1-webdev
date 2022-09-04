package br.com.senac.trabalho1.filtro;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter(filterName = "AutenticacaoFilter", urlPatterns = "/*")
public class AutenticacaoFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
        System.out.println("Entrou no filtro :D");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("Processando...");

        HttpServletRequest httpReq=(HttpServletRequest) request;

        String targetURI= httpReq.getRequestURI();

        chain.doFilter(request, response);
    }
}
