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

    private final static String LOGIN_PATH = "login.html";

    public void init(){}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Cookie[] cookies = request.getCookies();

        if (cookies != null && Arrays.stream(cookies).anyMatch(c -> c.getName().equals(COOKIE_NAME))) {
            System.out.println("Verificando se possui o cookie...");

            Cookie cookie = Arrays.stream(cookies).filter(c -> c.getName().equals(COOKIE_NAME)).findFirst().get();

            cookie = new Cookie(COOKIE_NAME, null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        request.getSession().setAttribute("infos",null);
        response.sendRedirect(LOGIN_PATH);
    }

}
