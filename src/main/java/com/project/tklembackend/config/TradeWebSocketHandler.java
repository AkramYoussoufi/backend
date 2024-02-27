package com.project.tklembackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.tklembackend.dto.MessageDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TradeWebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    public void sendMessage(MessageDTO messageDTO) throws IOException {
        TextMessage message = new TextMessage(objectMapper.writeValueAsString(messageDTO));
        for (WebSocketSession session : sessions) {
            session.sendMessage(message);
        }
    }
}
