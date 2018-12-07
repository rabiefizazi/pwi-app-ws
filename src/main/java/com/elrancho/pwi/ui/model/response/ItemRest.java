package com.elrancho.pwi.ui.model.response;

import java.time.LocalDateTime;

import com.elrancho.pwi.shared.dto.StoreDto;

public class ItemRest {

	private long itemUPC;
	private long vendorItem;
	private StoreDto storeDetails;
	private String description;
	private String category;
	private double cost;
	private boolean itemMaster;
	private LocalDateTime dateUploaded;

	public long getItemUPC() {
		return itemUPC;
	}

	public void setItemUPC(long itemUPC) {
		this.itemUPC = itemUPC;
	}

	public long getVendorItem() {
		return vendorItem;
	}

	public void setVendorItem(long vendorItem) {
		this.vendorItem = vendorItem;
	}

	public long getStoreDetails() {
		// Need to return storeId instead of storeDto. Otherwise, it will go into an
		// infinite loop.
		return storeDetails.getStoreId();
	}

	public void setStoreDetails(StoreDto storeDetails) {
		this.storeDetails = storeDetails;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public LocalDateTime getDateUploaded() {
		return dateUploaded;
	}

	public boolean isItemMaster() {
		return itemMaster;
	}

	public void setItemMaster(boolean itemMaster) {
		this.itemMaster = itemMaster;
	}

	public void setDateUploaded(LocalDateTime dateUploaded) {
		this.dateUploaded = dateUploaded;
	}

}
