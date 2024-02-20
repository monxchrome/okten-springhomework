package owu.springhomework.controller;

import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import owu.springhomework.dto.RefreshDto;
import owu.springhomework.dto.RequestDto;
import owu.springhomework.dto.ResponseDto;
import owu.springhomework.exception.AuthException;
import owu.springhomework.service.JwtService;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private static final long ACCESS_TOKEN_TTL_SECONDS = 30 ;

    private static final long REFRESH_TOKEN_TTL_SECONDS = 7 * 24 * 60 * 60;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;

    @PostMapping("/api/auth/signin")
    public ResponseEntity<ResponseDto> signIn(@RequestBody @Valid RequestDto requestDto) {
        Authentication authentication = UsernamePasswordAuthenticationToken
                .unauthenticated(requestDto.getUsername(), requestDto.getPassword());
        authenticationManager.authenticate(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(requestDto.getUsername());
        String accessToken = jwtService.generateAccessToken(userDetails, ACCESS_TOKEN_TTL_SECONDS);
        String refreshToken = jwtService.generateRefreshToken(userDetails, REFRESH_TOKEN_TTL_SECONDS);

        ResponseDto response = new ResponseDto();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/auth/refresh")
    public ResponseEntity<ResponseDto> refresh(@RequestBody @Valid RefreshDto refreshDto) {
        try {
            Date refreshTokenExpirationDate = jwtService.extractExpiration(refreshDto.getRefreshToken());

            if (refreshTokenExpirationDate.before(new Date())) {
                throw new AuthException("Refresh token expired");
            }

            String username = jwtService.extractUsername(refreshDto.getRefreshToken());
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String accessToken = jwtService.generateAccessToken(userDetails, ACCESS_TOKEN_TTL_SECONDS);
            Date accessTokenExpirationDate = jwtService.extractExpiration(refreshDto.getRefreshToken());

            ResponseDto response = new ResponseDto();
            response.setAccessToken(accessToken);

            if (refreshTokenExpirationDate.before(accessTokenExpirationDate)) {
                String refreshToken = jwtService.generateRefreshToken(userDetails, REFRESH_TOKEN_TTL_SECONDS);
                response.setRefreshToken(refreshToken);
            } else {
                response.setRefreshToken(refreshDto.getRefreshToken());
            }

            return ResponseEntity.ok(response);
        } catch (JwtException e) {
            throw new AuthException(e.getMessage(), e);
        }
    }
}
