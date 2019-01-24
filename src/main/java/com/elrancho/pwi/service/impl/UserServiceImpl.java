package com.elrancho.pwi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elrancho.pwi.io.entity.PasswordResetTokenEntity;
import com.elrancho.pwi.io.entity.UserEntity;
import com.elrancho.pwi.io.repository.PasswordResetTokenRepository;
import com.elrancho.pwi.io.repository.StoreRepository;
import com.elrancho.pwi.io.repository.UserRepository;
import com.elrancho.pwi.service.UserService;
import com.elrancho.pwi.shared.dto.UserDto;
import com.elrancho.pwi.shared.AmazonSES;
import com.elrancho.pwi.shared.Utils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	StoreRepository storeRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	Utils utils;

	@Autowired
	PasswordResetTokenRepository passwordResetTokenRepository;

	@Override
	@Transactional // We can remove this because we already enabled the lazy fetch = true in the
					// application properties file
	public UserDto getUserByUsername(String username) {

		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findUserByUsername(username);

		if (userEntity == null)
			throw new UsernameNotFoundException(username);

		returnValue = new ModelMapper().map(userEntity, UserDto.class);

		return returnValue;
	}

	@Override
	public UserDto getUserByUserId(String userId) {

		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findUserByUserIdString(userId);

		if (userEntity == null)
			throw new UsernameNotFoundException(userId);

		returnValue = new ModelMapper().map(userEntity, UserDto.class);

		return returnValue;

	}

	@Override
	public List<UserDto> getUsers() {

		ModelMapper modelMapper = new ModelMapper();
		List<UserDto> returnValue = new ArrayList<>();

		Iterable<UserEntity> users = userRepository.findAll();

		for (UserEntity user : users)
			returnValue.add(modelMapper.map(user, UserDto.class));

		return returnValue;
	}

	@Override
	public List<UserDto> getUserByStore(long storeId) {

		ModelMapper modelMapper = new ModelMapper();

		List<UserDto> returnValue = new ArrayList<>();

		Iterable<UserEntity> users = userRepository.findUserByStoreId(storeRepository.findStoreByStoreId(storeId));

		for (UserEntity user : users)
			returnValue.add(modelMapper.map(user, UserDto.class));

		return returnValue;
	}

	@Override
	public String createUser(UserDto userDto) {

		ModelMapper modelMapper = new ModelMapper();

		UserEntity userEntity = userRepository.findUserByUsername(userDto.getUsername());

		if (userEntity != null) {
			// if the user exist but the verification hasn't been completed yet, then delete
			// that user and create the new one.
			if (!userEntity.isEmailConfirmationStatus())
				userRepository.delete(userEntity);
			else
				return "User already exist";
			;
		}
		
		userEntity = userRepository.findUserByEmail(userDto.getEmail());
		if (userEntity != null) {
				return "Email already associated with another account";
		}

		userEntity = modelMapper.map(userDto, UserEntity.class);

		String publicUserId = utils.generateUserId(30);
		userEntity.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		userEntity.setUserIdString(publicUserId);
		userEntity.setEmailConfirmationToken(utils.generateEmailConfirmationToken(publicUserId));
		userEntity.setEmailConfirmationStatus(false);
		userEntity.setDateUpdated(utils.getTodaysDate());

		UserEntity newUser = userRepository.save(userEntity);

		// returnValue = modelMapper.map(newUser, UserDto.class);

		// Send an email message to user to verify their email address
		new AmazonSES().verifyEmail(modelMapper.map(newUser, UserDto.class));

		return "Added successfully";
	}

	@Override
	public UserDto updateUser(UserDto userDto) {

		ModelMapper modelMapper = new ModelMapper();

		if (userRepository.findUserByUsername(userDto.getUsername()) == null)
			throw new RuntimeException("User " + userDto.getUsername() + " not found.");

		UserDto returnValue = new UserDto();

		UserEntity updateUser = new UserEntity();

		updateUser = modelMapper.map(userDto, UserEntity.class);

		updateUser.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		updateUser.setDateUpdated(utils.getTodaysDate());

		updateUser = userRepository.save(updateUser);

		returnValue = modelMapper.map(updateUser, UserDto.class);

		return returnValue;
	}

	@Transactional
	@Override
	public void deleteUser(String userId) {

		UserEntity userEntity = userRepository.findUserByUserIdString(userId);

		if (userEntity == null)
			throw new RuntimeException("User Not found.");

		userRepository.delete(userEntity);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity userEntity = userRepository.findUserByUsername(username);

		if (userEntity == null)
			throw new UsernameNotFoundException(username);

		return new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.isEmailConfirmationStatus(),
				true, true, true, new ArrayList<>());
		// return new User(userEntity.getUsername(), userEntity.getPassword(), new
		// ArrayList<>());
	}

	@Override
	public boolean verifyEmailToken(String token) {

		boolean returnValue = false;

		// Find user by token
		UserEntity userEntity = userRepository.findUserByEmailConfirmationToken(token);

		if (userEntity != null) {
			boolean hasTokenExpired = Utils.hasTokenExpired(token);
			if (!hasTokenExpired)
				userEntity.setEmailConfirmationToken(null);
			userEntity.setEmailConfirmationStatus(Boolean.TRUE);
			userRepository.save(userEntity);
			returnValue = true;
		}
		return returnValue;
	}

	@Override
	public boolean requestPasswordReset(String email) {

		boolean returnValue = false;

		UserEntity userEntity = userRepository.findUserByEmail(email);

		if (userEntity == null) {
			return returnValue;
		}

		String token = utils.generatePasswordResetToken(userEntity.getUserIdString());

		PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
		passwordResetTokenEntity.setToken(token);
		passwordResetTokenEntity.setUserDetails(userEntity);
		passwordResetTokenRepository.save(passwordResetTokenEntity);

		returnValue = new AmazonSES().sendPasswordResetRequest(userEntity.getUsername(), userEntity.getEmail(), token);

		return returnValue;
	}

	@Override
	public boolean resetPassword(String token, String password) {
		boolean returnValue = false;

		if (Utils.hasTokenExpired(token)) {
			return returnValue;
		}

		PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenRepository
				.findPasswordResetTokenByToken(token);

		if (passwordResetTokenEntity == null) {
			return returnValue;
		}

		// Prepare new password
		String encodedPassword = bCryptPasswordEncoder.encode(password);

		// Update User password in database
		UserEntity userEntity = passwordResetTokenEntity.getUserDetails();
		userEntity.setPassword(encodedPassword);
		UserEntity savedUserEntity = userRepository.save(userEntity);

		// Verify if password was saved successfully
		if (savedUserEntity != null && savedUserEntity.getPassword().equalsIgnoreCase(encodedPassword)) {
			returnValue = true;
		}

		// Remove Password Reset token from database
		passwordResetTokenRepository.delete(passwordResetTokenEntity);

		return returnValue;
	}

}
