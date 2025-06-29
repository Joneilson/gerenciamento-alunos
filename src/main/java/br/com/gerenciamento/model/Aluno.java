package br.com.gerenciamento.model;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    @Size(min = 5, max = 35, message = "O Nome deve conter entre 5 a 35 caracteres")
    @NotBlank(message = "O nome não pode ser vazio")
    @NotNull(message = "O nome é obrigatório")
    private String nome;

    @Column(name = "matricula", unique = true)
    @NotNull(message = "A matrícula é obrigatória")
    @Size(min = 3, message = "É necessário gerar o número de matrícula com ao menos 3 caracteres")
    private String matricula;

    @Column(name = "curso")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "O curso é obrigatório")
    private Curso curso;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "O status é obrigatório")
    private Status status;

    @Column(name = "turno")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "O turno é obrigatório")
    private Turno turno;

    public Aluno() {
    }

    public Aluno(String nome, String matricula, Curso curso, Turno turno, Status status) {
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
        this.turno = turno;
        this.status = status;
    }

    // getters e setters (mantidos iguais)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public Turno getTurno() { return turno; }
    public void setTurno(Turno turno) { this.turno = turno; }
}
