package com.cusca.auth.services;

import com.cusca.auth.models.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<UserModel> users;

    @PostConstruct
    public void loadUsers() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("users.json");
            if (inputStream == null) {
                throw new RuntimeException("Archivo users.json no encontrado");
            }
            users = Arrays.asList(objectMapper.readValue(inputStream, UserModel[].class));
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo JSON", e);
        }
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public UserModel findByUsernameAndPassword(String username, String password) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
