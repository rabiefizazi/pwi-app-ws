package com.elrancho.pwi.shared.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ItemDto {

	private String itemIdString;
	private long itemUPC;
	private long vendorItem;
	private StoreDto storeDetails;
	private String description;
	private String category;
	private double cost;
	private String unitOfMeasure;
	private boolean itemMaster;
	
	private LocalDateTime dateUploaded;
	private List<InventoryCountDto> inventoryCounts;


	public String getItemIdString() {
		return itemIdString;
	}

	public void setItemIdString(String itemIdString) {
		this.itemIdString = itemIdString;
	}

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

	public StoreDto getStoreDetails() {
		return storeDetails;
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

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public boolean isItemMaster() {
		return itemMaster;
	}

	public void setItemMaster(boolean itemMaster) {
		this.itemMaster = itemMaster;
	}
	
	public LocalDateTime getDateUploaded() {
		return dateUploaded;
	}

	public void setDateUploaded(LocalDateTime dateUploaded) {
		this.dateUploaded = dateUploaded;
	}

	public List<InventoryCountDto> getInventoryCounts() {
		return inventoryCounts;
	}

	public void setInventoryCounts(List<InventoryCountDto> inventoryCounts) {
		this.inventoryCounts = inventoryCounts;
	}

}
