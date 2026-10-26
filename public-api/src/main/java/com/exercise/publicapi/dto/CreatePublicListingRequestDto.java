
package com.exercise.publicapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CreatePublicListingRequestDto {

    @NotNull
    @Min(1)
    private Integer userId;

    @NotBlank
    @Pattern(regexp = "rent|sale")
    private String listingType;

    @NotNull
    @Min(1)
    private Integer price;

    public Integer getUserId() {
    	
        return userId;
        
    }

    public void setUserId(Integer userId) {
    	
        this.userId = userId;
        
    }

    public String getListingType() {
    	
        return listingType;
        
    }

    public void setListingType(String listingType) {
    	
        this.listingType = listingType;
        
    }

    public Integer getPrice() {
    	
        return price;
        
    }

    public void setPrice(Integer price) {
    	
        this.price = price;
        
    }
    
}


