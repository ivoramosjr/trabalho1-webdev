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
    private static final String COOKIE_VALUE = "autenticado";
    private static final int COOKIE_TIME = 300;
    public void init(){
        loginService = new LoginService();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Carregando usuário...");

        HttpSession session = request.getSession();
        String infos = (String) session.getAttribute("infos");
        //TODO refatorar esse método se for null pegar do cookie a info do usuário
        if(infos == null){
            System.out.println("Fechou o navegador...");
            RequestDispatcher rd= getServletContext().getRequestDispatcher("/logout");
            rd.forward(request, response);
        }else{
            Usuario usuario = Encriptador.desencriptaConteudo(infos);
            request.setAttribute("usuario", usuario.getLogin());
            RequestDispatcher rd= getServletContext().getRequestDispatcher("/WEB-INF/paginaRestrita.jsp");
            rd.forward(request, response);
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

            session.setAttribute("infos",Encriptador.encriptarConteudo(usuario));
            session.setMaxInactiveInterval(COOKIE_TIME);

            request.setAttribute("usuario", usuario.getLogin());

            String manterLogado = request.getParameter("manter-login");

            if(manterLogado != null)
                response.addCookie(criaCookie(usuario));

            RequestDispatcher rd= getServletContext().getRequestDispatcher("/WEB-INF/paginaRestrita.jsp");
            rd.forward(request, response);

        }else{
            System.out.println("Usuario nao existe! Redireciona para tela de login.");
            RequestDispatcher rd= request.getRequestDispatcher("login.html");
            rd.forward(request, response);
        }
    }

    private Cookie criaCookie(Usuario usuario){
        Cookie cookie = new Cookie(COOKIE_NAME, COOKIE_VALUE);
        cookie.setMaxAge(COOKIE_TIME);
        return cookie;
    }
}
