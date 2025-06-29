package br.com.gerenciamento.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotNull(message = "Email não pode ser nulo")
    private String email;

    @Size(min = 3, max = 20, message = "O usuário precisa conter entre 3 a 20 caracteres")
    @NotNull(message = "Usuário não pode ser nulo")
    @Column(name = "user", unique = true) // importante para login único
    private String user;

    @NotNull(message = "Senha não pode ser nula")
    private String senha;

    public Usuario() {
    }

    public Usuario(String email, String user, String senha) {
        this.email = email;
        this.user = user;
        this.senha = senha;
    }

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
