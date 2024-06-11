package com.example.diplom2.DTO;

import com.example.diplom2.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String email;

    private String role;
    private String name;
    private String surname;






    public UserDTO(Long id, String username, String name, String surname, String email) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
}