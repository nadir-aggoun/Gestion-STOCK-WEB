package com.aggoun.MyFirstApp.Achat.Service;

import com.aggoun.MyFirstApp.Achat.Entity.User;
import com.aggoun.MyFirstApp.Achat.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Enregistrer un utilisateur avec un mot de passe haché
    public User registerUser(User user) {
        logger.info("Registering user: " + user.getUsername());
        // Hacher le mot de passe
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Trouver un utilisateur par son nom d'utilisateur
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Vérifier si un utilisateur existe avec cet email
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    // Vérifier les identifiants
    public boolean loginUser(String username, String plainPassword) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            boolean passwordMatches = passwordEncoder.matches(plainPassword, user.get().getPassword());
            if (passwordMatches) {
                logger.info("User login successful: " + username);
            } else {
                logger.warning("User login failed (incorrect password): " + username);
            }
            return passwordMatches;
        }
        logger.warning("User login failed (username not found): " + username);
        return false;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
