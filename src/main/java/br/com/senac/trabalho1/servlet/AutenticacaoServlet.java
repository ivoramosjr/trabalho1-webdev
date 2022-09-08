package br.com.senac.trabalho1.servlet;

import br.com.senac.trabalho1.entity.Usuario;
import br.com.senac.trabalho1.security.Encriptador;
import br.com.senac.trabalho1.service.LoginService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "AutenticacaoServlet", value = "/AutenticacaoServlet")
public class AutenticacaoServlet extends HttpServlet {

    LoginService loginService;
    private static final String NOME_COOKIE = "auth";

    private static final int TEMPO_COOKIE = 3600;
    public void init(){
        loginService = new LoginService();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Carregando usuário...");
        Cookie cookie = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals(NOME_COOKIE)).findFirst().get();
        Usuario usuario = Encriptador.desencriptaConteudo(cookie.getValue());
        request.setAttribute("usuario", usuario.getLogin());
        RequestDispatcher rd= getServletContext().getRequestDispatcher("/WEB-INF/paginaRestrita.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Tentando logar");

        Usuario usuario = new Usuario();

        String login = request.getParameter("login");
        usuario.setLogin(login);
        String senha = request.getParameter("password");
        usuario.setSenha(senha);

        if(loginService.validarLogin(usuario)){
            System.out.println("Validado!");
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
        Cookie cookie = new Cookie(NOME_COOKIE, Encriptador.encriptarConteudo(usuario));
        cookie.setMaxAge(TEMPO_COOKIE);
        return cookie;
    }
}