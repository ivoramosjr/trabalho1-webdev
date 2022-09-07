package br.com.senac.trabalho1.security;

import br.com.senac.trabalho1.entity.Usuario;

import java.util.Arrays;
import java.util.List;

public final class Encriptador {

    public static String encriptarConteudo(Usuario usuario){
        char[] login = usuario.getLogin().toCharArray();
        char[] senha = usuario.getSenha().toCharArray();
        String loginCriptografado = "";
        String senhaCriptografada = "";

        for(char letra: login){
            loginCriptografado = loginCriptografado.concat((int) letra +"%");
        }

        for(char letra: senha){
            senhaCriptografada = senhaCriptografada.concat((int) letra +"%");
        }

        return loginCriptografado+"-"+senhaCriptografada;
    }

    public static Usuario desencriptaConteudo(String conteudo){
        List<String> conteudos = Arrays.asList(conteudo.split("-"));

        String[] login = conteudos.get(0).split("%");
        String[] senha = conteudos.get(1).split("%");
        String loginDescriptografado = "";
        String senhaDescriptografada = "";

        for(String numero: login){
            char digito = (char)Integer.parseInt(numero, 10);
            loginDescriptografado = loginDescriptografado.concat(Character.toString(digito));
        }
        for(String numero: senha){
            char digito = (char)Integer.parseInt(numero, 10);
            senhaDescriptografada = senhaDescriptografada.concat(Character.toString(digito));
        }

        Usuario usuario = new Usuario();
        usuario.setLogin(loginDescriptografado);
        usuario.setSenha(senhaDescriptografada);

        return usuario;
    }

}
