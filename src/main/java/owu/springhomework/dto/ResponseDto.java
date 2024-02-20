package owu.springhomework.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResponseDto {

    private String accessToken;

    private String refreshToken;

}
