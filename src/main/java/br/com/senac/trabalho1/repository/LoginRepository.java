package br.com.senac.trabalho1.repository;

import br.com.senac.trabalho1.entity.Usuario;

import java.util.ArrayList;
import java.util.List;

public class LoginRepository {

    public LoginRepository(){}

    public List<Usuario> carregarUsuarios() {
        List<Usuario> usuariosEntity = new ArrayList<>();
        usuariosEntity.add(new Usuario(1, "admin", "admin123"));
        usuariosEntity.add(new Usuario(2, "ivo", "ivo123"));
        usuariosEntity.add(new Usuario(3, "thiago", "thiago123"));
        usuariosEntity.add(new Usuario(4, "leozin", "leozin123"));
        return usuariosEntity;
    }

}
