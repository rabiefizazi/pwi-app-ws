package com.elrancho.pwi.shared.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Id;

public class UserDto {
	private String userIdString;

	private long storeId;
	private boolean enabled;
	private String email;
	private String username;
	private String password;
	private boolean loggedIn;
	private String tempPassword;
	private boolean emailConfirmationStatus = false;
	private String emailConfirmationToken;
	private String confirmationCode;
	private LocalDateTime confirmationtTime;
	private LocalDateTime dateUpdated;

	public String getUserIdString() {
		return userIdString;
	}

	public void setUserIdString(String userIdString) {
		this.userIdString = userIdString;
	}

	public long getStoreId() {
		return storeId;
	}

	public void setStoreId(long storeId) {
		this.storeId = storeId;
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
