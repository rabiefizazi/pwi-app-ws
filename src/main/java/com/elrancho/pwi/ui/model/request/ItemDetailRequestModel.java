package com.elrancho.pwi.ui.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDetailRequestModel {

	@CsvBindByName(column ="Item UPC")
	private long itemUPC;
	
	@CsvBindByName(column ="Vendor-Item")
	private long vendorItem;
	
	@CsvBindByName(column ="Store ID")
	private long storeId;
	
	@CsvBindByName(column ="Item Description")
	private String description;
	
	@CsvBindByName(column ="Category")
	private String category;
	
	@CsvBindByName(column ="Item Cost")
	private double cost;
	
	@CsvBindByName(column ="Units")
	private String unitOfMeasure;
	
	private boolean itemMaster;

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

	public boolean isItemMaster() {
		return itemMaster;
	}

	public void setItemMaster(boolean itemMaster) {
		this.itemMaster = itemMaster;
	}

}
