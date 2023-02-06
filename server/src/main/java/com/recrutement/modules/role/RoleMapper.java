package com.recrutement.modules.role;

import org.mapstruct.Mapper;

@Mapper()
public interface RoleMapper {

    Role toEntity(RoleDto dto);

    RoleDto toDto(Role role);
}
