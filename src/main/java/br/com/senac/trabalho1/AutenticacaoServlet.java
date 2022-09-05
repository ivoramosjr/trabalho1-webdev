package br.com.senac.trabalho1;

import br.com.senac.trabalho1.entity.Usuario;
import br.com.senac.trabalho1.service.LoginService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Enumeration;

@WebServlet(name = "AutenticacaoServlet", value = "/AutenticacaoServlet")
public class AutenticacaoServlet extends HttpServlet {

    LoginService loginService;
    public void init(){
        loginService = new LoginService();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
            RequestDispatcher rd= getServletContext().getRequestDispatcher("/WEB-INF/paginaRestrita.jsp");
            rd.forward(request, response);

        }else{
            System.out.println("Usuario nao existe! Redireciona para tela de login.");
            RequestDispatcher rd= request.getRequestDispatcher("login.html");
            rd.forward(request, response);
        }
    }
}
