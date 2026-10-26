
package com.exercise.publicapi.dto;

import jakarta.validation.constraints.NotBlank;

public class CreatePublicUserRequestDto {

    @NotBlank
    private String name;

    public String getName() {
    	
        return name;
        
    }

    public void setName(String name) {
    	
        this.name = name;
        
    }
    
    
}


