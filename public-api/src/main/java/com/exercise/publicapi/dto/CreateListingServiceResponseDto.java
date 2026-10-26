
package com.exercise.publicapi.dto;

public class CreateListingServiceResponseDto {

    private boolean result;
    private ListingFromServiceDto listing;

    public boolean isResult() {
    	
        return result;
        
    }

    public void setResult(boolean result) {
    	
        this.result = result;
        
    }

    public ListingFromServiceDto getListing() {
    	
        return listing;
        
    }

    public void setListing(ListingFromServiceDto listing) {
    	
        this.listing = listing;
        
    }
    
    
}


