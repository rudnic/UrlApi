package com.example.urlapi.services;

import com.example.urlapi.entities.Urls;
import com.example.urlapi.repo.UrlRepository;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UrlServiceImpl implements UrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Override
    public void createUrlToken(Urls url) {
        urlRepository.save(url);
    }

    @Override
    public String getUrlToken(String url) {
        return urlRepository.getUrlToken(url);
    }

    @Override
    public String getUrlByToken(String token) {
        return urlRepository.getUrlByToken(token);

    }

    @Override
    public List<String> getUsersUrlsTokens(String userId) {
        return urlRepository.getUsersUrls(userId);
    }

    @Override
    public void incCountClicks(String token) {
        urlRepository.incCountClicks(token);
    }

    @Override
    public Integer getCountClicks(String token) {
        return urlRepository.getCountClicks(token);
    }

    @Override
    public void deleteUrlToken(String token) {
        urlRepository.deleteUrlToken(token);
    }
}
