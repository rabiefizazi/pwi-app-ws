package com.elrancho.pwi.shared.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.elrancho.pwi.io.entity.StoreEntity;

public class ItemDto {

	private String itemIdString;
	private long itemUPC;
	private long vendorItem;
	private long storeId;
	private String description;
	private String category;
	private double cost;
	private String unitOfMeasure;
	private boolean itemMaster;
	private LocalDateTime dateUploaded;
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
	public LocalDateTime getDateUploaded() {
		return dateUploaded;
	}
	public void setDateUploaded(LocalDateTime dateUploaded) {
		this.dateUploaded = dateUploaded;
	}
	
	
}
