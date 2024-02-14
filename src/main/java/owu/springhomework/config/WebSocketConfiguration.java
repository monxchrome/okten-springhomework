package owu.springhomework.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import owu.springhomework.handler.CommentHandler;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final CommentHandler commentHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(commentHandler, "/ws/reviews");
    }
}
