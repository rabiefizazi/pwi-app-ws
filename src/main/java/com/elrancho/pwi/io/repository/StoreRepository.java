package com.elrancho.pwi.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.elrancho.pwi.io.entity.StoreEntity;

@Repository
public interface StoreRepository extends CrudRepository<StoreEntity, Long> {

	public StoreEntity findStoreByStoreId(long storeId);
}
