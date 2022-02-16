package com.example.urlapi.services;

import com.example.urlapi.entities.Urls;

import java.util.List;
import java.util.Map;

public interface UrlService {
    public void createUrlToken(String url);
    public String getUrlToken(String url, String userId);
    public String getUrlByToken(String token);
    public List<String> getUsersUrlsTokens(String userId);
}
