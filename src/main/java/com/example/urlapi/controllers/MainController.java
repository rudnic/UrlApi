package com.example.urlapi.controllers;

import com.example.urlapi.HashingDigest;
import com.example.urlapi.entities.Urls;
import com.example.urlapi.entities.User;
import com.example.urlapi.services.UrlService;
import com.example.urlapi.services.UserService;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@Controller
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
            return new ResponseEntity<>("ok3", HttpStatus.UNAUTHORIZED);
        }

        Map<String, String> res = new HashMap<>();
        res.put("url", url);
        res.put("token", urlService.getUrlToken(url, id));

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{token}")
    public ResponseEntity<?> redirectToSourceUrl(@PathVariable String token) {
        String url = urlService.getUrlByToken(token).getSourceUrl();

        if (!url.startsWith("http")) {
            url = "http://" + url;
        }

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
    }

    @GetMapping("/my-urls")
    public ResponseEntity<?> showUserUrls(@RequestHeader("Signature") String authentication,
                                          @RequestParam(name = "id") String id) throws NoSuchAlgorithmException {

        if (!userService.validateSignature(id, authentication)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Map<String, String> urlPairs = new HashMap<>();

        for (String token : urlService.getUsersUrlsTokens(id)) {
            urlPairs.put(urlService.getUrlByToken(token).getSourceUrl(), token);
        }

        return new ResponseEntity<>(urlPairs, HttpStatus.OK);
    }


}
