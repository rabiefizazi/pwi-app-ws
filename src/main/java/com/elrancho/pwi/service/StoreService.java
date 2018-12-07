package com.elrancho.pwi.service;

import java.util.List;

import com.elrancho.pwi.shared.dto.StoreDto;


public interface StoreService {

	public StoreDto getStore(long storeId);
	public List<StoreDto> getStores();
	public StoreDto createStore(StoreDto storeDto);
	public StoreDto updateStore(StoreDto storeDto);
	public StoreDto deleteStore(long storeId);
}
