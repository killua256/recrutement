package com.recrutement.modules.verifToken;

import org.springframework.stereotype.Repository;
import com.recrutement.modules.base.BaseRepository;
import com.recrutement.modules.user.User;

import java.util.Optional;

@Repository
public interface VerifTokenRepository extends BaseRepository<VerifToken> {

    Optional<VerifToken> findVerifTokenByTypeAndValue(VerifTokenType type, String value);
}
