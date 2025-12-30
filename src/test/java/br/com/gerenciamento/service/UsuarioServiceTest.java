package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.util.Util;

import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {
    @Autowired
    private ServiceUsuario serviceUsuario;


    @Test
    public void testSalvarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("novo@teste.com");
        usuario.setUser("novoUser");
        usuario.setSenha("123456");

        serviceUsuario.salvarUsuario(usuario);
        Assert.assertNotNull(usuario.getId());
    }

   
    @Test(expected = EmailExistsException.class)
    public void testSalvarUsuarioEmailDuplicado() throws Exception {
        Usuario u1 = new Usuario();
        u1.setEmail("unico@teste.com");
        u1.setUser("userSucesso");
        u1.setSenha("123");
        serviceUsuario.salvarUsuario(u1);

        Usuario u2 = new Usuario();
        u2.setEmail("unico@teste.com"); 
        u2.setUser("u2");
        u2.setSenha("456");
        
        serviceUsuario.salvarUsuario(u2); 
    }

    
    @Test
    public void testLoginUserSucesso() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("login.service@teste.com");
        usuario.setUser("loginService");
        usuario.setSenha("senha123"); 
        
        serviceUsuario.salvarUsuario(usuario); 

        
        
        Usuario logado = serviceUsuario.loginUser("loginService", Util.md5("senha123"));
        Assert.assertNotNull(logado);
    }

    
    @Test
    public void testLoginUserFalha() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("falha.service@teste.com");
        usuario.setUser("falhaService");
        usuario.setSenha("123");
        serviceUsuario.salvarUsuario(usuario);

        Usuario logado = serviceUsuario.loginUser("falhaService", Util.md5("errada"));
        Assert.assertNull(logado);
    }
}
