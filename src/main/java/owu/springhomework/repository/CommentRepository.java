package owu.springhomework.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import owu.springhomework.entity.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, ObjectId> {

    List<Comment> findAllByPostId(Long postId);
}
