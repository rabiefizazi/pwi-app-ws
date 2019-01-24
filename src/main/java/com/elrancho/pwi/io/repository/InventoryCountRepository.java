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

	public InventoryCountEntity findInventoryCountByStoreIdAndDepartmentIdAndWeekEndDateAndVendorItem(
			long storeId, long departmentId, LocalDate periodDate, long vendorItem);

	public List<InventoryCountEntity> findInventoryCountByStoreIdAndDepartmentIdAndWeekEndDate(
			long storeId, long departmentId, LocalDate periodDate);

	public List<InventoryCountEntity> findInventoryCountByWeekEndDate(
			LocalDate periodDate);
	
	public List<InventoryCountEntity> findInventoryCountByStoreIdAndDepartmentId(long storeId,
			 long departmentId);
	

}
