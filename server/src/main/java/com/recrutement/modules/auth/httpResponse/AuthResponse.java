package com.recrutement.modules.auth.httpResponse;

import lombok.*;
import com.recrutement.modules.user.dto.UserDto;
import com.recrutement.security.jwt.JwtResponse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private UserDto user;
    private JwtResponse accessToken;
    private JwtResponse refreshToken;
    private String authority;
    private Boolean isMFA;
    private Long MFAId;
}
