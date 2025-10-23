package faby.mind_link.controller;

import faby.mind_link.dto.JwtResponseDTO;
import faby.mind_link.dto.UserLoginDTO;
import faby.mind_link.dto.UserResponseDTO;
import faby.mind_link.dto.UserSignupDTO;
import faby.mind_link.entity.User;
import faby.mind_link.security.JwtUtil;
import faby.mind_link.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserSignupDTO dto) {
        try {
            User createdUser = userService.signupUser(dto);

            // Mappa User -> UserResponseDTO
            UserResponseDTO response = new UserResponseDTO(
                    createdUser.getEmail(),
                    createdUser.getFirstName(),
                    createdUser.getLastName()
            );
            System.out.println("User "+ response.getEmail() + " registrato con successo");
            System.out.println(response.toString());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO dto) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
            );

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String token = jwtUtil.generateToken(userDetails.getUsername());

            JwtResponseDTO response = new JwtResponseDTO(
                    token,
                    "Login avvenuto con successo",
                    userDetails.getUsername()
            );
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Credenziali errate");
        }
    }


}
