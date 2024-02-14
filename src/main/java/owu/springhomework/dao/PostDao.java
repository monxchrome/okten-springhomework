package owu.springhomework.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import owu.springhomework.entity.Post;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostDao {

    private final EntityManager entityManager;

    public List<Post> getPosts() {
        TypedQuery<Post> query = entityManager.createQuery("select p from Post p", Post.class);

        return query.getResultList();
    }

    @Transactional
    public Post savePost(Post post) {
        entityManager.persist(post);

        return post;
    }
}
