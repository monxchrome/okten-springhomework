package owu.springhomework.com.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import owu.springhomework.com.util.View;

@Data
@Builder
public class CarDto {

    private Long id;

    @JsonView({View.Internal.class, View.External.class})
    private String model;

    @JsonView({View.Internal.class, View.External.class})
    private String producer;

    @JsonView(View.Internal.class)
    private Integer power;

    @JsonView({View.Internal.class, View.External.class})
    private MultipartFile photo;
}
