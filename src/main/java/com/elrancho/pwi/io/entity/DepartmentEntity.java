package com.elrancho.pwi.io.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "department")
public class DepartmentEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="departmentid")
	private long departmentId;

	@Column(name = "description", length = 50)
	private String description;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "storeid")
	private  StoreEntity storeDetails;

	@Column(name = "type", length = 25)
	private String type;
	
	@OneToMany(mappedBy="departmentDetails", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
	private List<InventoryCountEntity> inventoryCounts;

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

	public StoreEntity getStoreDetails() {
		return storeDetails;
	}

	public void setStoreDetails(StoreEntity storeDetails) {
		this.storeDetails = storeDetails;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<InventoryCountEntity> getInventoryCounts() {
		return inventoryCounts;
	}

	public void setInventoryCounts(List<InventoryCountEntity> inventoryCounts) {
		this.inventoryCounts = inventoryCounts;
	}
	
	
	
}
