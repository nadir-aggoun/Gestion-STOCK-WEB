package com.aggoun.MyFirstApp.Achat.Repository;

import com.aggoun.MyFirstApp.Achat.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    // Méthode pour trouver un utilisateur par son email
    Optional<User> findByEmail(String email);

}
