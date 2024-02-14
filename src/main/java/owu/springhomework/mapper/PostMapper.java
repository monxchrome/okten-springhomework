package owu.springhomework.mapper;

import org.springframework.stereotype.Component;
import owu.springhomework.dto.PostDto;
import owu.springhomework.entity.Post;

@Component
public class PostMapper {

    public PostDto toDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .topic(post.getTopic())
                .description(post.getDescription())
                .build();
    }

    public Post fromDto(PostDto postDto) {
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTopic(postDto.getTopic());
        post.setDescription(postDto.getDescription());

        return post;
    }
}
