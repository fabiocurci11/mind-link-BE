package faby.mind_link.dto;


import lombok.Data;

@Data
public class UserSignupDTO {
    private String email;
    private String first_name;
    private String last_name;
    private String password;
}
