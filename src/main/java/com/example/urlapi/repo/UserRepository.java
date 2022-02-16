package com.example.urlapi.repo;

import com.example.urlapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u.secretKey from User u where u.id = ?1")
    public String findSecretById(String id);
}
