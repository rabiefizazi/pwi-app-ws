package com.elrancho.pwi.ui.model.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class InventoryCountRest {

	private long storeId;
	private long departmentId;
	private String userId;
	private long vendorItem;
	private String itemDescription;
	private double cost;
	private double quantity;
	private LocalDate weekEndDate;
	private LocalDateTime dateUpdated;
	private boolean itemMaster;
	


	public long getStoreId() {
		return storeId;
	}
	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}
	public long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public long getVendorItem() {
		return vendorItem;
	}
	public void setVendorItem(long vendorItem) {
		this.vendorItem = vendorItem;
	}
	
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public LocalDate getWeekEndDate() {
		return weekEndDate;
	}
	public void setWeekEndDate(LocalDate weekEndDate) {
		this.weekEndDate = weekEndDate;
	}
	public LocalDateTime getDateUpdated() {
		return dateUpdated;
	}
	public void setDateUpdated(LocalDateTime dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
	public boolean isItemMaster() {
		return itemMaster;
	}
	public void setItemMaster(boolean itemMaster) {
		this.itemMaster = itemMaster;
	}


	
}
