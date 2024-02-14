package owu.springhomework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import owu.springhomework.entity.Comment;
import owu.springhomework.repository.CommentRepository;
import owu.springhomework.repository.WebSocketSessionRepository;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final WebSocketSessionRepository webSocketSessionRepository;

    public void saveComment(Long postId, String text) {
        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setText(text);

        commentRepository.save(comment);
        webSocketSessionRepository.sendToAll("New comment: %s".formatted(text));
    }
}
