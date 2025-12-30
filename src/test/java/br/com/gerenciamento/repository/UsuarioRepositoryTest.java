package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;

import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testFindByEmail() {
        Usuario usuario = new Usuario();
        usuario.setEmail("email@teste.com");
        usuario.setUser("user1");
        usuario.setSenha("123");
        usuarioRepository.save(usuario);

        Usuario encontrado = usuarioRepository.findByEmail("email@teste.com");
        Assert.assertNotNull(encontrado);
        Assert.assertEquals("user1", encontrado.getUser());
    }

    @Test
    public void testFindByEmailNaoEncontrado() {
        Usuario encontrado = usuarioRepository.findByEmail("naoexiste@email.com");
        Assert.assertNull(encontrado);
    }

    @Test
    public void testBuscarLoginSucesso() {
        Usuario usuario = new Usuario();
        usuario.setEmail("login@teste.com");
        usuario.setUser("loginUser");
        usuario.setSenha("senha123");
        usuarioRepository.save(usuario);

        Usuario logado = usuarioRepository.buscarLogin("loginUser", "senha123");
        Assert.assertNotNull(logado);
    }


    @Test
    public void testBuscarLoginFalhaSenha() {
        Usuario usuario = new Usuario();
        usuario.setEmail("falha@teste.com");
        usuario.setUser("falhaUser");
        usuario.setSenha("senhaCorreta");
        usuarioRepository.save(usuario);

        Usuario logado = usuarioRepository.buscarLogin("falhaUser", "senhaErrada");
        Assert.assertNull(logado);
    }
}
