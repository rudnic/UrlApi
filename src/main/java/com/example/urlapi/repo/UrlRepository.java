package com.example.urlapi.repo;

import com.example.urlapi.entities.Urls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Repository
public interface UrlRepository extends JpaRepository<Urls, Long> {

    @Query("select u.token from Urls u where u.sourceUrl = ?1")
    public String getUrlToken(String url);

    @Query("select u.sourceUrl from Urls u where u.token = ?1")
    public String getUrlByToken(String token);

    @Query("select u.token from Urls u where u.userId = ?1")
    public List<String> getUsersUrls(String userId);

    @Query("update Urls u set u.countClicks = u.countClicks + 1 where u.token = ?1")
    @Transactional
    @Modifying
    public void incCountClicks(String token);

    @Query("select u.countClicks from Urls u where u.token = ?1")
    public Integer getCountClicks(String token);

    @Query("delete from Urls u where u.token = ?1")
    @Modifying
    @Transactional
    public void deleteUrlToken(String token);
}
