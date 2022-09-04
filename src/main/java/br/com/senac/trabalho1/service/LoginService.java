package br.com.senac.trabalho1.service;

import br.com.senac.trabalho1.entity.Usuario;
import br.com.senac.trabalho1.repository.LoginRepository;

public class LoginService {

    private LoginRepository loginRepository;

    public LoginService(){
        loginRepository = new LoginRepository();
    }

    public boolean validarLogin(Usuario usuario){
        return loginRepository.validaLogin(usuario);
    }

}
