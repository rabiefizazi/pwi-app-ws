package com.elrancho.pwi.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.elrancho.pwi.shared.dto.DepartmentDto;
import com.elrancho.pwi.shared.dto.InventoryCountDto;
import com.elrancho.pwi.shared.dto.ItemDto;
import com.elrancho.pwi.shared.dto.StoreDto;

@Service
public interface InventoryCountService {

	public InventoryCountDto getInventoryCount(StoreDto storeDto, DepartmentDto departmentDto,
			LocalDate weekEndDate, ItemDto itemDto);

	
	public List<InventoryCountDto> getInventoryCounts(StoreDto storeDto, DepartmentDto departmentDto,
			LocalDate weekEndDate);
	
	public List<InventoryCountDto> getInventoryCountsSummary(StoreDto storeDto, DepartmentDto departmentDto);

	public InventoryCountDto createInventoryCount(InventoryCountDto inventoryCountDto);

	public InventoryCountDto updateInventoryCount(InventoryCountDto inventoryCountDto);

	public InventoryCountDto deleteInventoryCount(StoreDto storeDto, DepartmentDto departmentDto,
			LocalDate weekEndDate, ItemDto itemDto);


}
