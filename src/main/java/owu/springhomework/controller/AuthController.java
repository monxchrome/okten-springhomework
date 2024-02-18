package owu.springhomework.controller;

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
import owu.springhomework.dto.RequestDto;
import owu.springhomework.dto.ResponseDto;
import owu.springhomework.service.JwtService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;

    @PostMapping("/api/auth/signin")
    public ResponseEntity<ResponseDto> signIn(@RequestBody @Valid RequestDto requestDto) {
        Authentication authentication = UsernamePasswordAuthenticationToken
                .unauthenticated(requestDto.getUsername(), requestDto.getPassword());
        authenticationManager.authenticate(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(requestDto.getUsername());
        String token = jwtService.generateToken(userDetails);

        ResponseDto response = new ResponseDto();
        response.setToken(token);

        return ResponseEntity.ok(response);
    }
}
