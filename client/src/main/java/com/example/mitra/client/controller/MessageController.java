package com.example.mitra.client.controller;

//import com.example.mitra.client.dto.Message;
import com.example.mitra.api.Message;
import com.example.mitra.client.service.MessageRestTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class MessageController {

    private final MessageRestTemplate service;

    public MessageController(MessageRestTemplate service) {
        this.service = service;
    }


    @PostMapping("/messages")
    public void createMessage(@RequestBody Message message) {
        service.post(message);
    }

    @GetMapping("/messages")
    public List<Message> getListMessages(@RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime begin,
                                         @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return service.get(begin, end);
    }

    @GetMapping("/messages/{id}")
    public Message getMessageById(@PathVariable Long id) {
        return service.get(id);
    }
}
