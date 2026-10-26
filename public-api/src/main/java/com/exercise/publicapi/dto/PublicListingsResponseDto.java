
package com.exercise.publicapi.dto;

import java.util.List;

public class PublicListingsResponseDto {
	
    private boolean result;
    private List<PublicListingDto> listings;

    public boolean isResult() { 
    	
    	return result; 
    	
    }
    
    public void setResult(boolean result) { 
    	
    	this.result = result; 
    	
    }

    public List<PublicListingDto> getListings() { 
    	
    	return listings; 
    	
    }
    
    public void setListings(List<PublicListingDto> listings) {
    	
        this.listings = listings;
        
    }
    
}


