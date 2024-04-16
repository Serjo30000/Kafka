package com.apiService.apiMovieReviews.wrappers;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RestTemplateClient {
    private final RestTemplate template;

    public <T> T request(String url, Class<T> type, Object... uriVariables) {
        try {
            return template.getForObject(url, type, uriVariables);
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Error occurred during HTTP request: " + e.getMessage(), e);
        }
    }

    public <T> List<T> requestLst(String url, Class<T[]> type, Object... uriVariables) {
        try {
            T[] responseArray = template.getForObject(url, type, uriVariables);
            if (responseArray != null) {
                return List.of(responseArray);
            } else {
                return List.of();
            }
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Error occurred during HTTP request: " + e.getMessage(), e);
        }
    }

}
