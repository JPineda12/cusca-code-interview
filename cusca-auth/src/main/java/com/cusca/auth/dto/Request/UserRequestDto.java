package com.cusca.auth.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRequestDto {

    @NotBlank(message = "Username cannot be blank")
    @NotNull(message = "Username cannot be null")
    @Size(max=32)
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @NotNull(message = "Password cannot be null")
    @Size(min=4, message="Password length needs to be longer than 4")
    private String password;

    public UserRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
