package com.elrancho.pwi.ui.model.response;

import java.time.LocalDateTime;

public class ItemRest {

	private long itemUPC;
	private long vendorItem;
	private long storeId;
	private String description;
	private String category;
	private double cost;
	private String unitOfMeasure;
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

	public long getStoreId() {
		// Need to return storeId instead of storeDto. Otherwise, it will go into an
		// infinite loop.
		return storeId;
	}

	public void setStoreId(long storeId) {
		this.storeId = storeId;
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

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
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
