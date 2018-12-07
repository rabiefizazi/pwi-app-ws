package com.elrancho.pwi.shared.dto;

import java.util.List;

public class StoreDto {

	private long storeId;
	private String description;
	private String district;
	private List<DepartmentDto> departments;
	private List<UserDto> users;
	private List<ItemDto> items;
	private List<InventoryCountDto> inventoryCounts;

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

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public List<DepartmentDto> getDepartments() {
		return departments;
	}

	public void setDepartments(List<DepartmentDto> departments) {
		this.departments = departments;
	}

	public List<UserDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}

	public List<ItemDto> getItems() {
		return items;
	}

	public void setItems(List<ItemDto> items) {
		this.items = items;
	}

	public List<InventoryCountDto> getInventoryCounts() {
		return inventoryCounts;
	}

	public void setInventoryCounts(List<InventoryCountDto> inventoryCounts) {
		this.inventoryCounts = inventoryCounts;
	}

}
