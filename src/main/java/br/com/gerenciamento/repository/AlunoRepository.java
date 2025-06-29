package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    @Query("SELECT a FROM Aluno a WHERE a.status = 'ATIVO' ")
    List<Aluno> findByStatusAtivo();

    @Query("SELECT a FROM Aluno a WHERE a.status = 'INATIVO' ")
    List<Aluno> findByStatusInativo();

    List<Aluno> findByNomeContainingIgnoreCase(String nome);

    Aluno findByMatricula(String matricula);  // método necessário para testes
}
