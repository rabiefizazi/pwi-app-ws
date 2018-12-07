package com.elrancho.pwi.io.entity;

import java.io.Serializable;

public class ItemEntityPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long vendorItem;
	
	private long storeDetails;
	
	public long getVendorItem() {
		return vendorItem;
	}

	public void setVendorItem(long vendorItem) {
		this.vendorItem = vendorItem;
	}

	public long getStoreDetails() {
		return storeDetails;
	}

	public void setStoreDetails(long storeDetails) {
		this.storeDetails = storeDetails;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (storeDetails ^ (storeDetails >>> 32));
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
		if (storeDetails != other.storeDetails)
			return false;
		if (vendorItem != other.vendorItem)
			return false;
		return true;
	}


}
