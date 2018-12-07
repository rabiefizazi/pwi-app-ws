package com.elrancho.pwi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elrancho.pwi.io.entity.StoreEntity;
import com.elrancho.pwi.io.repository.StoreRepository;
import com.elrancho.pwi.service.StoreService;
import com.elrancho.pwi.shared.dto.StoreDto;

@Service
public class StoreServiceImpl implements StoreService {

	@Autowired
	StoreRepository storeRepository;

	@Override
	public StoreDto getStore(long storeId) {

		StoreEntity storeEntity = storeRepository.findStoreByStoreId(storeId);

		if (storeEntity == null) {
			System.out.println("Store " + storeId + " doesn't exist.");
			return null;
		}

		ModelMapper modelMapper = new ModelMapper();

		return modelMapper.map(storeEntity, StoreDto.class);
	}

	@Override
	public List<StoreDto> getStores() {

		List<StoreDto> returnResult = new ArrayList<>();

		if (storeRepository.findAll() == null) {

			System.out.println("Not items found.");
			return null;
		}

		ModelMapper modelMapper = new ModelMapper();

		Iterable<StoreEntity> stores = storeRepository.findAll();
		for (StoreEntity store : stores)
			returnResult.add(modelMapper.map(store, StoreDto.class));

		return returnResult;
	}

	@Override
	public StoreDto createStore(StoreDto storeDto) {

		StoreEntity storeEntity = storeRepository.findStoreByStoreId(storeDto.getStoreId());

		if (storeEntity != null) {
			System.out.println("Store " + storeEntity.getStoreId() + " already exist.");
			return null;
		}

		ModelMapper modelMapper = new ModelMapper();
		StoreEntity newStore = storeRepository.save(modelMapper.map(storeDto, StoreEntity.class));

		return modelMapper.map(newStore, StoreDto.class);
	}

	@Override
	public StoreDto updateStore(StoreDto storeDto) {

		StoreEntity updatedStore = storeRepository.findStoreByStoreId(storeDto.getStoreId());
		
		if (updatedStore == null) {
			System.out.println("Store " + storeDto.getStoreId() + " doesn't exist.");
			return null;
		}

		updatedStore.setDescription(storeDto.getDescription());
		updatedStore.setDistrict(storeDto.getDistrict());
		
		updatedStore = storeRepository.save(updatedStore);

		return new ModelMapper().map(updatedStore, StoreDto.class);
	}

	@Override
	public StoreDto deleteStore(long storeId) {

		StoreEntity storeEntity = storeRepository.findStoreByStoreId(storeId);

		StoreDto returnValue = new ModelMapper().map(storeEntity, StoreDto.class);

		storeRepository.delete(storeEntity);

		return returnValue;
	}

}
