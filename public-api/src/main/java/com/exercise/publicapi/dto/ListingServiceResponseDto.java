
package com.exercise.publicapi.dto;

import java.util.List;

public class ListingServiceResponseDto {
	
    private boolean result;
    private List<ListingFromServiceDto> listings;

    public boolean isResult() { 
    	
    	return result; 
    	
    }
    
    public void setResult(boolean result) {
    	
    	this.result = result;
    	
    }

    public List<ListingFromServiceDto> getListings() {
    	
    	return listings; 
    	
    }
    
    public void setListings(List<ListingFromServiceDto> listings) {
    	
        this.listings = listings;
        
    }
    
}


