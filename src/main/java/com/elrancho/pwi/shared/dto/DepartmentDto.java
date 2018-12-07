package com.elrancho.pwi.shared.dto;

import java.util.List;

public class DepartmentDto {

	private long departmentId;
	private String description;
	private  StoreDto storeDetails;
	private String type;
	private List<InventoryCountDto> inventoryCounts;
	
	public long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public StoreDto getStoreDetails() {
		return storeDetails;
	}
	public void setStoreDetails(StoreDto storeDetails) {
		this.storeDetails = storeDetails;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<InventoryCountDto> getInventoryCounts() {
		return inventoryCounts;
	}
	public void setInventoryCounts(List<InventoryCountDto> inventoryCounts) {
		this.inventoryCounts = inventoryCounts;
	}	
	
	
}
