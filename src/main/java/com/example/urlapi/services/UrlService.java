package com.example.urlapi.services;

import com.example.urlapi.entities.Urls;

import java.util.Map;

public interface UrlService {
    public void createUrlToken(String url);
    public String getUrlToken(String url, String userId);
    public Urls getUrlByToken(String token);
}
