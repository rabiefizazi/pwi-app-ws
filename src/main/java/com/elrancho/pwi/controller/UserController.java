package com.elrancho.pwi.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elrancho.pwi.service.StoreService;
import com.elrancho.pwi.service.UserService;
import com.elrancho.pwi.shared.dto.StoreDto;
import com.elrancho.pwi.shared.dto.UserDto;
import com.elrancho.pwi.ui.model.request.PasswordResetModel;
import com.elrancho.pwi.ui.model.request.PasswordResetRequestModel;
import com.elrancho.pwi.ui.model.request.UserDetailRequestModel;
import com.elrancho.pwi.ui.model.response.OperationStatusModel;
import com.elrancho.pwi.ui.model.response.RequestOperationMessage;
import com.elrancho.pwi.ui.model.response.RequestOperationName;
import com.elrancho.pwi.ui.model.response.RequestOperationStatus;
import com.elrancho.pwi.ui.model.response.UserRest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	StoreService storeService;

	// this is commented to secure user account being access by other users using
	// guessed usernames

	/*
	 * @GetMapping(path="/byun/{username}", produces =
	 * {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}) public
	 * UserRest getUserByUsername(@PathVariable String username) {
	 * 
	 * UserRest returnValue = new UserRest();
	 * 
	 * UserDto userDto = userService.getUserByUsername(username);
	 * 
	 * returnValue = new ModelMapper().map(userDto, UserRest.class);
	 * 
	 * return returnValue; }
	 */

	@GetMapping(path = "/{userId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest getUserByUserId(@PathVariable String userId) {

		UserDto userDto = userService.getUserByUserId(userId);

		// this is to add hatoeas
		Link addressLink = linkTo(UserController.class).slash(userId).withSelfRel();

		UserRest userRestModel = new ModelMapper().map(userDto, UserRest.class);

		userRestModel.add(addressLink);
		// userRestModel.add(addressLink); -- just to show how to add multiple address
		return userRestModel;
	}

	@GetMapping(path = "/str/{storeId}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public List<UserRest> getUsersByStore(@PathVariable long storeId) {

		ModelMapper modelMapper = new ModelMapper();
		List<UserRest> returnValue = new ArrayList<>();

		Iterable<UserDto> users = userService.getUserByStore(storeId);

		for (UserDto user : users)
			returnValue.add(modelMapper.map(user, UserRest.class));

		return returnValue;
	}

	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<UserRest> getUsers() {

		ModelMapper modelMapper = new ModelMapper();
		List<UserRest> returnValue = new ArrayList<>();

		Iterable<UserDto> users = userService.getUsers();

		for (UserDto user : users)
			returnValue.add(modelMapper.map(user, UserRest.class));

		return returnValue;
	}

	@PostMapping(path = "/new", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> createUser(@RequestBody UserDetailRequestModel userDetail) {

		UserDto newUser = new UserDto();

		newUser.setUsername(userDetail.getUsername());
		newUser.setPassword(userDetail.getPassword());
		newUser.setEmail(userDetail.getEmail());
		
		StoreDto storeDto = storeService.getStore(userDetail.getStoreId());
		//check if the store exist
		
		if(storeDto==null){
			
			OperationStatusModel operationStatus = new OperationStatusModel();
			operationStatus.setOperationName(RequestOperationName.CREATE_NEW_USER.name().toString());
			operationStatus.setOperationResult(RequestOperationStatus.ERROR.name().toString());
			return new ResponseEntity<>(operationStatus, HttpStatus.FAILED_DEPENDENCY);
		}
		newUser.setStoreDetails(storeDto);

		String additionStatus = userService.createUser(newUser);


		OperationStatusModel operationStatus = new OperationStatusModel();
		
		if(additionStatus.equals("User already exist")){
			operationStatus.setOperationName(RequestOperationName.CREATE_NEW_USER.name().toString());
			operationStatus.setOperationResult(RequestOperationStatus.ERROR.name().toString());
			operationStatus.setOperationMessage(RequestOperationMessage.USER_ALREADY_EXIST.name().toString());
			return new ResponseEntity<>(operationStatus, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else if(additionStatus.equals("Email already associated with another account")){
			operationStatus.setOperationName(RequestOperationName.CREATE_NEW_USER.name().toString());
			operationStatus.setOperationResult(RequestOperationStatus.ERROR.name().toString());
			operationStatus.setOperationMessage(RequestOperationMessage.EMAIL_ALREADY_ASSOCIATED_WITH_ANOTHER_ACCOUNT.name().toString());
			return new ResponseEntity<>(operationStatus, HttpStatus.FOUND);
		} else{
			operationStatus.setOperationName(RequestOperationName.CREATE_NEW_USER.name().toString());
			operationStatus.setOperationResult(RequestOperationStatus.SUCCESS.name().toString());
			operationStatus.setOperationMessage(RequestOperationMessage.USER_CREATED_SUCCESSFULLY.name().toString());
			return new ResponseEntity<>(operationStatus, HttpStatus.CREATED);
			
		} 
	}

	@PutMapping(path = "/update", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public UserRest updateUser(@RequestBody UserDetailRequestModel userDetail) {

		ModelMapper modelMapper = new ModelMapper();

		UserRest returnValue = new UserRest();

		UserDto updatedUser = userService.updateUser(modelMapper.map(userDetail, UserDto.class));

		returnValue = modelMapper.map(updatedUser, UserRest.class);

		return returnValue;
	}

	@DeleteMapping(path = "/delete/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public OperationStatusModel deleteUser(@PathVariable String userId) {

		OperationStatusModel returnValue = new OperationStatusModel();

		returnValue.setOperationName(RequestOperationStatus.DELETE.name());

		userService.deleteUser(userId);

		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

		return returnValue;

	}

	/*
	 * http://localhost:8080/perishablesWeeklyInventory/users/email-verification?
	 * token=sdfsdf
	 **/
	@CrossOrigin(origins = "http://ec2-54-163-206-204.compute-1.amazonaws.com:8080")
	@GetMapping(path = "/email-verification", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public OperationStatusModel verificationEmail(@RequestParam(value = "token") String token) {

		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());

		boolean isVerified = userService.verifyEmailToken(token);

		if (isVerified)
			returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		else
			returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

		return returnValue;
	}

	@CrossOrigin(origins = "http://ec2-54-163-206-204.compute-1.amazonaws.com:8080")
	@PostMapping(path = "/password-reset-request", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public OperationStatusModel requestReset(@RequestBody PasswordResetRequestModel passwordResetRequestModel) {
		OperationStatusModel returnValue = new OperationStatusModel();

		boolean operationResult = userService.requestPasswordReset(passwordResetRequestModel.getEmail());

		returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());
		returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

		if (operationResult) {
			returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		}

		return returnValue;
	}

	@CrossOrigin(origins = "http://ec2-54-163-206-204.compute-1.amazonaws.com:8080")
	@PostMapping(path = "/password-reset", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public OperationStatusModel resetPassword(@RequestBody PasswordResetModel passwordResetModel) {
		OperationStatusModel returnValue = new OperationStatusModel();

		boolean operationResult = userService.resetPassword(passwordResetModel.getToken(),
				passwordResetModel.getPassword());

		returnValue.setOperationName(RequestOperationName.PASSWORD_RESET.name());
		returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

		if (operationResult) {
			returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		}

		return returnValue;
	}

}
