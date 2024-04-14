package com.apiService.apiMovieReviews.services;

import java.util.concurrent.ExecutionException;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.apiService.apiMovieReviews.dtos.MessageRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageSender {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;

    public MessageRes send(String topic, String key, Object obj) {
        try {
            var s = mapper.writeValueAsString(obj);
            kafkaTemplate.send(topic, key, s).get();
            return new MessageRes("Added data successfully");
        } catch (JsonProcessingException e) {
            log.error("Json error", e.getMessage());
            return new MessageRes("Unprocessable Content");
        } catch (InterruptedException | ExecutionException e) {
            log.error("Server error", e.getMessage());
            return new MessageRes("Internal server error");
        }
    }
}
