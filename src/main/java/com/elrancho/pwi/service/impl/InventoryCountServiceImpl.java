package com.elrancho.pwi.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elrancho.pwi.io.entity.DepartmentEntity;
import com.elrancho.pwi.io.entity.InventoryCountEntity;
import com.elrancho.pwi.io.entity.ItemEntity;
import com.elrancho.pwi.io.entity.StoreEntity;
import com.elrancho.pwi.io.repository.DepartmentRepository;
import com.elrancho.pwi.io.repository.InventoryCountRepository;
import com.elrancho.pwi.io.repository.ItemRepository;
import com.elrancho.pwi.io.repository.StoreRepository;
import com.elrancho.pwi.io.repository.UserRepository;
import com.elrancho.pwi.service.InventoryCountService;
import com.elrancho.pwi.service.ItemService;
import com.elrancho.pwi.shared.Utils;
import com.elrancho.pwi.shared.dto.DepartmentDto;
import com.elrancho.pwi.shared.dto.InventoryCountDto;
import com.elrancho.pwi.shared.dto.ItemDto;
import com.elrancho.pwi.shared.dto.StoreDto;

@Service
public class InventoryCountServiceImpl implements InventoryCountService {

	@Autowired
	InventoryCountRepository inventoryCountRepository;

	@Autowired
	StoreRepository storeRepository;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	UserRepository userRepository;

	@Autowired
	ItemService itemService;
	@Autowired
	Utils utils;

	@Override
	public InventoryCountDto getInventoryCount(StoreDto storeDto, DepartmentDto departmentDto, LocalDate weekEndDate,
			ItemDto itemDto) {

		StoreEntity storeEntity = storeRepository.findStoreByStoreId(storeDto.getStoreId());
		DepartmentEntity departmentEntity = departmentRepository
				.findDepartmentByDepartmentId(departmentDto.getDepartmentId());
		ItemEntity itemEntity = itemRepository.findItemByStoreDetailsAndVendorItem(departmentEntity.getStoreDetails(),
				itemDto.getVendorItem());

		InventoryCountEntity inventoryCountEntity = inventoryCountRepository
				.findInventoryCountByStoreDetailsAndDepartmentDetailsAndWeekEndDateAndItemDetails(storeEntity,
						departmentEntity, weekEndDate, itemEntity);

		return new ModelMapper().map(inventoryCountEntity, InventoryCountDto.class);
	}

	@Override
	public List<InventoryCountDto> getInventoryCounts(StoreDto storeDto, DepartmentDto departmentDto, LocalDate weekEndDate) {

		List<InventoryCountDto> returnValue = new ArrayList<>();

		StoreEntity storeEntity = storeRepository.findStoreByStoreId(storeDto.getStoreId());
		DepartmentEntity departmentEntity = departmentRepository
				.findDepartmentByDepartmentId(departmentDto.getDepartmentId());

		Iterable<InventoryCountEntity> inventoryCounts = inventoryCountRepository
				.findInventoryCountByStoreDetailsAndDepartmentDetailsAndWeekEndDate(storeEntity, departmentEntity,
						weekEndDate);

		ModelMapper modelMapper = new ModelMapper();

		for (InventoryCountEntity inventoryCount : inventoryCounts)
			returnValue.add(modelMapper.map(inventoryCount, InventoryCountDto.class));

		return returnValue;
	}
	
	@Override
	public List<InventoryCountDto> getInventoryCountsSummary(StoreDto storeDto, DepartmentDto departmentDto) {

		List<InventoryCountDto> returnValue = new ArrayList<>();

		StoreEntity storeEntity = storeRepository.findStoreByStoreId(storeDto.getStoreId());
		DepartmentEntity departmentEntity = departmentRepository
				.findDepartmentByDepartmentId(departmentDto.getDepartmentId());

		Iterable<InventoryCountEntity> inventoryCounts = inventoryCountRepository
				.findInventoryCountByStoreDetailsAndDepartmentDetails(storeEntity, departmentEntity);

		ModelMapper modelMapper = new ModelMapper();

		for (InventoryCountEntity inventoryCount : inventoryCounts)
			returnValue.add(modelMapper.map(inventoryCount, InventoryCountDto.class));

		return returnValue;
	}

	@Override
	public InventoryCountDto createInventoryCount(InventoryCountDto inventoryCountDto) {

		InventoryCountDto returnValue = new InventoryCountDto();

		ModelMapper modelMapper = new ModelMapper();

		StoreEntity storeEntity = modelMapper.map(inventoryCountDto.getStoreDetails(), StoreEntity.class);
		DepartmentEntity departmentEntity = modelMapper.map(inventoryCountDto.getDepartmentDetails(),DepartmentEntity.class);
		ItemEntity itemEntity = modelMapper.map(inventoryCountDto.getItemDetails(), ItemEntity.class);

		InventoryCountEntity inventoryCountEntity = inventoryCountRepository
				.findInventoryCountByStoreDetailsAndDepartmentDetailsAndWeekEndDateAndItemDetails(storeEntity,
						departmentEntity, utils.getWeekEndDate(), itemEntity);

		if (inventoryCountEntity != null)
			throw new RuntimeException("Item "+itemEntity.getVendorItem()+" already exist.");

		inventoryCountEntity = modelMapper.map(inventoryCountDto, InventoryCountEntity.class);

		inventoryCountEntity.setTransactionIdString(utils.generateInventoryCountId(30));
		inventoryCountEntity.setWeekEndDate(utils.getWeekEndDate());
		inventoryCountEntity.setDateUpdated(utils.getTodaysDate());
		inventoryCountEntity.setVendorItem(inventoryCountEntity.getItemDetails().getVendorItem());

		InventoryCountEntity newInventoryCount = inventoryCountRepository.save(inventoryCountEntity);

		returnValue = modelMapper.map(newInventoryCount, InventoryCountDto.class);

		return returnValue;
	}

	@Override
	public InventoryCountDto updateInventoryCount(InventoryCountDto inventoryCountDto) {

		ModelMapper modelMapper = new ModelMapper();

		StoreEntity storeEntity = modelMapper.map(inventoryCountDto.getStoreDetails(), StoreEntity.class);
		DepartmentEntity departmentEntity = modelMapper.map(inventoryCountDto.getDepartmentDetails(),
				DepartmentEntity.class);
		ItemEntity itemEntity = modelMapper.map(inventoryCountDto.getItemDetails(), ItemEntity.class);

		InventoryCountEntity updatedInventoryCount = inventoryCountRepository
				.findInventoryCountByStoreDetailsAndDepartmentDetailsAndWeekEndDateAndItemDetails(storeEntity,
						departmentEntity, utils.getWeekEndDate(), itemEntity);

		if (updatedInventoryCount == null)
			throw new RuntimeException("Item not found");

		updatedInventoryCount.setQuantity(inventoryCountDto.getQuantity());
		updatedInventoryCount.setDateUpdated(utils.getTodaysDate());
		
		updatedInventoryCount = inventoryCountRepository.save(updatedInventoryCount);

		return modelMapper.map(updatedInventoryCount, InventoryCountDto.class);
	}

	@Override
	public InventoryCountDto deleteInventoryCount(StoreDto storeDto, DepartmentDto departmentDto, LocalDate weekEndDate,
			ItemDto itemDto) {

		ModelMapper modelMapper = new ModelMapper();

		InventoryCountDto returnValue = getInventoryCount(storeDto, departmentDto, weekEndDate, itemDto);

		InventoryCountEntity inventoryCountEntity = modelMapper.map(returnValue, InventoryCountEntity.class);

		inventoryCountRepository.delete(inventoryCountEntity);

		return returnValue;
	}	
}
