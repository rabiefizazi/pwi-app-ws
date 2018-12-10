package com.elrancho.pwi.io.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.elrancho.pwi.io.entity.InventoryCountEntity;
import com.elrancho.pwi.io.entity.DepartmentEntity;
import com.elrancho.pwi.io.entity.ItemEntity;
import com.elrancho.pwi.io.entity.StoreEntity;

@Repository
public interface InventoryCountRepository extends CrudRepository<InventoryCountEntity, Long> {

	public InventoryCountEntity findInventoryCountByStoreDetailsAndDepartmentDetailsAndWeekEndDateAndItemDetails(
			StoreEntity storeEntity, DepartmentEntity departmentEntity, LocalDate periodDate, ItemEntity itemEntity);

	public List<InventoryCountEntity> findInventoryCountByStoreDetailsAndDepartmentDetailsAndWeekEndDate(
			StoreEntity storeEntity, DepartmentEntity departmentEntity, LocalDate periodDate);

	public List<InventoryCountEntity> findInventoryCountByStoreDetailsAndDepartmentDetails(StoreEntity storeEntity,
			DepartmentEntity departmentEntity);

}
