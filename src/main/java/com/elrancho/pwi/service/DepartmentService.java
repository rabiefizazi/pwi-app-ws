package com.elrancho.pwi.service;

import java.util.List;
import com.elrancho.pwi.shared.dto.DepartmentDto;


public interface DepartmentService {
	
	public DepartmentDto getDepartment(long storeId, long departmentId);
	public DepartmentDto getDepartmentByDepartment(long departmentId);
	public List<DepartmentDto> getDepartmentsByStore(long storeId);
	public List<DepartmentDto> getDepartments();
	public DepartmentDto creatDepartment(DepartmentDto departmentDto);
	public DepartmentDto updateDepartment(DepartmentDto departmentDto);
	public DepartmentDto deleteDepartment(long departmentId);
	

}
