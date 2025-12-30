package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void testSalvarUsuario() throws Exception {
        mockMvc.perform(post("/salvarUsuario")
                .param("email", "novo@teste.com")
                .param("user", "novousuario")
                .param("senha", "123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testLoginSucesso() throws Exception {
        // Criar usuário preliminarmente através do service para garantir hash correto
        Usuario usuario = new Usuario();
        usuario.setEmail("login@teste.com");
        usuario.setUser("usuarioLogin");
        usuario.setSenha("senha123");
        serviceUsuario.salvarUsuario(usuario);

        mockMvc.perform(post("/login")
                .param("user", "usuarioLogin")
                .param("senha", "senha123")) // Envia a senha crua, o controller fará o hash
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"))
                .andExpect(request().sessionAttribute("usuarioLogado", org.hamcrest.Matchers.notNullValue()));
    }

    @Test
    public void testLoginFalhaSenhaIncorreta() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("erro@teste.com");
        usuario.setUser("usuarioErro");
        usuario.setSenha("senhaCerta");
        serviceUsuario.salvarUsuario(usuario);

        mockMvc.perform(post("/login")
                .param("user", "usuarioErro")
                .param("senha", "senhaErrada"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/cadastro")) // O código atual redireciona para cadastro
                .andExpect(model().attributeDoesNotExist("msg")); // A mensagem é perdida no código atual
    }

    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(post("/logout"))
                .andExpect(status().isOk()) // O método logout retorna login() que renderiza a view
                .andExpect(view().name("login/login"))
                .andExpect(request().sessionAttributeDoesNotExist("usuarioLogado"));
    }
}
