package com.elrancho.pwi.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.elrancho.pwi.io.entity.PasswordResetTokenEntity;

@Repository
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetTokenEntity, Long> {
	PasswordResetTokenEntity findPasswordResetTokenByToken(String token);
}
