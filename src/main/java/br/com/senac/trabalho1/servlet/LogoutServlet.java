package br.com.senac.trabalho1.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "logout", value = "/logout")
public class LogoutServlet extends HttpServlet {

    private static final String COOKIE_NAME = "auth";

    private static final String LOGIN_PATH = "login.jsp";

    @Override
    public void init(){}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Cookie[] cookies = request.getCookies();

        if (cookies != null && Arrays.stream(cookies).anyMatch(c -> c.getName().equals(COOKIE_NAME))) {
            System.out.println("Verificando se possui o cookie...");

            Cookie cookie = new Cookie(COOKIE_NAME, null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        request.getSession().invalidate();
        response.sendRedirect(LOGIN_PATH);
    }

}
