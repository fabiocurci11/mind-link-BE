package faby.mind_link.repository;


import faby.mind_link.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Metodo per trovare un utente per email
    Optional<User> findByEmail(String email);

    // Controlla se un'email esiste già
    boolean existsByEmail(String email);
}

