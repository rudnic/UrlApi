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
}
