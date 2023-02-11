package com.recrutement.modules.user;

import com.recrutement.modules.auth.httpRequest.SignupRequest;
import com.recrutement.modules.base.BaseMapper;
import com.recrutement.modules.role.RoleMapper;
import com.recrutement.modules.user.dto.UserDto;
import com.recrutement.modules.user.dto.UserMinDto;
import org.mapstruct.*;

@Mapper(uses = {
        RoleMapper.class
})
public interface UserMapper extends BaseMapper<UserDto, User> {

    @Mapping(target = "displayName", source = ".", qualifiedByName="toDisplayName")
    @Mapping(
            source = "role",
            target = "role",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    UserDto toUserDto(User user);

    @Mapping(
            source = "role",
            target = "role",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    User toUser(UserDto userDto);

    @Mapping(
            source = "role",
            target = "role",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    User sigReqToUser(SignupRequest signupRequest);

    @Mapping(target = "displayName", source = ".", qualifiedByName="toDisplayName")
    UserMinDto toUserMinDto(User user);
    User mintoUser(UserMinDto userDto);

    @Named("toDisplayName")
    default String translateToDisplayName(User user) {
        return user.getFirstname() + " " + user.getLastname();
    }
}
