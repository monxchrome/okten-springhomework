package owu.springhomework.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import owu.springhomework.dto.PostDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostUploadService {

    private final PostService postService;

    private final ObjectMapper objectMapper;

    @Transactional
    @SneakyThrows
    public List<PostDto> uploadPosts(MultipartFile file) {
        List<PostDto> postsDto = objectMapper.readValue(file.getBytes(), new TypeReference<>() {});

        return postService.createALl(postsDto);
    }
}
