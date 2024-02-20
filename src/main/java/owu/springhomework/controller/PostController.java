package owu.springhomework.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import owu.springhomework.dto.PostDto;
import owu.springhomework.service.PostService;
import owu.springhomework.service.PostUploadService;
import owu.springhomework.util.View;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final PostUploadService postUploadService;

    @JsonView(View.Internal.class)
    @GetMapping("/internal/posts")
    public ResponseEntity<List<PostDto>> getPostsInternal(@RequestParam(required = false) String topic) {
        return ResponseEntity.ok(postService.getPosts(topic));
    }

    @JsonView(View.External.class)
    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getPosts(@RequestParam(required = false) String topic) {
        return ResponseEntity.ok(postService.getPosts(topic));
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Optional<PostDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @Secured("seller")
    @PostMapping("/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postDto) {
        return ResponseEntity.ok().body(postService.createPost(postDto));
    }

    @PostMapping("/posts/upload")
    public ResponseEntity<List<PostDto>> uploadPosts(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(postUploadService.uploadPosts(file));
    }
}
