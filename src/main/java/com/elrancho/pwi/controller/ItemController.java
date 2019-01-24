package com.elrancho.pwi.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.elrancho.pwi.service.ItemService;
import com.elrancho.pwi.service.StoreService;
import com.elrancho.pwi.shared.Utils;
import com.elrancho.pwi.shared.dto.ItemDto;
import com.elrancho.pwi.ui.model.request.ItemDetailRequestModel;
import com.elrancho.pwi.ui.model.response.ItemRest;
import com.elrancho.pwi.ui.model.response.ItemRestList;

@RestController
@RequestMapping("/items")
public class ItemController {

	@Autowired
	ItemService itemService;
	
	@Autowired
	StoreService storeService;

	@GetMapping(path = "/{storeId}/{vendorItem}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ItemRestList getItem(@PathVariable long storeId, @PathVariable long vendorItem,
			HttpServletResponse response) {

		if (itemService.getItem(storeId, vendorItem) == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			// throw new RuntimeException("Item " + vendorItem + " not found.");
			return null;
		} else {
			ItemDto itemDto = itemService.getItem(storeId, vendorItem);
			List<ItemRest> itemRests = new ArrayList<>();
			itemRests.add(new ModelMapper().map(itemDto, ItemRest.class));

			ItemRestList returnValue = new ItemRestList();

			returnValue.setItems(itemRests);

			response.setStatus(HttpServletResponse.SC_OK);
			return returnValue;
		}

	}

	@GetMapping(path = "/{storeId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public List<ItemRest> getItemsByStore(@PathVariable long storeId) {

		ModelMapper modelMapper = new ModelMapper();
		List<ItemRest> returnValue = new ArrayList<>();

		Iterable<ItemDto> items = itemService.getItemsByStore(storeId);

		for (ItemDto item : items) {
			returnValue.add(modelMapper.map(item, ItemRest.class));
		}
		return returnValue;
	}

	@GetMapping(path = "/", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public List<ItemRest> getItemsByStore() {

		ModelMapper modelMapper = new ModelMapper();
		List<ItemRest> returnValue = new ArrayList<>();

		Iterable<ItemDto> items = itemService.getItems();

		for (ItemDto item : items) {
			returnValue.add(modelMapper.map(item, ItemRest.class));
		}
		return returnValue;
	}

	@PostMapping(path = "/new", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ItemRest createItem(@RequestBody ItemDetailRequestModel itemDetail) {

		
		ModelMapper modelMapper = new ModelMapper();

		ItemDto itemDto = new ItemDto();
		
		itemDto.setItemUPC(itemDetail.getItemUPC());
		itemDto.setVendorItem(itemDetail.getVendorItem());
		itemDto.setStoreId(storeService.getStore(itemDetail.getStoreId()).getStoreId());
		itemDto.setDescription(itemDetail.getDescription());
		itemDto.setCost(itemDetail.getCost());
		itemDto.setUnitOfMeasure(itemDetail.getUnitOfMeasure());
		itemDto.setItemMaster(itemDetail.isItemMaster());;
		itemDto.setCategory(itemDetail.getCategory());
		
		ItemDto newItem = itemService.createItem(itemDto);

		ItemRest returnValue = modelMapper.map(newItem, ItemRest.class);
		return returnValue;
	}

	@PutMapping(path = "/update", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ItemRest updateItem(@RequestBody ItemDetailRequestModel itemDetail) {

		ModelMapper modelMapper = new ModelMapper();

		ItemRest returnValue = new ItemRest();

		ItemDto itemDto = modelMapper.map(itemDetail, ItemDto.class);

		ItemDto updatedItem = itemService.updateItem(itemDto);

		returnValue = modelMapper.map(updatedItem, ItemRest.class);

		return returnValue;
	}

	@DeleteMapping(path = "/{storeId}/{vendorItem}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ItemRest deleteItem(@PathVariable long storeId, @PathVariable long vendorItem) {

		return new ModelMapper().map(itemService.deleteItem(storeId, vendorItem), ItemRest.class);
	}
	
	//upload itemList CSV file
	@PostMapping(value = "/upload", consumes = "multipart/form-data")
    public void uploadItemList(@RequestParam("file") MultipartFile file) throws IOException {
		
		List<ItemDetailRequestModel> ItemList = Utils.uploadItemListCsv(ItemDetailRequestModel.class, file.getInputStream());
		
		Iterable<ItemDetailRequestModel> items = ItemList;
		
		List<ItemDto> itemDtos = new ArrayList<>();
		
		for(ItemDetailRequestModel itemDetail:items) {

			ItemDto itemDto = new ItemDto();
			itemDto.setItemUPC(itemDetail.getItemUPC());
			itemDto.setVendorItem(itemDetail.getVendorItem());
			itemDto.setStoreId(storeService.getStore(itemDetail.getStoreId()).getStoreId());
			itemDto.setDescription(itemDetail.getDescription());
			itemDto.setCost(itemDetail.getCost());
			itemDto.setUnitOfMeasure(itemDetail.getUnitOfMeasure());
			itemDto.setItemMaster(true);;
			itemDto.setCategory(itemDetail.getCategory());
			
			itemDtos.add(itemDto);			
		}
		
        itemService.uploadItemListCsv(itemDtos);
    }
}
