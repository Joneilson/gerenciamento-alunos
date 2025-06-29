package br.com.gerenciamento.service;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario usuarioService; // Corrigido o nome do bean

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void salvarUsuarioComSucesso() throws Exception {
        Usuario usuario = new Usuario("teste@email.com", "usuarioTeste", "senha123");
        Usuario salvo = usuarioService.salvarUsuario(usuario);
        Assert.assertNotNull(salvo.getId());
    }

    @Test(expected = ConstraintViolationException.class)
    public void salvarUsuarioSemUser() throws Exception {
        Usuario usuario = new Usuario("teste2@email.com", null, "senha123");
        usuarioService.salvarUsuario(usuario);
    }

    @Test
    public void buscarUsuarioPorUser() {
        Usuario usuario = new Usuario("buscar@email.com", "usuarioBuscar", "senha123");
        usuarioRepository.save(usuario);

        Optional<Usuario> encontrado = usuarioRepository.findByUser("usuarioBuscar");
        Assert.assertTrue(encontrado.isPresent());
    }

    @Test
    public void buscarUsuarioInexistente() {
        Optional<Usuario> encontrado = usuarioRepository.findByUser("naoExiste");
        Assert.assertTrue(encontrado.isEmpty());
    }
}
