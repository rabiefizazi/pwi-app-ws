package com.elrancho.pwi.controller;

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
import com.elrancho.pwi.shared.dto.DepartmentDto;
import com.elrancho.pwi.ui.model.request.DepartmentDetailRequestModel;
import com.elrancho.pwi.ui.model.response.DepartmentRest;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

	@Autowired
	DepartmentService departmentService;

	@GetMapping(path = "/{storeId}/{departmentId}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public DepartmentRest getDepartment(@PathVariable long storeId, @PathVariable long departmentId) {

		DepartmentDto departmentDto = departmentService.getDepartment(storeId,departmentId);

		return new ModelMapper().map(departmentDto, DepartmentRest.class);
	}
	
	@GetMapping(path="/{storeId}", produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public List<DepartmentRest> getDepartmentsByStore(@PathVariable long storeId){
		
		ModelMapper modelMapper = new ModelMapper();
		
		List<DepartmentRest> returnValue = new ArrayList<>();
		
		Iterable<DepartmentDto> departments = departmentService.getDepartmentsByStore(storeId);
		
		for(DepartmentDto department:departments)
			returnValue.add(modelMapper.map(department, DepartmentRest.class));
		
		return returnValue;
	}
	
	@GetMapping(path= "/", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public List<DepartmentRest> getDepartments(){
		
		ModelMapper modelMapper = new ModelMapper();
		List<DepartmentRest> returnValue = new ArrayList<>();
		
		Iterable<DepartmentDto> departments = departmentService.getDepartments();
		
		for(DepartmentDto department: departments) 
			returnValue.add(modelMapper.map(department, DepartmentRest.class));
		
		return returnValue;
	}

	@PostMapping(path="/new", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public DepartmentRest createDepartment(@RequestBody DepartmentDetailRequestModel departmentDetail) {

		ModelMapper modelMapper = new ModelMapper();
		
		DepartmentDto newDepartment = departmentService.creatDepartment(modelMapper.map(departmentDetail, DepartmentDto.class));
		
		return modelMapper.map(newDepartment, DepartmentRest.class);
		
	}
	
	
	@PutMapping(path="/update", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public DepartmentRest updatedDepartment(@RequestBody DepartmentDetailRequestModel departmentDetail) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		DepartmentDto updatedDepartment = modelMapper.map(departmentDetail, DepartmentDto.class);
			
		updatedDepartment=departmentService.updateDepartment(updatedDepartment);
		
		return modelMapper.map(updatedDepartment, DepartmentRest.class);
	}
	
	@DeleteMapping(path="/{departmentId}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public DepartmentRest deleteDepartment(@PathVariable long departmentId) {
		
		
		return new ModelMapper().map(departmentService.deleteDepartment(departmentId), DepartmentRest.class);
	}
}
