package br.com.gerenciamento.service;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceAluno {

    @Autowired
    private AlunoRepository alunoRepository;

    public Aluno save(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public List<Aluno> findAll() {
        return alunoRepository.findAll();
    }

    public Aluno getById(Long id) {
        Optional<Aluno> alunoOpt = alunoRepository.findById(id);
        return alunoOpt.orElse(null);
    }

    public void deleteById(Long id) {
        alunoRepository.deleteById(id);
    }

    public List<Aluno> findByStatusAtivo() {
        return alunoRepository.findByStatusAtivo();
    }

    public List<Aluno> findByStatusInativo() {
        return alunoRepository.findByStatusInativo();
    }

    public List<Aluno> findByNomeContainingIgnoreCase(String nome) {
        return alunoRepository.findByNomeContainingIgnoreCase(nome);
    }
}
