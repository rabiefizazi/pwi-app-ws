package com.elrancho.pwi.io.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.elrancho.pwi.io.entity.ItemEntity;
import com.elrancho.pwi.io.entity.StoreEntity;

@Repository
public interface ItemRepository extends CrudRepository<ItemEntity, Long> {

	public ItemEntity findItemByStoreIdAndVendorItem(long storeId, long vendorItem);
	
	public List<ItemEntity> findItemByStoreId(long storeId);
	
	public ItemEntity findItemByVendorItem(long vendorItem);
	
	//void saveAll(List<ItemEntity> users);
}
