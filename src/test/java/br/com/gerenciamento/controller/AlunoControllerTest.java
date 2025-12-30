package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void testInserirAlunoSucesso() throws Exception {
        mockMvc.perform(post("/InsertAlunos")
                .param("nome", "Carlos Silva")
                .param("curso", "INFORMATICA")
                .param("matricula", "ACA123")
                .param("turno", "NOTURNO")
                .param("status", "ATIVO"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void testInserirAlunoComErroValidacao() throws Exception {
        // O nome deve ter no mínimo 5 caracteres (definido na Model Aluno)
        mockMvc.perform(post("/InsertAlunos")
                .param("nome", "Ana") 
                .param("curso", "INFORMATICA")
                .param("matricula", "ACA123")
                .param("turno", "NOTURNO")
                .param("status", "ATIVO"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/formAluno"))
                .andExpect(model().attributeHasFieldErrors("aluno", "nome"));
    }

    @Test
    public void testListagemAlunos() throws Exception {
        // Salva um aluno para garantir que a lista não esteja vazia
        Aluno aluno = new Aluno();
        aluno.setNome("Teste Listagem");
        aluno.setCurso(Curso.DIREITO);
        aluno.setMatricula("LST001");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setStatus(Status.ATIVO);
        alunoRepository.save(aluno);

        mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/listAlunos"))
                .andExpect(model().attributeExists("alunosList"));
    }

    @Test
    public void testPesquisarAluno() throws Exception {
        // Prepara cenário
        Aluno aluno = new Aluno();
        aluno.setNome("Aluno Pesquisa");
        aluno.setCurso(Curso.BIOMEDICINA);
        aluno.setMatricula("PES001");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setStatus(Status.ATIVO);
        alunoRepository.save(aluno);

        mockMvc.perform(post("/pesquisar-aluno")
                .param("nome", "Pesquisa"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/pesquisa-resultado"))
                .andExpect(model().attributeExists("ListaDeAlunos"));
    }
}
