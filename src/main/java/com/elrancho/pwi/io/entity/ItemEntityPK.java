package com.elrancho.pwi.io.entity;

import java.io.Serializable;

public class ItemEntityPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long vendorItem;
	
//	private long storeDetails;
	private long storeId;
	
	public long getVendorItem() {
		return vendorItem;
	}

	public void setVendorItem(long vendorItem) {
		this.vendorItem = vendorItem;
	}

	public long getStoreId() {
		return storeId;
	}

	public void setstoreId(long storeId) {
		this.storeId = storeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (storeId ^ (storeId >>> 32));
		result = prime * result + (int) (vendorItem ^ (vendorItem >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemEntityPK other = (ItemEntityPK) obj;
		if (storeId != other.storeId)
			return false;
		if (vendorItem != other.vendorItem)
			return false;
		return true;
	}


}
