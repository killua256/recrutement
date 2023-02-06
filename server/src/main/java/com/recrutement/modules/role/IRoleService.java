package com.recrutement.modules.role;

import java.util.List;

public interface IRoleService {

    RoleDto findByName(String name);
    RoleDto createByName(String name);

    List<RoleDto> findAll();
}
