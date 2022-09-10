package br.com.senac.trabalho1.servlet;

import br.com.senac.trabalho1.entity.Usuario;
import br.com.senac.trabalho1.security.Encriptador;
import br.com.senac.trabalho1.service.LoginService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet(name = "AutenticacaoServlet", value = "/AutenticacaoServlet")
public class AutenticacaoServlet extends HttpServlet {

    LoginService loginService;
    private static final String COOKIE_NAME = "auth";
    private static final int COOKIE_TIME = 300;
    private static final String LOGOUT_PATH = "logout";
    private static final String RESTRICT_PATH = "/WEB-INF/paginaRestrita.jsp";
    private static final String LOGIN_PATH = "login.html";
    @Override
    public void init(){
        loginService = new LoginService();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Carregando usuário...");
        HttpSession session = request.getSession();
        String infos = (String) session.getAttribute("infos");
        if(infos == null){
            System.out.println("Fechou o navegador...");
            Cookie cookie = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals(COOKIE_NAME))
                    .findFirst()
                    .get();

            if(Encriptador.validaInfos(cookie.getValue())){
                Usuario usuario = Encriptador.desencriptaConteudo(cookie.getValue());
                if(loginService.validarLogin(usuario)){
                    session.setAttribute("infos", infos);
                    session.setMaxInactiveInterval(COOKIE_TIME);
                    request.setAttribute("usuario", usuario.getLogin());
                    RequestDispatcher rd= getServletContext().getRequestDispatcher(RESTRICT_PATH);
                    rd.forward(request, response);
                }else{
                    RequestDispatcher rd= getServletContext().getRequestDispatcher("/"+LOGOUT_PATH);
                    rd.forward(request, response);
                }
            }else{
                RequestDispatcher rd= getServletContext().getRequestDispatcher("/"+LOGOUT_PATH);
                rd.forward(request, response);
            }
        }else{
            Usuario usuario = Encriptador.desencriptaConteudo(infos);
            if(Encriptador.validaInfos((String) session.getAttribute("infos"))
                    && loginService.validarLogin(usuario)){
                request.setAttribute("usuario", usuario.getLogin());
                RequestDispatcher rd= getServletContext().getRequestDispatcher(RESTRICT_PATH);
                rd.forward(request, response);
            }else{
                RequestDispatcher rd= getServletContext().getRequestDispatcher("/"+LOGOUT_PATH);
                rd.forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Tentando logar");

        HttpSession session = request.getSession();

        Usuario usuario = new Usuario();

        String login = request.getParameter("login");
        usuario.setLogin(login);
        String senha = request.getParameter("password");
        usuario.setSenha(senha);

        if(loginService.validarLogin(usuario)){
            System.out.println("Validado!");

            String infos = Encriptador.encriptarConteudo(usuario);
            session.setAttribute("infos", infos);
            session.setMaxInactiveInterval(COOKIE_TIME);

            request.setAttribute("usuario", usuario.getLogin());

            String manterLogado = request.getParameter("manter-login");

            if(manterLogado != null)
                response.addCookie(criaCookie(infos));

            RequestDispatcher rd= getServletContext().getRequestDispatcher(RESTRICT_PATH);
            rd.forward(request, response);

        }else{
            System.out.println("Usuario nao existe! Redireciona para tela de login.");
            RequestDispatcher rd= request.getRequestDispatcher(LOGIN_PATH);
            rd.forward(request, response);
        }
    }

    private Cookie criaCookie(String infos){
        Cookie cookie = new Cookie(COOKIE_NAME, infos);
        cookie.setMaxAge(COOKIE_TIME);
        return cookie;
    }
}
