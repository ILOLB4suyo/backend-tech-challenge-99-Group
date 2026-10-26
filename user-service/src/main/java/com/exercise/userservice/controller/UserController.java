
package com.exercise.userservice.controller;

import com.exercise.userservice.dto.CreateUserRequestDto;
import com.exercise.userservice.dto.GetUsersResponseDto;
import com.exercise.userservice.dto.UserResponseDto;
import com.exercise.userservice.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public GetUsersResponseDto getUsers(
            @RequestParam(name = "pageNum", defaultValue = "1")
            @Min(1) Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10")
            @Min(1) Integer pageSize
    		) {
    	
        		return userService.getUsers(pageNum - 1, pageSize);
    		}
    

    @GetMapping("/users/{id}")
    public UserResponseDto getUser(@PathVariable("id") @Min(1) Integer id) {
        
    	return userService.getUser(id);
        
    }

    @PostMapping("/users")
    public UserResponseDto createUser(@Valid CreateUserRequestDto requestDto) {
        
    	return userService.createUser(requestDto);
        
    }
    
}



