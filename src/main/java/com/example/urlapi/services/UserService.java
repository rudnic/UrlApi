package com.example.urlapi.services;

import com.example.urlapi.entities.User;
import com.example.urlapi.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.NoSuchAlgorithmException;

public interface UserService {
    public void save(User user);
    public String findSecretById(String id);
    public boolean validateSignature(String id, String signature) throws NoSuchAlgorithmException;
}
