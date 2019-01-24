package com.elrancho.pwi.io.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.elrancho.pwi.io.entity.StoreEntity;
import com.elrancho.pwi.io.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

	public UserEntity findUserByUsername(String username);

	public UserEntity findUserByUserIdString(String username);

	public List<UserEntity> findUserByStoreId(StoreEntity StoreEntity);

	public UserEntity findUserByEmailConfirmationToken(String token);
	
	public UserEntity findUserByEmail(String email);
}
