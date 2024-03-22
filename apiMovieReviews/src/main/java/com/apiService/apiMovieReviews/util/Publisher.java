package com.apiService.apiMovieReviews.util;

import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class Publisher {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;

    public ResponseEntity<MessageRes> send(String topic, String key, Object obj) {
        try {
            var s = mapper.writeValueAsString(obj);
            kafkaTemplate.send(topic, key, s).get();
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new MessageRes("Added data successfully"));
        } catch (JsonProcessingException e) {

            log.error("Json error", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new MessageRes("Unprocessable Content"));

        } catch (InterruptedException | ExecutionException e) {

            log.error("Server error", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageRes("Internal server error"));

        }
    }
}
