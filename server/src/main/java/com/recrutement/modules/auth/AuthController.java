package com.recrutement.modules.auth;

import com.recrutement.exceptions.*;
import com.recrutement.modules.auth.httpRequest.SignupRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import com.recrutement.modules.auth.httpResponse.SignupResponse;
import com.recrutement.modules.auth.service.IAuthService;
import com.recrutement.modules.auth.httpRequest.AuthRequest;
import com.recrutement.modules.auth.httpResponse.AuthResponse;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private IAuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthRequest authRequest,
            HttpServletResponse res
    ) {
        try {
            AuthResponse authResponse = authService.signin(authRequest);
            if (authResponse.getIsMFA()) {
                return new ResponseEntity<>(authResponse.getMFAId(), HttpStatus.OK);
            } else {
                res.setHeader("Access-Control-Allow-Credentials", "true");
                res.addCookie(accessTokenCookie(authResponse));
                res.addCookie(refreshTokenCookie(authResponse));
                return new ResponseEntity<>(authResponse.getUser(), HttpStatus.OK);
            }
        } catch (BadCredentialsException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>("Please check your credentials", HttpStatus.NOT_FOUND);
        } catch (UserNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DeactivatedAccountException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>("Login failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signin-mfa")
    public ResponseEntity<?> authenticateMFA(
            @RequestBody String MFAToken,
            HttpServletResponse res
    ) {
        try {
            AuthResponse authResponse = authService.MFASignin(MFAToken);
            res.setHeader("Access-Control-Allow-Credentials", "true");
            res.addCookie(accessTokenCookie(authResponse));
            res.addCookie(refreshTokenCookie(authResponse));
            return new ResponseEntity<>(authResponse.getUser(), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>("Please check your credentials", HttpStatus.NOT_FOUND);
        } catch (DataNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (TokenExpiredException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>("Login failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/resend-code/{tokenId}")
    public ResponseEntity<?> resendCode(@PathVariable Long tokenId) {
        try{
            Long authResponse = authService.resendCode(tokenId);
            return ResponseEntity.ok(authResponse);
        } catch (DataNotFoundException e){
            logger.error(e.getMessage());
            return new ResponseEntity<>("Not authenticated", HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity<>("Resend email code failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest user) {
        try {
            authService.signup(user);
            SignupResponse signupResponse = new SignupResponse();
            signupResponse.setSuccess(true);
            //signupResponse.setEmailSent(authService.sendAccountActivation(userResponse));
            return ResponseEntity.ok(signupResponse);
        } catch (UserAlreadyExistsException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>("User signup failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletResponse res) {
        try {
            AuthResponse authResponse = authService.refreshToken();
            res.setHeader("Access-Control-Allow-Credentials", "true");
            res.addCookie(accessTokenCookie(authResponse));
            res.addCookie(refreshTokenCookie(authResponse));
            return ResponseEntity.ok(true);
        } catch (UserNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (TokenExpiredException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Cookie accessTokenCookie(AuthResponse authResponse) {
        return setCookie(
                "accessToken",
                authResponse.getAccessToken().getToken(),
                authResponse.getAccessToken().getExpiration()
        );
    }

    private Cookie refreshTokenCookie(AuthResponse authResponse) {
        return setCookie(
                "refreshToken",
                authResponse.getRefreshToken().getToken(),
                authResponse.getRefreshToken().getExpiration()
        );
    }

    private Cookie setCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        return cookie;
    }

}
