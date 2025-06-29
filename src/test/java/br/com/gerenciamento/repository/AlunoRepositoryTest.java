package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.*;
import br.com.gerenciamento.model.Aluno;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void salvarAluno() {
        Aluno aluno = new Aluno("Ana Silva", "112233", Curso.DIREITO, Turno.MATUTINO, Status.ATIVO);
        Aluno salvo = alunoRepository.save(aluno);
        Assert.assertNotNull(salvo.getId());
    }

    @Test
    public void buscarPorMatricula() {
        alunoRepository.save(new Aluno("Bruno Costa", "998877", Curso.CONTABILIDADE, Turno.MATUTINO, Status.ATIVO));
        Aluno aluno = alunoRepository.findByMatricula("998877");
        Assert.assertNotNull(aluno);
    }

    @Test
    public void removerAluno() {
        Aluno aluno = alunoRepository.save(new Aluno("Carlos Souza", "123789", Curso.DIREITO, Turno.NOTURNO, Status.ATIVO));
        alunoRepository.delete(aluno);
        Aluno encontrado = alunoRepository.findByMatricula("123789");
        Assert.assertNull(encontrado);
    }

    @Test
    public void listarTodosAlunos() {
        alunoRepository.save(new Aluno("Daniele Lima", "555666", Curso.INFORMATICA, Turno.NOTURNO, Status.ATIVO));
        Assert.assertFalse(alunoRepository.findAll().isEmpty());
    }
}
