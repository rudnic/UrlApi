package com.example.urlapi.controllers;

import com.example.urlapi.HashingDigest;
import com.example.urlapi.entities.Urls;
import com.example.urlapi.entities.User;
import com.example.urlapi.services.UrlService;
import com.example.urlapi.services.UserService;
import org.hashids.Hashids;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private UrlService urlService;

    @GetMapping("/gen-new-user")
    public ResponseEntity<?> getKey() throws NoSuchAlgorithmException {

        User user = new User();
        userService.save(user);

        Map<String, String> res = new HashMap<>();
        res.put("id", user.getId());
        res.put("secret-key", user.getSecretKey());
        res.put("signature", HashingDigest.doHashDigest(user.getId(), user.getSecretKey()));

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/do-shortly")
    public ResponseEntity<?> getShort(@RequestHeader("Signature") String authentication,
                                           @RequestParam(name = "id") String id,
                                           @RequestParam(name = "url") String url) throws NoSuchAlgorithmException {

        if (!userService.validateSignature(id, authentication)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Map<String, String> res = new HashMap<>();
        String token = new Hashids(url + id).encode(6);

        if (urlService.getUrlByToken(token) != null) {
            res.put(url, token);
            return new ResponseEntity<>(res, HttpStatus.FOUND);
        }

        urlService.createUrlToken(new Urls(url, token, id));
        res.put(url, token);
        return new ResponseEntity<>(res, HttpStatus.CREATED);


    }

    @GetMapping("/{token}")
    public ResponseEntity<?> redirectToSourceUrl(@PathVariable String token) {

        String url = urlService.getUrlByToken(token);

        if (url == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        urlService.incCountClicks(token);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
    }

    @GetMapping("/my-urls")
    public ResponseEntity<?> showUserUrls(@RequestHeader("Signature") String authentication,
                                          @RequestParam(name = "id") String id) throws NoSuchAlgorithmException {

        if (!userService.validateSignature(id, authentication)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Map<String, Map<String, String>> urlPairs = new HashMap<>();

        for (String token : urlService.getUsersUrlsTokens(id)) {

            Map<String, String> urlInfo = new HashMap<>();
            urlInfo.put("token", token);
            urlInfo.put("clicks_count", urlService.getCountClicks(token).toString());

            urlPairs.put(urlService.getUrlByToken(token), urlInfo);
        }

        return new ResponseEntity<>(urlPairs, HttpStatus.OK);

    }

    @DeleteMapping("/{token}")
    public ResponseEntity<?> deleteUrlToken(@RequestHeader("Signature") String authentication,
                                            @RequestParam(name = "id") String id,
                                            @PathVariable String token) throws NoSuchAlgorithmException {

        if (!userService.validateSignature(id, authentication)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (urlService.getUrlByToken(token) == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        urlService.deleteUrlToken(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
