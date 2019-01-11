package com.elrancho.pwi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.elrancho.pwi.shared.dto.ItemDto;

@Service
public interface ItemService {

	public ItemDto getItem(long storeId, long vendorItem);
	public List<ItemDto> getItemsByStore(long storeId);	
	public List<ItemDto> getItems();	
	public ItemDto createItem(ItemDto itemDto);
	public ItemDto updateItem(ItemDto itemDto);
	public ItemDto deleteItem(long storeId, long vendorItem);
	public void uploadItemListCsv(List<ItemDto> itemDto);
}
