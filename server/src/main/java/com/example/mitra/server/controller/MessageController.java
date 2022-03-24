package com.example.mitra.server.controller;

import com.example.mitra.server.model.Message;
import com.example.mitra.server.repo.MessageRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class MessageController {

    private final MessageRepository repository;

    public MessageController(MessageRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/messages")
    public void createMessage(@RequestBody Message message) {
        if (message.getCreateAt() == null) {
            message.setCreateAt(LocalDateTime.now());
        }
        repository.save(message);
    }

    @GetMapping("/messages")
    public List<Message> getListMessages(@RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime begin,
                                         @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        if (begin == null && end == null) {
            return repository.findAll();
        }
        if (begin != null && end == null) {
            return repository.findAllByCreateAtGreaterThan(begin);
        }
        if (begin == null) {
            return repository.findAllByCreateAtLessThan(end);
        }
        return repository.findAllByCreateAtGreaterThanAndCreateAtLessThan(begin, end);
    }

    @GetMapping("/messages/{id}")
    public Message getMessageById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }
}
