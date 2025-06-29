package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.CriptoExistsException;
import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class ServiceUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvarUsuario(Usuario user) throws Exception {
        if (usuarioRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailExistsException("Este email já está cadastrado: " + user.getEmail());
        }

        try {
            user.setSenha(Util.md5(user.getSenha()));
        } catch (NoSuchAlgorithmException e) {
            throw new CriptoExistsException("Erro na criptografia da senha");
        }

        return usuarioRepository.save(user);
    }

    public Usuario loginUser(String user, String senha) {
        // Supondo que senha já venha criptografada, ou você deve criptografar aqui antes
        try {
            String senhaCripto = Util.md5(senha);
            return usuarioRepository.buscarLogin(user, senhaCripto);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
