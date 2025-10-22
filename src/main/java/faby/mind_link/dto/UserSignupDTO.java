package faby.mind_link.dto;


import lombok.Data;

@Data
public class UserSignupDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
