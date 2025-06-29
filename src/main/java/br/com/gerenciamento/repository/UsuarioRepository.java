package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUser(String user);

    Usuario findByEmail(String email);

    // Supondo que você tenha um método customizado para login:
    @Query("SELECT u FROM Usuario u WHERE u.user = :user AND u.senha = :senha")
    Usuario buscarLogin(@Param("user") String user, @Param("senha") String senha);
}
