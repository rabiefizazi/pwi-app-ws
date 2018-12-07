package com.elrancho.pwi.ui.model.response;

import com.elrancho.pwi.shared.dto.StoreDto;

public class DepartmentRest {

	private long departmentId;
	private String description;
	private StoreDto storeDetails;
	private String type;

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

	public long getStoreDetails() {
		//We will need to return the storeId not the entire store object. Otherwise this will get into an infinite loop
		return storeDetails.getStoreId();
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

}
