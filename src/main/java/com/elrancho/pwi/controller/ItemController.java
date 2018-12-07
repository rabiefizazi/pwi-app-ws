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

import com.elrancho.pwi.service.ItemService;
import com.elrancho.pwi.shared.dto.ItemDto;
import com.elrancho.pwi.ui.model.request.ItemDetailRequestModel;
import com.elrancho.pwi.ui.model.response.ItemRest;

@RestController
@RequestMapping("/items")
public class ItemController {

	@Autowired
	ItemService itemService;
	
	@GetMapping(path="/{storeId}/{vendorItem}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ItemRest getItem(@PathVariable long storeId, @PathVariable long vendorItem) {
		
		ItemDto itemDto = itemService.getItem(storeId, vendorItem);
		
		ItemRest returnV = new ModelMapper().map(itemDto, ItemRest.class);
		
		return returnV;
		
	}
	
	@GetMapping(path="/{storeId}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public List<ItemRest> getItemsByStore(@PathVariable long storeId){
		
		ModelMapper modelMapper = new ModelMapper();
		List<ItemRest> returnValue = new ArrayList<>();
		
		Iterable<ItemDto> items = itemService.getItemsByStore(storeId);
		
		for(ItemDto item:items) {
			returnValue.add(modelMapper.map(item,ItemRest.class));
		}
		return returnValue;
	}
	
	@GetMapping( path="/", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public List<ItemRest> getItemsByStore(){
		
		ModelMapper modelMapper = new ModelMapper();
		List<ItemRest> returnValue = new ArrayList<>();
		
		Iterable<ItemDto> items = itemService.getItems();
		
		for(ItemDto item:items) {
			returnValue.add(modelMapper.map(item,ItemRest.class));
		}
		return returnValue;
	}
	
	@PostMapping(path="/new", consumes= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ItemRest createItem(@RequestBody ItemDetailRequestModel itemDetail) {
		
		ModelMapper modelMapper = new ModelMapper();
		ItemDto itemDto = modelMapper.map(itemDetail, ItemDto.class);
		
		ItemDto newItem = itemService.createItem(itemDto);
		
		ItemRest returnValue = modelMapper.map(newItem,ItemRest.class);
		return returnValue;
	}
	
	@PutMapping(path="/update", consumes= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ItemRest updateItem(@RequestBody ItemDetailRequestModel itemDetail) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		ItemRest returnValue = new ItemRest();
		
		ItemDto itemDto = modelMapper.map(itemDetail,ItemDto.class);
		
		ItemDto updatedItem = itemService.updateItem(itemDto);
		
		returnValue =modelMapper.map(updatedItem,ItemRest.class);
		
		return returnValue;
	}
	
	@DeleteMapping(path="/{storeId}/{vendorItem}", produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ItemRest deleteItem(@PathVariable long storeId, @PathVariable long vendorItem) {
		
		
		return new ModelMapper().map(itemService.deleteItem(storeId, vendorItem), ItemRest.class);
	}
}
