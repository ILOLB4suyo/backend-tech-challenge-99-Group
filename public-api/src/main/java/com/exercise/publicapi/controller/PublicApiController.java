
package com.exercise.publicapi.controller;

import com.exercise.publicapi.dto.PublicListingsResponseDto;
import com.exercise.publicapi.service.PublicApiService;

import jakarta.validation.constraints.Min;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.publicapi.dto.CreatePublicUserRequestDto;
import com.exercise.publicapi.dto.CreatePublicUserResponseDto;
import com.exercise.publicapi.dto.CreatePublicListingRequestDto;
import com.exercise.publicapi.dto.CreatePublicListingResponseDto;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/public-api")
@Validated
public class PublicApiController {

    private final PublicApiService publicApiService;

    public PublicApiController(PublicApiService publicApiService) {
    	
        this.publicApiService = publicApiService;
        
    }

    @GetMapping("/listings")
    public PublicListingsResponseDto getListings(
            @RequestParam(
                    name = "pageNum",
                    defaultValue = "1"
            )
            @Min(1) Integer pageNum,

            @RequestParam(
                    name = "pageSize",
                    defaultValue = "10"
            )
            @Min(1) Integer pageSize,

            @RequestParam(
                    name = "userId",
                    required = false
            )
            @Min(1) Integer userId) {

        return publicApiService.getListings(pageNum, pageSize, userId);
        
    }
    

	@PostMapping("/users")
	public CreatePublicUserResponseDto createUser(@Valid @RequestBody CreatePublicUserRequestDto requestDto) {
	
	    return publicApiService.createUser(requestDto);
	    
	}
	
	@PostMapping("/listings")
	public CreatePublicListingResponseDto createListing(@Valid @RequestBody CreatePublicListingRequestDto requestDto) {
	
	    return publicApiService.createListing(requestDto);
	    
	}

    
}



