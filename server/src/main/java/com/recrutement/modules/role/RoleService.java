package com.recrutement.modules.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleMapper roleMapper;

    private RoleDto save(RoleDto dto){
        return roleMapper.toDto(
                roleRepository.save(
                        roleMapper.toEntity(dto)
                )
        );
    }
    @Override
    public RoleDto findByName(String name) {
        return roleMapper.toDto(roleRepository.findOneByName(name));
    }

    @Override
    public RoleDto createByName(String name) {
        RoleDto dto = new RoleDto();
        dto.setName(name);
        return save(dto);
    }

    @Override
    public List<RoleDto> findAll() {
        return roleRepository.findAll().stream().map(roleMapper::toDto).collect(Collectors.toList());
    }
}
