package com.elrancho.pwi.io.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@Entity(name = "inventorycount")
public class InventoryCountEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "transactionid")
	private long transactionId;

	@Column(name = "transactionidstring", length = 50)
	private String transactionIdString;

	@ManyToOne
	@JoinColumn(name = "storeid")
	private StoreEntity storeDetails;

	@ManyToOne
	@JoinColumn(name = "departmentid")
	private DepartmentEntity departmentDetails;

	@Column(name = "username" )
	private String username;

	@Column(name = "vendoritem")
	private long vendorItem;
	
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "vendoritem", referencedColumnName = "vendoritem", insertable = false, updatable = false),
			@JoinColumn(name = "storeid", referencedColumnName = "storeid", insertable = false, updatable = false) })
	private ItemEntity itemDetails;

	@Column(name = "cost")
	private double cost;

	@Column(name = "quantity")
	private double quantity;

	@Column(name = "weekenddate")
	private LocalDate weekEndDate;

	@Column(name = "dateupdated")
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

	public StoreEntity getStoreDetails() {
		return storeDetails;
	}

	public void setStoreDetails(StoreEntity storeDetails) {
		this.storeDetails = storeDetails;
	}

	public DepartmentEntity getDepartmentDetails() {
		return departmentDetails;
	}

	public void setDepartmentDetails(DepartmentEntity departmentDetails) {
		this.departmentDetails = departmentDetails;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ItemEntity getItemDetails() {
		return itemDetails;
	}

	
	public long getVendorItem() {
		return vendorItem;
	}

	public void setVendorItem(long vendorItem) {
		this.vendorItem = vendorItem;
	}

	public void setItemDetails(ItemEntity itemDetails) {
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
