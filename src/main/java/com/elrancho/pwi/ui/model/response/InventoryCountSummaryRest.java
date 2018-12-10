package com.elrancho.pwi.ui.model.response;

import java.time.LocalDate;

public class InventoryCountSummaryRest {
	
	private long storeId;
	private long departmentId;
	private LocalDate weekEndDate;
	private double totalInventory;
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
	public LocalDate getWeekEndDate() {
		return weekEndDate;
	}
	public void setWeekEndDate(LocalDate weekEndDate) {
		this.weekEndDate = weekEndDate;
	}
	public double getTotalInventory() {
		return totalInventory;
	}
	public void setTotalInventory(double totalInventory) {
		this.totalInventory = totalInventory;
	}


}
