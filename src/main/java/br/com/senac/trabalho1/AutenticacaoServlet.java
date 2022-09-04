package br.com.senac.trabalho1;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AutenticacaoServlet", value = "/AutenticacaoServlet")
public class AutenticacaoServlet extends HttpServlet {

    public void init(){
        System.out.println("Olá :D");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
