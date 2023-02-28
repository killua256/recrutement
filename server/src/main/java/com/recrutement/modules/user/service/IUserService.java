package com.recrutement.modules.user.service;

import com.recrutement.exceptions.DataNotFoundException;
import com.recrutement.exceptions.UserNotFoundException;
import com.recrutement.modules.documents.Document;
import com.recrutement.modules.user.User;
import com.recrutement.modules.user.dto.UserDto;

public interface IUserService {

    UserDto save(User user);

    UserDto update(Long id, UserDto dto) throws UserNotFoundException;

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    UserDto saveDto(UserDto user);

    UserDto findByUsername(String username) throws DataNotFoundException;

    UserDto updateAvatar(Document avatar) throws UserNotFoundException;

    UserDto updateCover(Document cover) throws UserNotFoundException;
}
