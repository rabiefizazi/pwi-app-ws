package com.elrancho.pwi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elrancho.pwi.io.entity.ItemEntity;
import com.elrancho.pwi.io.entity.StoreEntity;
import com.elrancho.pwi.io.repository.ItemRepository;
import com.elrancho.pwi.io.repository.StoreRepository;
import com.elrancho.pwi.service.InventoryCountService;
import com.elrancho.pwi.service.ItemService;
import com.elrancho.pwi.shared.Utils;
import com.elrancho.pwi.shared.dto.ItemDto;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	StoreRepository storeRepository;

	@Autowired
	InventoryCountService inventoryCountService;

	@Autowired
	Utils utils;

	@Override
	public ItemDto getItem(long storeId, long vendorItem) {

		StoreEntity storeEntity = storeRepository.findStoreByStoreId(storeId);

		ItemEntity itemEntity = itemRepository.findItemByStoreDetailsAndVendorItem(storeEntity, vendorItem);

		if (itemEntity == null)
			throw new RuntimeException("Item " + vendorItem + " not found.");

		return new ModelMapper().map(itemEntity, ItemDto.class);
	}

	@Override
	public List<ItemDto> getItemsByStore(long storeId) {

		List<ItemDto> returnResult = new ArrayList<>();

		ModelMapper modelMapper = new ModelMapper();

		Iterable<ItemEntity> items = itemRepository.findItemByStoreDetails(storeRepository.findStoreByStoreId(storeId));
		for (ItemEntity item : items)
			returnResult.add(modelMapper.map(item, ItemDto.class));

		return returnResult;
	}

	@Override
	public List<ItemDto> getItems() {

		ModelMapper modelMapper = new ModelMapper();

		List<ItemDto> returnValue = new ArrayList<>();

		Iterable<ItemEntity> items = itemRepository.findAll();

		for (ItemEntity item : items)
			returnValue.add(modelMapper.map(item, ItemDto.class));

		return returnValue;
	}

	@Override
	public ItemDto createItem(ItemDto itemDto) {

		ModelMapper modelMapper = new ModelMapper();

		ItemEntity itemEntity = modelMapper.map(itemDto, ItemEntity.class);

		StoreEntity storeEntity = itemEntity.getStoreDetails();

		if (itemRepository.findItemByStoreDetailsAndVendorItem(storeEntity, itemEntity.getVendorItem()) != null)
			throw new RuntimeException("Item " + itemEntity.getVendorItem() + " already exist.");

		itemEntity.setItemIdString(utils.generateItemId(30));
		itemEntity.setDateUploaded(utils.getTodaysDate());

		ItemEntity newItem = itemRepository.save(itemEntity);

		return modelMapper.map(newItem, ItemDto.class);

	}

	@Override
	public ItemDto updateItem(ItemDto itemDto) {

		ModelMapper modelMapper = new ModelMapper();
		StoreEntity storeEntity = modelMapper.map(itemDto.getStoreDetails(), StoreEntity.class);

		ItemEntity updatedItem = itemRepository.findItemByStoreDetailsAndVendorItem(storeEntity,
				itemDto.getVendorItem());

		if (updatedItem == null)
			throw new RuntimeException("Item " + itemDto.getVendorItem() + " not found.");

		updatedItem.setItemUPC(itemDto.getItemUPC());
		updatedItem.setDescription(itemDto.getDescription());
		updatedItem.setCost(itemDto.getCost());
		updatedItem.setDateUploaded(utils.getTodaysDate());

		updatedItem = itemRepository.save(updatedItem);

		return modelMapper.map(updatedItem, ItemDto.class);
	}

	@Override
	public ItemDto deleteItem(long storeId, long vendorItem) {

		ItemEntity deletedItem = itemRepository
				.findItemByStoreDetailsAndVendorItem(storeRepository.findStoreByStoreId(storeId), vendorItem);

		ItemDto returnValue = new ModelMapper().map(deletedItem, ItemDto.class);

		itemRepository.delete(deletedItem);

		return returnValue;
	}

}
