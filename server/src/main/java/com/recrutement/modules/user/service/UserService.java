package com.recrutement.modules.user.service;

import com.recrutement.exceptions.DataNotFoundException;
import com.recrutement.modules.documents.Document;
import com.recrutement.modules.user.User;
import com.recrutement.modules.user.UserMapper;
import com.recrutement.modules.user.UserRepository;
import com.recrutement.modules.user.dto.UserDto;
import com.recrutement.utils.UtilsService;
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
    private UserMapper userMapper;
    @Autowired
    private UtilsService utilsService;

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

    @Override
    public UserDto updateAvatar(Document avatar) {
        User user = utilsService.getCurrentUser();
        user.setAvatar(avatar);
        user = userRepository.save(user);
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto updateCover(Document cover) {
        User user = utilsService.getCurrentUser();
        user.setCover(cover);
        user = userRepository.save(user);
        return userMapper.toUserDto(user);
    }
}
