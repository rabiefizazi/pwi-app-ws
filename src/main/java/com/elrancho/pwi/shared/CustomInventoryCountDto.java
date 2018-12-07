package com.elrancho.pwi.shared;

import java.time.LocalDateTime;

public class CustomInventoryCountDto {

	private String transactionIdString;
	private long storeId;
	private long departmentId;
	private String Username;
	private long vendorItem;
	private long upc;
	private double cost;
	private String Category;
	private double quantity;
	private LocalDateTime dateUpdated;
	private boolean itemMaster;

	public String getTransactionIdString() {
		return transactionIdString;
	}

	public void setTransactionIdString(String transactionIdString) {
		this.transactionIdString = transactionIdString;
	}

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

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public long getVendorItem() {
		return vendorItem;
	}

	public void setVendorItem(long vendorItem) {
		this.vendorItem = vendorItem;
	}

	public long getUpc() {
		return upc;
	}

	public void setUpc(long upc) {
		this.upc = upc;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
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
