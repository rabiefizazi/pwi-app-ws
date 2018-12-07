package com.elrancho.pwi.ui.model.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.elrancho.pwi.shared.dto.DepartmentDto;
import com.elrancho.pwi.shared.dto.ItemDto;
import com.elrancho.pwi.shared.dto.StoreDto;
import com.elrancho.pwi.shared.dto.UserDto;

public class InventoryCountRest {

	private StoreDto storeDetails;
	private DepartmentDto departmentDetails;
	private UserDto userDetails;
	private ItemDto itemDetails;
	private double cost;
	private double quantity;
	private LocalDate weekEndDate;
	private LocalDateTime dateUpdated;
	private boolean itemMaster;
	
	
	public long getStoreDetails() {
		return storeDetails.getStoreId();
	}
	public void setStoreDetails(StoreDto storeDetails) {
		this.storeDetails = storeDetails;
	}
	public long getDepartmentDetails() {
		return departmentDetails.getDepartmentId();
	}
	public void setDepartmentDetails(DepartmentDto departmentDetails) {
		this.departmentDetails = departmentDetails;
	}
	public String getUserDetails() {
		return userDetails.getUsername();
	}
	public void setUserDetails(UserDto userDetails) {
		this.userDetails = userDetails;
	}
	public long getItemDetails() {
		return itemDetails.getVendorItem();
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
	public boolean isItemMaster() {
		return itemMaster;
	}
	public void setItemMaster(boolean itemMaster) {
		this.itemMaster = itemMaster;
	}


	
}
