package com.enigma.tokopakedi.controller;

import lombok.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class PostController {
    private final RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity<?> getAllPost(){
        String url="https://17c4-2001-448a-2020-d35a-bcf8-3e81-f5c3-d41c.ngrok-free.app/articles";
        ResponseEntity<List<ArticleResponse>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ArticleResponse>>() {});
        return responseEntity;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ArticleResponse {
        private String id;
        private String title;
        private String body;
        private String author;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Article article){
        HttpEntity<Article> request = new HttpEntity<>(article);
        String url = "https://17c4-2001-448a-2020-d35a-bcf8-3e81-f5c3-d41c.ngrok-free.app/articles/"+article.getId();
        return restTemplate.exchange(url, HttpMethod.PUT, request, new ParameterizedTypeReference<>() {});

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Article {
        private String id;
        private String title;
        private String body;
        private String author;
    }


}
