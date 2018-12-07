package com.elrancho.pwi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elrancho.pwi.io.entity.DepartmentEntity;
import com.elrancho.pwi.io.entity.StoreEntity;
import com.elrancho.pwi.io.repository.DepartmentRepository;
import com.elrancho.pwi.io.repository.StoreRepository;
import com.elrancho.pwi.service.DepartmentService;
import com.elrancho.pwi.service.StoreService;
import com.elrancho.pwi.shared.dto.DepartmentDto;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	StoreRepository storeRepository;
	@Autowired
	StoreService storeService;

	@Override
	public DepartmentDto getDepartment(long storeId, long departmentId) {

		DepartmentEntity departmentEntity = departmentRepository
				.findDepartmentByStoreDetailsAndDepartmentId(storeRepository.findStoreByStoreId(storeId), departmentId);
		
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(departmentEntity, DepartmentDto.class);
	}

	@Override
	public List<DepartmentDto> getDepartmentsByStore(long storeId) {

		ModelMapper modelMapper = new ModelMapper();

		List<DepartmentDto> returnValue = new ArrayList<>();

		Iterable<DepartmentEntity> departments = departmentRepository
				.findDepartmentByStoreDetails(storeRepository.findStoreByStoreId(storeId));

		for (DepartmentEntity department : departments)
			returnValue.add(modelMapper.map(department, DepartmentDto.class));

		return returnValue;
	}

	@Override
	public List<DepartmentDto> getDepartments() {

		List<DepartmentDto> departmentDtos = new ArrayList<>();

		ModelMapper modelMapper = new ModelMapper();

		Iterable<DepartmentEntity> departments = departmentRepository.findAll();
		for (DepartmentEntity department : departments)
			departmentDtos.add(modelMapper.map(department, DepartmentDto.class));

		return departmentDtos;
	}

	@Override
	public DepartmentDto creatDepartment(DepartmentDto departmentDto) {

		if (departmentRepository.findDepartmentByDepartmentId(departmentDto.getDepartmentId()) != null) {

			System.out.println("Department " + departmentDto.getDepartmentId() + " already exist.");
			return null;
		}

		ModelMapper modelMapper = new ModelMapper();
		DepartmentEntity newDepartment = departmentRepository
				.save(modelMapper.map(departmentDto, DepartmentEntity.class));

		return modelMapper.map(newDepartment, DepartmentDto.class);
	}

	@Override
	public DepartmentDto updateDepartment(DepartmentDto departmentDto) {

		ModelMapper modelMapper = new ModelMapper();

		DepartmentEntity updatedDepartment = departmentRepository
				.findDepartmentByDepartmentId(departmentDto.getDepartmentId());

		updatedDepartment.setDepartmentId(departmentDto.getDepartmentId());
		updatedDepartment.setDescription(departmentDto.getDescription());
		// This is needed if the user create the department under the wrong store or
		// similar scenarios
		updatedDepartment.setStoreDetails(
				modelMapper.map(storeService.updateStore(departmentDto.getStoreDetails()), StoreEntity.class));

		updatedDepartment = departmentRepository.save(updatedDepartment);

		return modelMapper.map(updatedDepartment, DepartmentDto.class);
	}

	@Override
	public DepartmentDto deleteDepartment(long departmentId) {

		DepartmentEntity departmentEntity = departmentRepository.findDepartmentByDepartmentId(departmentId);

		DepartmentDto returnValue = new ModelMapper().map(departmentEntity, DepartmentDto.class);

		departmentRepository.delete(departmentEntity);

		return returnValue;
	}

}
