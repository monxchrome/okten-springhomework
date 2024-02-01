package owu.springhomework.com.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ErrorDto {

    private Long timestamp;

    private String details;

    private Map<String, String> errors;
}
