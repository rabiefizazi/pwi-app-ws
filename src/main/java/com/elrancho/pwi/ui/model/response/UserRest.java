package com.elrancho.pwi.ui.model.response;

import java.time.LocalDateTime;

import org.springframework.hateoas.ResourceSupport;

import com.elrancho.pwi.io.entity.StoreEntity;

public class UserRest extends ResourceSupport{

	private String userIdString;
	private StoreEntity storeDetails;
	private boolean enabled;
	private String email;
	private String username;
	private String password;
	private LocalDateTime dateUpdated;

	public String getUserIdString() {
		return userIdString;
	}

	public void setUserIdString(String userIdString) {
		this.userIdString = userIdString;
	}

	public long getStoreDetails() {
		return storeDetails.getStoreId();
	}

	public void setStoreDetails(StoreEntity storeDetails) {
		this.storeDetails = storeDetails;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(LocalDateTime dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

}
