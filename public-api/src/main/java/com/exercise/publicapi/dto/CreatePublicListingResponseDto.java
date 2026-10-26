
package com.exercise.publicapi.dto;

public class CreatePublicListingResponseDto {

    private ListingFromServiceDto listing;

    public ListingFromServiceDto getListing() {
    	
        return listing;
        
    }

    public void setListing(ListingFromServiceDto listing) {
    	
        this.listing = listing;
        
    }
    
}


