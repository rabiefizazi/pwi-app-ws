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

import com.elrancho.pwi.service.StoreService;
import com.elrancho.pwi.shared.dto.StoreDto;
import com.elrancho.pwi.ui.model.request.StoreDetailRequestModel;
import com.elrancho.pwi.ui.model.response.StoreRest;

@RestController
@RequestMapping("/stores")
public class StoreController {

	@Autowired
	StoreService storeService;

	@GetMapping(path = "/{storeId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public StoreRest store(@PathVariable long storeId) {

		StoreDto storeDto = storeService.getStore(storeId);

		if (storeDto == null) {
			System.out.println("Store " + storeId + " not found.");
			return null;
		}
		return new ModelMapper().map(storeDto, StoreRest.class);
	}

	@GetMapping(path = "/", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<StoreRest> stores() {

		List<StoreRest> returnValue = new ArrayList<>();
		ModelMapper modelMapper = new ModelMapper();

		Iterable<StoreDto> stores = storeService.getStores();
		for (StoreDto store : stores)
			returnValue.add(modelMapper.map(store, StoreRest.class));

		return returnValue;
	}

	@PostMapping(path="/new", consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public StoreRest createStore(@RequestBody StoreDetailRequestModel storeDetail) {

		ModelMapper modelMapper = new ModelMapper();

		StoreDto newStore = storeService.createStore(modelMapper.map(storeDetail, StoreDto.class));

		return modelMapper.map(newStore, StoreRest.class);
	}

	@PutMapping(path="/update", consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public StoreRest updateStore(@RequestBody StoreDetailRequestModel storeDetail) {

		ModelMapper modelMapper = new ModelMapper();

		StoreDto updatedStore = storeService.updateStore(modelMapper.map(storeDetail, StoreDto.class));

		return modelMapper.map(updatedStore, StoreRest.class);
	}

	@DeleteMapping(path = "/{storeId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public StoreRest deleteStore(@PathVariable long storeId) {

		return new ModelMapper().map(storeService.deleteStore(storeId), StoreRest.class);

	}

}
