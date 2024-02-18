package owu.springhomework.handler;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import owu.springhomework.repository.WebSocketSessionRepository;
import owu.springhomework.service.CommentService;

@Component
@Slf4j
@RequiredArgsConstructor
public class CommentHandler extends TextWebSocketHandler {

    private final CommentService commentService;

    private final WebSocketSessionRepository webSocketSessionRepository;

    @Override
    public void afterConnectionEstablished(@NonNull  WebSocketSession session) throws Exception {
        log.info("New connection");
        webSocketSessionRepository.addSession(session);
    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) {
        String commentText = message.getPayload();
        log.info("Got new comment: {}", commentText);
        commentService.saveComment(1L, commentText);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        log.info("Connection has been closed");
        webSocketSessionRepository.removeSession(session);
    }
}
