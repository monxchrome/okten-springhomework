package owu.springhomework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import owu.springhomework.dto.PostDto;
import owu.springhomework.entity.Post;
import owu.springhomework.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Value;
import owu.springhomework.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    private final MailService mailService;

    @Value("${spring.mail.username}")
    private String mailFrom;

    public List<PostDto> getPosts(String topic) {
        return Optional
                .ofNullable(topic)
                .map(postRepository::findByTopic)
                .orElseGet(postRepository::findAll)
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    public PostDto createPost(PostDto postDto) {
        Post post = postMapper.fromDto(postDto);
        Post createdPost = postRepository.save(post);
        sendPostCreatedMail(createdPost);

        return postMapper.toDto(createdPost);
    }

    public List<PostDto> createALl(List<PostDto> postsDto) {
        List<Post> posts = postsDto.stream().map(postMapper::fromDto).toList();
        List<Post> createdPosts = postRepository.saveAll(posts);
        createdPosts.forEach(this::sendPostCreatedMail);

        return createdPosts.stream().map(postMapper::toDto).toList();
    }

    private void sendPostCreatedMail(Post post) {
        mailService.sendEmail(
                mailFrom,
                "New post created",
                "Post %s has been created with description: %s".formatted(
                        post.getTopic(),
                        post.getDescription()
                )
        );
    }

    public Optional<PostDto> getPostById(Long id) {
        return postRepository.findById(id).map(postMapper::toDto);
    }
}
