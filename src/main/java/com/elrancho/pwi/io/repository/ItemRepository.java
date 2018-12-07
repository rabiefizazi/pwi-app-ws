package com.elrancho.pwi.io.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.elrancho.pwi.io.entity.ItemEntity;
import com.elrancho.pwi.io.entity.StoreEntity;

@Repository
public interface ItemRepository extends CrudRepository<ItemEntity, Long> {

	public ItemEntity findItemByStoreDetailsAndVendorItem(StoreEntity storeEntity, long vendorItem);
	
	public List<ItemEntity> findItemByStoreDetails(StoreEntity storeEntity);
	
	public ItemEntity findItemByVendorItem(long vendorItem);
}
