package com.elrancho.pwi.shared.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class InventoryCountDto {

	private long transactionId;
	private String transactionIdString;
	private StoreDto storeDetails;
	private DepartmentDto departmentDetails;
	private UserDto userDetails;
	public double vendorItem;
	private ItemDto itemDetails;
	private double cost;
	private double quantity;
	private LocalDate weekEndDate;
	private LocalDateTime dateUpdated;

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionIdString() {
		return transactionIdString;
	}

	public void setTransactionIdString(String transactionIdString) {
		this.transactionIdString = transactionIdString;
	}

	public StoreDto getStoreDetails() {
		return storeDetails;
	}

	public void setStoreDetails(StoreDto storeDetails) {
		this.storeDetails = storeDetails;
	}

	public DepartmentDto getDepartmentDetails() {
		return departmentDetails;
	}

	public void setDepartmentDetails(DepartmentDto departmentDetails) {
		this.departmentDetails = departmentDetails;
	}

	public UserDto getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDto userDetails) {
		this.userDetails = userDetails;
	}

	public ItemDto getItemDetails() {
		return itemDetails;
	}

	public double getVendorItem() {
		return vendorItem;
	}

	public void setVendorItem(double vendorItem) {
		this.vendorItem = vendorItem;
	}
	
	public void setItemDetails(ItemDto itemDetails) {
		this.itemDetails = itemDetails;
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

}
