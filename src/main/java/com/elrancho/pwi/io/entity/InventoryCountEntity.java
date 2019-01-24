package com.elrancho.pwi.io.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name = "storeid")
	@Column(name = "storeid")
	private long storeId;

//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name = "departmentid")
	@Column(name = "departmentid")
	private long departmentId;

	@Column(name = "username" )
	private String username;

	@Column(name = "vendoritem")
	private long vendorItem;

	@Column(name = "unitofmeasure")
	private String unitOfMeasure;
	
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumns({
//			@JoinColumn(name = "vendoritem", referencedColumnName = "vendoritem", insertable = false, updatable = false),
//			@JoinColumn(name = "storeid", referencedColumnName = "storeid", insertable = false, updatable = false) })
//	private long vendorItem;

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

//	public StoreEntity getStoreDetails() {
//		return storeDetails;
//	}
//
//	public void setStoreDetails(StoreEntity storeDetails) {
//		this.storeDetails = storeDetails;
//	}
//
//	public DepartmentEntity getDepartmentDetails() {
//		return departmentDetails;
//	}
//
//	public void setDepartmentDetails(DepartmentEntity departmentDetails) {
//		this.departmentDetails = departmentDetails;
//	}
	
	

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
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

	public long getVendorItem() {
		return vendorItem;
	}

	public void setVendorItem(long vendorItem) {
		this.vendorItem = vendorItem;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
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
