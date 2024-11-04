package com.vhProject.Dto;

import lombok.Data;

@Data //No need to make getter setter
public class LoginDto {
    private String email;
    private String password;

    // Constructor, if necessary
    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setters, if necessary
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
