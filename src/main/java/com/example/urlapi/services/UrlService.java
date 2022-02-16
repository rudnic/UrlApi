package com.example.urlapi.services;

import com.example.urlapi.entities.Urls;

import java.util.List;
import java.util.Map;

public interface UrlService {
    public void createUrlToken(Urls url);
    public String getUrlToken(String url);
    public String getUrlByToken(String token);
    public List<String> getUsersUrlsTokens(String userId);
    public void incCountClicks(String token);
    public Integer getCountClicks(String token);
}
