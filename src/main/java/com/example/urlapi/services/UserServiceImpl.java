package com.example.urlapi.services;

import com.example.urlapi.HashingDigest;
import com.example.urlapi.entities.User;
import com.example.urlapi.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public String findSecretById(String id) {
        return userRepository.findSecretById(id);
    }

    @Override
    public boolean validateSignature(String id, String signature) throws NoSuchAlgorithmException {
        String secretKey = findSecretById(id);
        if (secretKey == null) {
            return false;
        }
        String expectedDigest = HashingDigest.doHashDigest(id, secretKey);
        if (signature.equals(expectedDigest)) {
            return true;
        }

        return false;
    }
}
