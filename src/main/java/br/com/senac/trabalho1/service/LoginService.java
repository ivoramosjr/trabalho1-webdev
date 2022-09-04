package br.com.senac.trabalho1.service;

import br.com.senac.trabalho1.entity.Usuario;
import br.com.senac.trabalho1.repository.LoginRepository;

import java.util.List;

public class LoginService {

    private LoginRepository loginRepository;

    public LoginService(){
        loginRepository = new LoginRepository();
    }

    public boolean validarLogin(Usuario usuario){
        return validaLogin(usuario);
    }


    private boolean validaLogin(Usuario usuario){
        List<Usuario> usuarios = loginRepository.carregarUsuarios();

        if(usuario.getLogin() == null || usuario.getSenha() == null)
            return false;

        if(usuario.getLogin().equals("") || usuario.getSenha().equals(""))
            return false;

        return usuarios.stream()
                .anyMatch(u -> u.getLogin().equals(usuario.getLogin())
                        && u.getSenha().equals(usuario.getSenha()));
    }

}
