package faby.mind_link.controller;

import faby.mind_link.dto.UserLoginDTO;
import faby.mind_link.dto.UserSignupDTO;
import faby.mind_link.entity.User;
import faby.mind_link.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody UserSignupDTO dto) {
        try {
            User createdUser = userService.signupUser(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO dto) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
            );
            // Se arriva qui, login riuscito
            System.out.println("Login OK");
            return ResponseEntity.ok("Login avvenuto con successo");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Credenziali errate");
        }
    }
}
