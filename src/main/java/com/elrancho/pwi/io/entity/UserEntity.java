package com.elrancho.pwi.io.entity;

import java.io.Serializable;
import java.time.LocalDateTime;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "users")
public class UserEntity implements Serializable {

	public static final long serialVersionUID = 1L;

	@Column(name = "useridstring", length = 50)
	private String userIdString;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "storeid")
	private StoreEntity storeDetails;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "email", length = 120, unique = true)
	private String email;

	@Id
	@Column(name = "username", length = 25)
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "loggedin")
	private boolean loggedIn;

	@Column(name = "temppassword")
	private String tempPassword;

	@Column(name = "emailconfirmationstatus")
	private boolean emailConfirmationStatus = false;

	@Column(name = "emailconfirmationtoken")
	private String emailConfirmationToken;

	@Column(name = "confirmationcode")
	private String confirmationCode;

	@Column(name = "confirmationttime")
	private LocalDateTime confirmationtTime;

	@Column(name = "dateUpdated")
	private LocalDateTime dateUpdated;

	public String getUserIdString() {
		return userIdString;
	}

	public void setUserIdString(String userIdString) {
		this.userIdString = userIdString;
	}

	public StoreEntity getStoreDetails() {
		return storeDetails;
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

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getTempPassword() {
		return tempPassword;
	}

	public void setTempPassword(String tempPassword) {
		this.tempPassword = tempPassword;
	}

	public boolean isEmailConfirmationStatus() {
		return emailConfirmationStatus;
	}

	public void setEmailConfirmationStatus(boolean emailConfirmationStatus) {
		this.emailConfirmationStatus = emailConfirmationStatus;
	}

	public String getEmailConfirmationToken() {
		return emailConfirmationToken;
	}

	public void setEmailConfirmationToken(String emailConfirmationToken) {
		this.emailConfirmationToken = emailConfirmationToken;
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

	public LocalDateTime getConfirmationtTime() {
		return confirmationtTime;
	}

	public void setConfirmationtTime(LocalDateTime confirmationtTime) {
		this.confirmationtTime = confirmationtTime;
	}

	public LocalDateTime getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(LocalDateTime dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

}
