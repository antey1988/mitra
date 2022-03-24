package com.example.mitra.server.repo;

import com.example.mitra.server.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByCreateAtGreaterThanAndCreateAtLessThan(LocalDateTime begin , LocalDateTime end);
    List<Message> findAllByCreateAtGreaterThan(LocalDateTime begin );
    List<Message> findAllByCreateAtLessThan(LocalDateTime end);
}
