package com.example.mitra.client.service;

import com.example.mitra.client.dto.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MessageRestTemplate {

    private final String url;
    private final RestTemplate template;

    public MessageRestTemplate(@Value("${message.gateway.rest.url}") String url, RestTemplate template) {
        this.url = url;
        this.template = template;
    }

    public void post(Message message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Message> request = new HttpEntity<>(message, headers);
        template.postForObject(String.format(url, ""), request, Void.class);
    }

    public List<Message> get(LocalDateTime begin, LocalDateTime end) {
        Message[] messages = template.getForObject(url + "?begin={begin}&end={end}", Message[].class, begin, end);
        return messages == null ? Collections.EMPTY_LIST : Arrays.asList(messages);
    }

    public Message get(Long id) {
        return template.getForObject(url + "/{id}", Message.class, id);
    }
}
