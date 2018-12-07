package com.elrancho.pwi.ui.model.request;

import com.elrancho.pwi.shared.dto.DepartmentDto;
import com.elrancho.pwi.shared.dto.ItemDto;
import com.elrancho.pwi.shared.dto.StoreDto;
import com.elrancho.pwi.shared.dto.UserDto;

public class InventoryCountDetailRequestModel {

	private StoreDto storeDetails;
	private DepartmentDto departmentDetails;
	private UserDto userDetails;
	private ItemDto itemDetails;
	private double cost;
	private double quantity;
	
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
	
	
}
