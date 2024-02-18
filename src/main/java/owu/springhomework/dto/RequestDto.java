package owu.springhomework.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
