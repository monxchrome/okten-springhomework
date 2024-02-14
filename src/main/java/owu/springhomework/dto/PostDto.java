package owu.springhomework.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;
import owu.springhomework.util.View;

@Data
@Builder
public class PostDto {

    @JsonView(View.Internal.class)
    private Long id;

    @JsonView(View.Internal.class)
    private String topic;

    @JsonView(View.External.class)
    private String description;
}
