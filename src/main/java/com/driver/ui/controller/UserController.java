package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.UserResponse;
import com.driver.service.impl.UserServiceImpl;
import com.driver.service.impl.UserServiceImpl;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserServiceImpl userService;
	@GetMapping(path = "/{id}")
	public UserResponse getUser(@PathVariable String id) throws Exception{
		UserDto userDto=userService.getUserByUserId(id);

		UserResponse response = UserResponse.builder().userId(userDto.getUserId())
				.email(userDto.getEmail())
				.firstName(userDto.getFirstName())
				.lastName(userDto.getLastName()).build();
		return response;
	}

	@PostMapping()
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception{
		String userId = userDetails.getFirstName()+userDetails.getLastName()+Math.random()*11948;
		UserDto userDto = UserDto.builder()
				.userId(userId)
				.email(userDetails.getEmail())
				.firstName(userDetails.getFirstName())
				.lastName(userDetails.getLastName()).build();
		userService.createUser(userDto);

		UserResponse response = UserResponse.builder().userId(userId)
				.email(userDetails.getEmail())
				.firstName(userDetails.getFirstName())
				.lastName(userDetails.getLastName()).build();

		return response;
	}

	@PutMapping(path = "/{id}")
	public UserResponse updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserDto userDto = UserDto.builder()
				.userId(id)
				.email(userDetails.getEmail())
				.firstName(userDetails.getFirstName())
				.lastName(userDetails.getLastName()).build();

		userService.updateUser(id, userDto);
		UserResponse response = UserResponse.builder().userId(id)
				.email(userDetails.getEmail())
				.firstName(userDetails.getFirstName())
				.lastName(userDetails.getLastName()).build();

		return response;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) throws Exception{
		userService.deleteUser(id);

		OperationStatusModel result=OperationStatusModel.builder()
				.operationResult("Successful").operationName("Delete").build();
		return result;
	}
	
	@GetMapping()
	public List<UserResponse> getUsers(){
		List<UserDto> list=userService.getUsers();
		List<UserResponse> ansList=new ArrayList<>();
		for(UserDto userDto : list) {
			UserResponse userResponse = UserResponse.builder()
					.userId(userDto.getUserId())
					.firstName(userDto.getFirstName())
					.lastName(userDto.getLastName()).email(userDto.getEmail()).build();

			ansList.add(userResponse);
		}
		return ansList;
	}
	
}
