package com.elrancho.pwi.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elrancho.pwi.service.DepartmentService;
import com.elrancho.pwi.service.InventoryCountService;
import com.elrancho.pwi.service.ItemService;
import com.elrancho.pwi.service.StoreService;
import com.elrancho.pwi.service.UserService;
import com.elrancho.pwi.shared.dto.DepartmentDto;
import com.elrancho.pwi.shared.dto.InventoryCountDto;
import com.elrancho.pwi.shared.dto.ItemDto;
import com.elrancho.pwi.shared.dto.StoreDto;
import com.elrancho.pwi.shared.dto.UserDto;
import com.elrancho.pwi.ui.model.request.InventoryCountDetailRequestModel;
import com.elrancho.pwi.ui.model.response.InventoryCountRest;
import com.elrancho.pwi.ui.model.response.InventoryCountRestList;
import com.elrancho.pwi.ui.model.response.InventoryCountSummaryRest;
import com.elrancho.pwi.ui.model.response.InventoryCountSummaryRestList;
import com.elrancho.pwi.ui.model.response.OperationStatusModel;
import com.elrancho.pwi.ui.model.response.RequestOperationName;
import com.elrancho.pwi.ui.model.response.RequestOperationStatus;

@RestController
@RequestMapping(path = "/inventorycounts")
public class InventoryCountController {

	@Autowired
	InventoryCountService inventoryCountService;
	@Autowired
	StoreService storeService;
	@Autowired
	DepartmentService departmentService;
	@Autowired
	UserService userService;
	@Autowired
	ItemService itemService;

	@GetMapping(path = "/{storeId}/{departmentId}/{vendorItem}/{weekEndDateString}", produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public InventoryCountRest getInventoryCount(@PathVariable long storeId, @PathVariable long departmentId,
			@PathVariable long vendorItem, @PathVariable String weekEndDateString) {

		StoreDto storeDto = storeService.getStore(storeId);
		DepartmentDto departmentDto = departmentService.getDepartment(storeId, departmentId);
		ItemDto itemDto = itemService.getItem(storeId, vendorItem);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate weekEndDate = LocalDate.parse(weekEndDateString, formatter);

		InventoryCountDto inventoryCountDto = inventoryCountService.getInventoryCount(storeDto, departmentDto,
				weekEndDate, itemDto);

		return new ModelMapper().map(inventoryCountDto, InventoryCountRest.class);

	}

	// Get inventory count details by store by department by week end date
	@GetMapping(path = "/{storeId}/{departmentId}/{weekEndDateString}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public InventoryCountRestList getInventoryCount(@PathVariable long storeId, @PathVariable long departmentId,
			@PathVariable String weekEndDateString) {

		InventoryCountRestList returnValue = new InventoryCountRestList();
		
		List<InventoryCountRest> inventoryCountRest = new ArrayList<>();

		ModelMapper modelMapper = new ModelMapper();

		StoreDto storeDto = storeService.getStore(storeId);
		DepartmentDto departmentDto = departmentService.getDepartment(storeId, departmentId);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate weekEndDate = LocalDate.parse(weekEndDateString, formatter);

		Iterable<InventoryCountDto> inventoryCounts = inventoryCountService.getInventoryCounts(storeDto, departmentDto,
				weekEndDate);

		InventoryCountRest inCountRest = new InventoryCountRest();
		for (InventoryCountDto inventoryCount : inventoryCounts) {
			inCountRest = modelMapper.map(inventoryCount, InventoryCountRest.class);
			inCountRest.setUnitOfMeasure((itemService.getItem(inCountRest.getStoreId(), inCountRest.getVendorItem())).getUnitOfMeasure());
			inventoryCountRest.add(inCountRest);
		}
			

		returnValue.setInventoryCounts(inventoryCountRest);
		
		return returnValue;

	}

	// Get inventory count summary by store by department by week end date

	@GetMapping(path = "/totalInventory/{storeId}/{departmentId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public InventoryCountSummaryRestList getInventoryCountSummary(@PathVariable long storeId,
			@PathVariable long departmentId) {

		InventoryCountSummaryRestList inventoryCountSummaryRestList = new InventoryCountSummaryRestList();
		List<InventoryCountSummaryRest> inventoryCountSummaryRests = new ArrayList<>();

		StoreDto storeDto = storeService.getStore(storeId);
		DepartmentDto departmentDto = departmentService.getDepartment(storeId, departmentId);

		Iterable<InventoryCountDto> inventoryCounts = inventoryCountService.getInventoryCountsSummary(storeDto,
				departmentDto);

		Map<LocalDate, Double> totalAmountMap = new LinkedHashMap<>();

		double currentAmount, totalAmount;
		for (InventoryCountDto inventoryCount : inventoryCounts) {
			currentAmount = inventoryCount.getQuantity() * inventoryCount.getCost();

			if (totalAmountMap.get(inventoryCount.getWeekEndDate()) != null)
				totalAmount = totalAmountMap.get(inventoryCount.getWeekEndDate()) + currentAmount;

			else
				totalAmount = currentAmount;
			
			//round to 2 decimals only.
			BigDecimal bd = new BigDecimal(Double.toString(totalAmount));
			bd = bd.setScale(2, RoundingMode.HALF_UP);
			totalAmount = bd.doubleValue();
			
			totalAmountMap.put(inventoryCount.getWeekEndDate(), totalAmount);
		}

		InventoryCountSummaryRest inventoryCountSummaryRest;

		for (Map.Entry<LocalDate, Double> entry : totalAmountMap.entrySet()) {
			inventoryCountSummaryRest = new InventoryCountSummaryRest();
			inventoryCountSummaryRest.setStoreId(storeId);
			inventoryCountSummaryRest.setDepartmentId(departmentId);
			inventoryCountSummaryRest.setWeekEndDate(entry.getKey());
			inventoryCountSummaryRest.setTotalInventory((entry.getValue()));
			
			inventoryCountSummaryRests.add(inventoryCountSummaryRest);
		}
		
		inventoryCountSummaryRestList.setInventoryCountSummaries(inventoryCountSummaryRests);

		return inventoryCountSummaryRestList;

	}

	@PostMapping(path = "/new", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public OperationStatusModel createInventoryCount(
			@RequestBody InventoryCountDetailRequestModel inventoryCountDetail) {

		OperationStatusModel operationStatusModel = new OperationStatusModel();
		operationStatusModel.setOperationName(RequestOperationName.CREATE_NEW_INVENTORY_COUNT.name());
		operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());

		StoreDto storeDto = storeService.getStore(inventoryCountDetail.getStoreId());
		DepartmentDto departmentDto = departmentService.getDepartment(inventoryCountDetail.getStoreId(),
				inventoryCountDetail.getDepartmentId());
		ItemDto itemDto = itemService.getItem(inventoryCountDetail.getStoreId(), inventoryCountDetail.getVendorItem());
		UserDto userDto = userService.getUserByUserId(inventoryCountDetail.getUserId());

		InventoryCountDto inventoryCountDto = new InventoryCountDto();
		inventoryCountDto.setStoreDetails(storeDto);
		inventoryCountDto.setDepartmentDetails(departmentDto);
		inventoryCountDto.setItemDetails(itemDto);
		inventoryCountDto.setUsername(userDto.getUsername());
		inventoryCountDto.setCost(inventoryCountDetail.getCost());
		inventoryCountDto.setQuantity(inventoryCountDetail.getQuantity());

		InventoryCountDto newInventoryCount = inventoryCountService.createInventoryCount(inventoryCountDto);

		if (newInventoryCount == null)
			operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.name());

		return operationStatusModel;
	}

	@PutMapping(path = "/update", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public OperationStatusModel updateInventoryCount(@RequestBody InventoryCountDetailRequestModel inventoryCountDetail) {

		OperationStatusModel operationStatusModel = new OperationStatusModel();
		operationStatusModel.setOperationName(RequestOperationName.UPDATE_INVENTORY_COUNT.name());
		operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());

		StoreDto storeDto = storeService.getStore(inventoryCountDetail.getStoreId());
		DepartmentDto departmentDto = departmentService.getDepartment(inventoryCountDetail.getStoreId(),
				inventoryCountDetail.getDepartmentId());
		ItemDto itemDto = itemService.getItem(inventoryCountDetail.getStoreId(), inventoryCountDetail.getVendorItem());
		UserDto userDto = userService.getUserByUserId(inventoryCountDetail.getUserId());

		InventoryCountDto inventoryCountDto = new InventoryCountDto();
		inventoryCountDto.setStoreDetails(storeDto);
		inventoryCountDto.setDepartmentDetails(departmentDto);
		inventoryCountDto.setItemDetails(itemDto);
		inventoryCountDto.setUsername(userDto.getUsername());
		inventoryCountDto.setCost(inventoryCountDetail.getCost());
		inventoryCountDto.setQuantity(inventoryCountDetail.getQuantity());

		InventoryCountDto updateInventoryCount = inventoryCountService.updateInventoryCount(inventoryCountDto);

		if (updateInventoryCount == null)
			operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.name());

		return operationStatusModel;

	}

	@DeleteMapping(path = "/{storeId}/{departmentId}/{vendorItem}/{weekEndDateString}", produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public InventoryCountRest deleteInventoryCount(@PathVariable long storeId, @PathVariable long departmentId,
			@PathVariable long vendorItem, @PathVariable String weekEndDateString) {

		StoreDto storeDto = storeService.getStore(storeId);
		DepartmentDto departmentDto = departmentService.getDepartment(storeId, departmentId);
		ItemDto itemDto = itemService.getItem(storeId, vendorItem);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		LocalDate weekEndDate = LocalDate.parse(weekEndDateString, formatter);

		InventoryCountDto inventoryCountDto = inventoryCountService.deleteInventoryCount(storeDto, departmentDto,
				weekEndDate, itemDto);

		return new ModelMapper().map(inventoryCountDto, InventoryCountRest.class);

	}

}
