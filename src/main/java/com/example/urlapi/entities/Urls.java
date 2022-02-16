package com.example.urlapi.entities;

import javax.persistence.*;

@Entity
@Table(name = "urls")
public class Urls {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "source_url")
    private String sourceUrl;

    private String token;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "count_clicks")
    private int countClicks;

    public Urls(String sourceUrl, String token, String userId) {
        this.sourceUrl = sourceUrl;
        this.token = token;
        this.userId = userId;
    }

    public Urls() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Urls{" +
                "id=" + id +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", token='" + token + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
