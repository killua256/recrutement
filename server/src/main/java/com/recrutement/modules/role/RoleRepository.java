package com.recrutement.modules.role;

import com.recrutement.modules.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends BaseRepository<Role> {
	Role findOneByName(String name);
}