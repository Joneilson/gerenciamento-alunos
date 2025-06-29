package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)  // ESSENCIAL para rodar com Spring
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void salvarUsuario() {
        Usuario usuario = new Usuario("marcos@email.com", "marcos", "senha");
        Usuario salvo = usuarioRepository.save(usuario);
        Assert.assertNotNull(salvo.getId());
    }

    @Test
    public void deletarUsuario() {
        Usuario usuario = usuarioRepository.save(new Usuario("aline@email.com", "aline", "senha123"));
        usuarioRepository.delete(usuario);
        Assert.assertTrue(usuarioRepository.findByUser("aline").isEmpty());
    }

    @Test
    public void atualizarSenha() {
        Usuario usuario = usuarioRepository.save(new Usuario("joana@email.com", "joana", "123"));
        usuario.setSenha("nova123");
        Usuario atualizado = usuarioRepository.save(usuario);
        Assert.assertEquals("nova123", atualizado.getSenha());
    }

    @Test
    public void encontrarUsuarioPorLogin() {
        usuarioRepository.save(new Usuario("paulo@email.com", "paulo", "abc"));
        Assert.assertTrue(usuarioRepository.findByUser("paulo").isPresent());
    }
}