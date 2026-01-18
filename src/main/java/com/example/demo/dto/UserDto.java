package com.example.demo.dto;

import com.example.demo.Role;
import lombok.Data;

@Data
public class UserDto {
    private long id;
    private String username;
    private String password;
    private Role role;

}
