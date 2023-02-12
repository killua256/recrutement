package com.recrutement.modules.user.service;

import com.recrutement.exceptions.DataNotFoundException;
import com.recrutement.modules.role.RoleRepository;
import com.recrutement.modules.user.User;
import com.recrutement.modules.user.UserMapper;
import com.recrutement.modules.user.UserRepository;
import com.recrutement.modules.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDto save(User user){
        return userMapper.toUserDto(
                userRepository.save(user)
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findByEmailOrUsername(username, username);
        if(optional.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        return optional.get();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public UserDto saveDto(UserDto user) {
        return save(userMapper.toUser(user));
    }

    @Override
    public UserDto findByUsername(String username) throws DataNotFoundException {
        Optional<User> optional = userRepository.findByUsername(username);
        if(optional.isEmpty()){
            throw new DataNotFoundException("User not found");
        }
        return userMapper.toUserDto(optional.get());
    }
}
