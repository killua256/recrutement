package com.recrutement.security.jwt;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.recrutement.modules.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider tokenProvider;
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @SneakyThrows
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    )
            throws ServletException, IOException {
        try {

            String jwt = "";
            if(request.getRequestURI().endsWith("auth/refresh-token")){
                jwt = getJwt(request, "refreshToken");
            } else {
                jwt = getJwt(request, "accessToken");
            }
            if (jwt != null && tokenProvider.validateJwtToken(jwt)) {
                String username = tokenProvider.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (Exception e) {
            logger.error("Can NOT set user authentication -> {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String getJwt(HttpServletRequest request, String name) {
        if (request.getCookies() != null && request.getCookies().length > 0) {
            Map<String, String> cookies = new HashMap<String, String>();
            Arrays.stream(request.getCookies()).forEach(
                    cookie -> {
                        cookies.put(cookie.getName(), cookie.getValue());
                    }
            );
            return cookies.get(name);
        }
        return null;
    }
}
