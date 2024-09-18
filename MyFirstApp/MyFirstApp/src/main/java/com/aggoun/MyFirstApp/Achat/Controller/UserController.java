package com.aggoun.MyFirstApp.Achat.Controller;

import com.aggoun.MyFirstApp.Achat.Entity.User;
import com.aggoun.MyFirstApp.Achat.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Check if the email already exists
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("USERNAME already in use");
        }

        // Hash the user's password before saving
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Save the user
        userService.saveUser(user);  // Assuming you have a saveUser method in userService

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User loginRequest) {
        System.out.println("Username: " + loginRequest.getUsername());
        System.out.println("Password: " + loginRequest.getPassword());
        boolean isAuthenticated = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        Map<String, String> response = new HashMap<>();
        if (isAuthenticated) {
            response.put("message", "Login successful");
            return ResponseEntity.ok(response); // Renvoie l'objet JSON directement
        }

        response.put("message", "Invalid username or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); // Renvoie l'objet JSON directement
    }

}
