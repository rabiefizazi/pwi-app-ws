package com.elrancho.pwi.io.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "item")
@IdClass(ItemEntityPK.class)
public class ItemEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "itemidstring", length = 50)
	private String itemIdString;

	@Column(name = "itemupc")
	private long itemUPC;

	@Id
	@Column(name = "vendoritem")
	private long vendorItem;

	@ManyToOne
	@Id
	@JoinColumn(name = "storeid", referencedColumnName = "storeid")
	private StoreEntity storeDetails;

	@Column(name = "description", length = 50)
	private String description;

	@Column(name = "category", length = 25)
	private String category;

	@Column(name = "cost")
	private double cost;
	
	@Column(name = "unitofmeasure")
	private String unitOfMeasure;

	@Column(name = "itemmaster", length = 5)
	private boolean itemMaster;

	@Column(name = "dateuploaded")
	private LocalDateTime dateUploaded;

	@OneToMany(mappedBy = "itemDetails", cascade = CascadeType.REMOVE)
	private List<InventoryCountEntity> inventoryCounts;

	public String getItemIdString() {
		return itemIdString;
	}

	public void setItemIdString(String itemIdString) {
		this.itemIdString = itemIdString;
	}

	public long getItemUPC() {
		return itemUPC;
	}

	public void setItemUPC(long itemUPC) {
		this.itemUPC = itemUPC;
	}

	public long getVendorItem() {
		return vendorItem;
	}

	public void setVendorItem(long vendorItem) {
		this.vendorItem = vendorItem;
	}

	public StoreEntity getStoreDetails() {
		return storeDetails;
	}

	public void setStoreDetails(StoreEntity storeDetails) {
		this.storeDetails = storeDetails;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public boolean isItemMaster() {
		return itemMaster;
	}

	public void setItemMaster(boolean itemMaster) {
		this.itemMaster = itemMaster;
	}

	public LocalDateTime getDateUploaded() {
		return dateUploaded;
	}

	public void setDateUploaded(LocalDateTime dateUploaded) {
		this.dateUploaded = dateUploaded;
	}

	public List<InventoryCountEntity> getInventoryCounts() {
		return inventoryCounts;
	}

	public void setInventoryCounts(List<InventoryCountEntity> inventoryCounts) {
		this.inventoryCounts = inventoryCounts;
	}

}
