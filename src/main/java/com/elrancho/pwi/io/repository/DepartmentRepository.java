package com.elrancho.pwi.io.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.elrancho.pwi.io.entity.DepartmentEntity;
import com.elrancho.pwi.io.entity.StoreEntity;

@Repository
public interface DepartmentRepository extends CrudRepository<DepartmentEntity, Long> {

	public DepartmentEntity findDepartmentByDepartmentId(long departmentId);

	public DepartmentEntity findDepartmentByStoreDetailsAndDepartmentId(StoreEntity storeEntity, long departmentId);

	public List<DepartmentEntity> findDepartmentByStoreDetails(StoreEntity storeEntity);
}
