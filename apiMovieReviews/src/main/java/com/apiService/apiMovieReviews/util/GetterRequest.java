package com.apiService.apiMovieReviews.util;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetterRequest {
    private final RestTemplate template;

    public <T> ResponseEntity<T> request(String url, Map<String, Object> params, Class<T> type) {
        try {
            if (params != null) {
                return template.exchange(url, HttpMethod.GET, null, type, params);
            }
            return template.exchange(url, HttpMethod.GET, null, type);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity
                    .status(e.getStatusCode().value())
                    .build();
        }
    }

    public <T> ResponseEntity<List<T>> requestLst(String url, Map<String, Object> params, Class<T> type) {
        ParameterizedTypeReference<List<T>> kst = new ParameterizedTypeReference<List<T>>() {
        };
        try {
            if (params != null) {
                return template.exchange(url, HttpMethod.GET, null, kst, params);
            }
            return template.exchange(url, HttpMethod.GET, null, kst);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity
                    .status(e.getStatusCode().value())
                    .build();
        }
    }

}
