package br.com.gerenciamento.repository;

import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.enums.Status;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {
    @Autowired
    private AlunoRepository alunoRepository;


    @Test
    public void testSalvarEBuscarPorId() {
        Aluno aluno = criarAluno("João Teste", Status.ATIVO);
        Aluno salvo = alunoRepository.save(aluno);

        Aluno encontrado = alunoRepository.findById(salvo.getId()).orElse(null);

        Assert.assertNotNull(encontrado);
        Assert.assertEquals("João Teste", encontrado.getNome());
    }

    @Test
    public void testFindByStatusAtivo() {
        alunoRepository.save(criarAluno("Ativo 1", Status.ATIVO));
        alunoRepository.save(criarAluno("Inativo 1", Status.INATIVO));

        List<Aluno> ativos = alunoRepository.findByStatusAtivo();

        Assert.assertFalse(ativos.isEmpty());
        for (Aluno a : ativos) {
            Assert.assertEquals(Status.ATIVO, a.getStatus());
        }
    }

    @Test
    public void testFindByStatusInativo() {
        alunoRepository.save(criarAluno("Ativo 2", Status.ATIVO));
        alunoRepository.save(criarAluno("Inativo 2", Status.INATIVO));

        List<Aluno> inativos = alunoRepository.findByStatusInativo();

        Assert.assertFalse(inativos.isEmpty());
        for (Aluno a : inativos) {
            Assert.assertEquals(Status.INATIVO, a.getStatus());
        }
    }

    @Test
    public void testFindByNomeContainingIgnoreCase() {
        alunoRepository.save(criarAluno("Maria Silva", Status.ATIVO));
        alunoRepository.save(criarAluno("Mariana Souza", Status.ATIVO));
        alunoRepository.save(criarAluno("Pedro Santos", Status.ATIVO));

        List<Aluno> resultados = alunoRepository.findByNomeContainingIgnoreCase("mari");

        Assert.assertTrue(resultados.size() >= 2);
        for (Aluno a : resultados) {
            Assert.assertTrue(a.getNome().toLowerCase().contains("mari"));
        }
    }

    private Aluno criarAluno(String nome, Status status) {
        Aluno aluno = new Aluno();
        aluno.setNome(nome);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setTurno(Turno.MATUTINO);
        aluno.setStatus(status);
        aluno.setMatricula("MAT" + System.currentTimeMillis());
        return aluno;
    }
}
