
package com.exercise.publicapi.service;

import com.exercise.publicapi.dto.ListingFromServiceDto;
import com.exercise.publicapi.dto.ListingServiceResponseDto;
import com.exercise.publicapi.dto.PublicListingDto;
import com.exercise.publicapi.dto.PublicListingsResponseDto;
import com.exercise.publicapi.dto.UserDto;
import com.exercise.publicapi.dto.UserServiceResponseDto;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import com.exercise.publicapi.dto.CreatePublicUserRequestDto;
import com.exercise.publicapi.dto.CreatePublicUserResponseDto;
import com.exercise.publicapi.dto.CreatePublicListingRequestDto;
import com.exercise.publicapi.dto.CreatePublicListingResponseDto;
import com.exercise.publicapi.dto.CreateListingServiceResponseDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class PublicApiService {

    private final RestTemplate restTemplate;

    @Value("${services.listing-url}")
    private String listingServiceUrl;

    @Value("${services.user-url}")
    private String userServiceUrl;

    public PublicApiService(RestTemplate restTemplate) {
    	
        this.restTemplate = restTemplate;
        
    }

    public PublicListingsResponseDto getListings(
    		
            Integer pageNum,
            Integer pageSize,
            Integer userId) {

        UriComponentsBuilder builder =
                UriComponentsBuilder
                        .fromHttpUrl(listingServiceUrl + "/listings")
                        .queryParam("pageNum", pageNum)
                        .queryParam("pageSize", pageSize);

        if (userId != null) {
        	
            builder.queryParam("userId", userId);
            
        }

        URI uri = builder.build().encode().toUri();

        ListingServiceResponseDto listingResponse =
                restTemplate.getForObject(
                        uri,
                        ListingServiceResponseDto.class
                );

        if (listingResponse == null) {
        	
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Listing Service returned an empty response"
                    
            );
        }

        List<PublicListingDto> publicListings =
                new ArrayList<>();

        if (listingResponse.getListings() != null) {
        	
            for (ListingFromServiceDto listing
                    : listingResponse.getListings()) {

                UserServiceResponseDto userResponse =
                        restTemplate.getForObject(
                                userServiceUrl + "/users/{id}",
                                UserServiceResponseDto.class,
                                listing.getUserId()
                        );

                if (userResponse == null
                        || userResponse.getUser() == null) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_GATEWAY,
                            "Could not retrieve listing owner"
                    );
                    
                }

                UserDto user = userResponse.getUser();

                PublicListingDto publicListing =
                        new PublicListingDto();

                publicListing.setId(listing.getId());
                publicListing.setListingType(
                        listing.getListingType()
                );
                publicListing.setPrice(listing.getPrice());
                publicListing.setCreatedAt(listing.getCreatedAt());
                publicListing.setUpdatedAt(listing.getUpdatedAt());
                publicListing.setUser(user);

                publicListings.add(publicListing);
                
            }
            
        }

        PublicListingsResponseDto response = new PublicListingsResponseDto();

        response.setResult(listingResponse.isResult());
        response.setListings(publicListings);

        return response;
        
    }
    
	
	public CreatePublicUserResponseDto createUser(
	        CreatePublicUserRequestDto requestDto) {
	
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(
	            MediaType.APPLICATION_FORM_URLENCODED
	    );
	
	    MultiValueMap<String, String> form =
	            new LinkedMultiValueMap<>();
	
	    form.add("name", requestDto.getName());
	
	    HttpEntity<MultiValueMap<String, String>> request =
	            new HttpEntity<>(form, headers);
	
	    UserServiceResponseDto serviceResponse =
	            restTemplate.postForObject(
	                    userServiceUrl + "/users",
	                    request,
	                    UserServiceResponseDto.class
	            );
	
	    if (serviceResponse == null
	            || serviceResponse.getUser() == null) {
	        throw new ResponseStatusException(
	                HttpStatus.BAD_GATEWAY,
	                "User Service returned an invalid response"
	        );
	    }
	
	    CreatePublicUserResponseDto response =
	            new CreatePublicUserResponseDto();
	
	    response.setUser(serviceResponse.getUser());
	
	    return response;
	    
	}

	public CreatePublicListingResponseDto createListing(
	        CreatePublicListingRequestDto requestDto) {
	
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(
	            MediaType.APPLICATION_FORM_URLENCODED
	    );
	
	    MultiValueMap<String, String> form =
	            new LinkedMultiValueMap<>();
	
	    form.add("userId", requestDto.getUserId().toString());
	    form.add("listingType", requestDto.getListingType());
	    form.add("price", requestDto.getPrice().toString());
	
	    HttpEntity<MultiValueMap<String, String>> request =
	            new HttpEntity<>(form, headers);
	
	    CreateListingServiceResponseDto serviceResponse =
	            restTemplate.postForObject(
	                    listingServiceUrl + "/listings",
	                    request,
	                    CreateListingServiceResponseDto.class
	            );
	
	    if (serviceResponse == null
	            || serviceResponse.getListing() == null) {
	        throw new ResponseStatusException(
	                HttpStatus.BAD_GATEWAY,
	                "Listing Service returned an invalid response"
	        );
	    }
	
	    CreatePublicListingResponseDto response =
	            new CreatePublicListingResponseDto();
	
	    response.setListing(serviceResponse.getListing());
	
	    return response;
	    
	}

    
}




