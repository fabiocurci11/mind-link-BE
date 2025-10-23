package faby.mind_link.dto;

public class JwtResponseDTO {
    private String token;
    private String message;
    private String email; // opzionale, utile per il frontend

    public JwtResponseDTO(String token, String message, String email) {
        this.token = token;
        this.message = message;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    public String getEmail() {
        return email;
    }
}
