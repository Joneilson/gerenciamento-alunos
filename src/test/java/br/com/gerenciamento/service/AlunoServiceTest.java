package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void getByIdExistente() {
        Aluno aluno = new Aluno("Vinicius Almeida", "123456", Curso.ADMINISTRACAO, Turno.NOTURNO, Status.ATIVO);
        Aluno salvo = this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(salvo.getId());
        Assert.assertNotNull(alunoRetorno);
        Assert.assertEquals("Vinicius Almeida", alunoRetorno.getNome());
    }

    @Test
    public void salvarSemNome() {
        Aluno aluno = new Aluno();
        aluno.setMatricula("123456");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            serviceAluno.save(aluno);
        });
    }

    @Test
    public void atualizarNomeAluno() {
        Aluno aluno = new Aluno("Maria", "2025001", Curso.DIREITO, Turno.MATUTINO, Status.ATIVO);
        Aluno salvo = serviceAluno.save(aluno);

        salvo.setNome("Maria Clara");
        Aluno atualizado = serviceAluno.save(salvo);

        Assert.assertEquals("Maria Clara", atualizado.getNome());
    }

    @Test
    public void buscarAlunoInexistente() {
        Aluno aluno = serviceAluno.getById(999L);
        Assert.assertNull(aluno);
    }
}
