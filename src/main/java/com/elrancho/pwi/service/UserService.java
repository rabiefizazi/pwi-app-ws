package com.elrancho.pwi.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.elrancho.pwi.shared.dto.UserDto;

public interface UserService extends UserDetailsService{

	public UserDto getUserByUsername(String username);
	public UserDto getUserByUserId(String userId);
	public List<UserDto> getUsers();
	public List<UserDto> getUserByStore(long storeId);
	
	public String createUser(UserDto userDto);
	public UserDto updateUser(UserDto userDto);
	public void deleteUser(String userId);
	public boolean verifyEmailToken(String token);
	boolean requestPasswordReset(String email);
	boolean resetPassword(String token, String password);
}
