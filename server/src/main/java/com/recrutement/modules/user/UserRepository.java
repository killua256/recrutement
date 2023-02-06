package com.recrutement.modules.user;

import java.util.Optional;

import com.recrutement.modules.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User> {

	Optional<User> findByEmailOrUsername(String email, String username);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	
}
