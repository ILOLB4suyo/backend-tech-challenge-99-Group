
package com.exercise.userservice.service.impl;

import com.exercise.userservice.dto.CreateUserRequestDto;
import com.exercise.userservice.dto.GetUsersResponseDto;
import com.exercise.userservice.dto.UserDto;
import com.exercise.userservice.dto.UserResponseDto;
import com.exercise.userservice.entity.User;
import com.exercise.userservice.repository.UserRepository;
import com.exercise.userservice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public GetUsersResponseDto getUsers(Integer pageNum, Integer pageSize) {

        PageRequest pageRequest = PageRequest.of(
                pageNum,
                pageSize,
                Sort.by("createdAt").descending()
        );

        List<UserDto> users = userRepository.findAll(pageRequest)
                .getContent()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        GetUsersResponseDto response = new GetUsersResponseDto();
        response.setResult(true);
        response.setUsers(users);

        return response;
        
    }

    @Override
    public UserResponseDto getUser(Integer id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));

        UserResponseDto response = new UserResponseDto();
        response.setResult(true);
        response.setUser(convertToDto(user));

        return response;
        
    }

    @Override
    public UserResponseDto createUser(CreateUserRequestDto requestDto) {

        User user = new User();
        user.setName(requestDto.getName());

        Long timestamp = nowInEpochMicroSecond();
        user.setCreatedAt(timestamp);
        user.setUpdatedAt(timestamp);

        User savedUser = userRepository.save(user);

        UserResponseDto response = new UserResponseDto();
        response.setResult(true);
        response.setUser(convertToDto(savedUser));

        return response;
        
    }

    private UserDto convertToDto(User user) {

        UserDto dto = new UserDto();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        return dto;
        
    }

    private Long nowInEpochMicroSecond() {
    	
        return ChronoUnit.MICROS.between(
                Instant.EPOCH,
                Instant.now()
        );
        
    }
    
}



