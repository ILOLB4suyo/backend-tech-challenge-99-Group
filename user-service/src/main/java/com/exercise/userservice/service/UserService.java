
package com.exercise.userservice.service;

import com.exercise.userservice.dto.CreateUserRequestDto;
import com.exercise.userservice.dto.GetUsersResponseDto;
import com.exercise.userservice.dto.UserResponseDto;

public interface UserService {

    GetUsersResponseDto getUsers(Integer pageNum, Integer pageSize);

    UserResponseDto getUser(Integer id);

    UserResponseDto createUser(CreateUserRequestDto requestDto);
    
}


