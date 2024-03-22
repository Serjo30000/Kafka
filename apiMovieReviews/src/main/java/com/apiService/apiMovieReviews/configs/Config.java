package com.apiService.apiMovieReviews.configs;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
    @Bean
    RestTemplate template(){
        return new RestTemplate();
    }
    
    @Bean
    ProducerFactory<String,String> producerFactory(KafkaProperties p){
        return new DefaultKafkaProducerFactory<>(p.buildProducerProperties());
    }

    @Bean
    KafkaTemplate<String,String> kafkaTemplate(ProducerFactory<String,String> factory){
        return new KafkaTemplate<>(factory);
    }
}
