package owu.springhomework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import owu.springhomework.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTopic(String topic);
}
