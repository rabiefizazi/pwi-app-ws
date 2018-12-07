package com.elrancho.pwi.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
import com.elrancho.pwi.shared.dto.DepartmentDto;
import com.elrancho.pwi.shared.dto.InventoryCountDto;
import com.elrancho.pwi.shared.dto.ItemDto;
import com.elrancho.pwi.shared.dto.StoreDto;
import com.elrancho.pwi.ui.model.request.InventoryCountDetailRequestModel;
import com.elrancho.pwi.ui.model.response.InventoryCountRest;

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
	ItemService itemService;

	@GetMapping(path = "/{storeId}/{departmentId}/{vendorItem}/{weekEndDateString}", produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public InventoryCountRest getInventoryCount(@PathVariable long storeId, @PathVariable long departmentId,
			@PathVariable long vendorItem, @PathVariable String weekEndDateString) {

		StoreDto storeDto = storeService.getStore(storeId);
		DepartmentDto departmentDto = departmentService.getDepartment(storeId, departmentId);
		ItemDto itemDto = itemService.getItem(storeId, vendorItem);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		LocalDate weekEndDate = LocalDate.parse(weekEndDateString, formatter);

		InventoryCountDto inventoryCountDto = inventoryCountService.getInventoryCount(storeDto, departmentDto,
				weekEndDate, itemDto);

		return new ModelMapper().map(inventoryCountDto, InventoryCountRest.class);

	}

	@GetMapping(path = "/{storeId}/{departmentId}/{weekEndDateString}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public List<InventoryCountRest> getInventoryCount(@PathVariable long storeId, @PathVariable long departmentId,
			@PathVariable String weekEndDateString) {

		List<InventoryCountRest> returnValue = new ArrayList<>();

		ModelMapper modelMapper = new ModelMapper();

		StoreDto storeDto = storeService.getStore(storeId);
		DepartmentDto departmentDto = departmentService.getDepartment(storeId, departmentId);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		LocalDate weekEndDate = LocalDate.parse(weekEndDateString, formatter);

		Iterable<InventoryCountDto> inventoryCounts = inventoryCountService.getInventoryCounts(storeDto, departmentDto,
				weekEndDate);

		for (InventoryCountDto inventoryCount : inventoryCounts)
			returnValue.add(modelMapper.map(inventoryCount, InventoryCountRest.class));

		return returnValue;

	}

	@PostMapping(path="/new", consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public InventoryCountRest createInventoryCount(@RequestBody InventoryCountDetailRequestModel inventoryCountDetail) {

		InventoryCountRest returnValue = new InventoryCountRest();
		ModelMapper modelMapper = new ModelMapper();
		InventoryCountDto newInventoryCount = inventoryCountService
				.createInventoryCount(modelMapper.map(inventoryCountDetail, InventoryCountDto.class));

		returnValue = modelMapper.map(newInventoryCount, InventoryCountRest.class);

		return returnValue;
	}

	@PutMapping(path="/update", consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public InventoryCountRest updateInventoryCount(@RequestBody InventoryCountDetailRequestModel inventoryCountDetail) {

		ModelMapper modelMapper = new ModelMapper();
		InventoryCountDto newInventoryCount = inventoryCountService
				.updateInventoryCount(modelMapper.map(inventoryCountDetail, InventoryCountDto.class));

		return modelMapper.map(newInventoryCount, InventoryCountRest.class);

	}
	
	@DeleteMapping(path = "/{storeId}/{departmentId}/{vendorItem}/{weekEndDateString}", produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
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
