
package com.elrancho.pwi.io.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "store")
public class StoreEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "storeid")
	private long storeId;

	@Column(name = "description", length = 50)
	private String description;

	@Column(name = "district", length = 25)
	private String district;

	@OneToMany(mappedBy = "storeDetails", cascade = CascadeType.REMOVE)
	private List<DepartmentEntity> departments;

	@OneToMany(mappedBy = "storeDetails", cascade = CascadeType.REMOVE)
	private List<UserEntity> users;

	@OneToMany(mappedBy = "storeDetails", cascade = CascadeType.REMOVE)
	private List<ItemEntity> items;

	@OneToMany(mappedBy = "storeDetails", cascade = CascadeType.REMOVE)
	private List<InventoryCountEntity> inventoryCounts;

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

	public List<DepartmentEntity> getDepartments() {
		return departments;
	}

	public void setDepartments(List<DepartmentEntity> departments) {
		this.departments = departments;
	}

	public List<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}

	public List<ItemEntity> getItems() {
		return items;
	}

	public void setItems(List<ItemEntity> items) {
		this.items = items;
	}

	public List<InventoryCountEntity> getInventoryCounts() {
		return inventoryCounts;
	}

	public void setInventoryCounts(List<InventoryCountEntity> inventoryCounts) {
		this.inventoryCounts = inventoryCounts;
	}

}
