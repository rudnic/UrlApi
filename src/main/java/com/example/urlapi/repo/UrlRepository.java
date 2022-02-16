package com.example.urlapi.repo;

import com.example.urlapi.entities.Urls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UrlRepository extends JpaRepository<Urls, Long> {

    @Query("select u.token from Urls u where u.sourceUrl = ?1")
    public String getUrlToken(String url);

    @Query("select u from Urls u where u.token = ?1")
    public Urls getUrlByToken(String token);

    @Query("select u.token from Urls u where u.userId = ?1")
    public List<String> getUsersUrls(String userId);
}
