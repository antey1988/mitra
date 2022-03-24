package com.example.mitra.server.controller;

import com.example.mitra.server.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class MessageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    void createMessage() throws Exception {
        Message message = new Message();
        message.setContent("Тестое сообщение");
        message.setCreateAt(LocalDateTime.now());

        MvcResult mvcResult = mockMvc.perform(post("/messages")
                        .content(mapper.writeValueAsString(message))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn();


        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void getListMessages() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/messages")).andDo(print()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(200, response.getStatus());

        List<Message> actual = mapper.readerFor(Message.class).<Message>readValues(response.getContentAsString(StandardCharsets.UTF_8)).readAll();
        assertEquals(6, actual.size());
    }

    @Test
    void getMessageById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/messages/1")).andDo(print()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(200, response.getStatus());

        Message actual = mapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), Message.class);
        assertAll(
                () -> assertEquals(1L, actual.getId()),
                () -> assertEquals(LocalDateTime.parse("2022-03-22T00:00:00"), actual.getCreateAt()),
                () -> assertEquals("Раз", actual.getContent())
        );
    }
}