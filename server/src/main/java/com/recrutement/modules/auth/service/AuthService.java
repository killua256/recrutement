package com.recrutement.modules.auth.service;

import com.recrutement.exceptions.TokenExpiredException;
import com.recrutement.exceptions.UserAlreadyExistsException;
import com.recrutement.exceptions.UserNotFoundException;
import com.recrutement.modules.auth.httpRequest.SignupRequest;
import com.recrutement.modules.role.IRoleService;
import com.recrutement.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.recrutement.config.ExternalConfigs;
import com.recrutement.modules.auth.httpRequest.AuthRequest;
import com.recrutement.modules.auth.httpResponse.AuthResponse;
import com.recrutement.modules.user.User;
import com.recrutement.modules.user.UserMapper;
import com.recrutement.modules.user.UserRepository;
import com.recrutement.modules.user.dto.UserDto;
import com.recrutement.modules.user.service.IUserService;
import com.recrutement.security.jwt.JwtProvider;
import com.recrutement.security.jwt.JwtResponse;

import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private IUserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UtilsService utilsService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private ExternalConfigs externalConfigs;

    @Override
    @Transactional
    public void signup(SignupRequest user) throws UserAlreadyExistsException {
        if (userService.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("Username already used");
        }
        if (userService.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Email already used");
        }
        user.setActivated(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleService.findByName("ROLE_USER"));

        userService.saveDto(user);
    }
    @Override
    @Transactional
    public void creatAdmin(SignupRequest user) throws UserAlreadyExistsException {
        if (userService.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("Username already used");
        }
        if (userService.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Email already used");
        }
        user.setActivated(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleService.findByName("ROLE_ADMIN"));

        userService.saveDto(user);
    }


    @Override
    public AuthResponse signin(AuthRequest authRequest) throws UserNotFoundException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Optional<User> optionalUser = userRepository.findByEmailOrUsername(authRequest.getUsername(), authRequest.getUsername());
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("Account not found");
        }
        User user = optionalUser.get();
        return authenticate(user);
    }

    @Override
    public AuthResponse refreshToken() throws TokenExpiredException, UserNotFoundException {
        return authenticate(utilsService.getCurrentUser());
    }


    private AuthResponse authenticate(User user) {
        UserDto dto = userMapper.toUserDto(user);
        JwtResponse accessToken = jwtProvider.generateJwtToken(user, false);
        JwtResponse refreshToken = jwtProvider.generateJwtToken(user, true);
        return new AuthResponse(dto, accessToken, refreshToken, user.getRole().getName());
    }

}
