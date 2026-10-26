
package com.exercise.publicapi.dto;

public class UserServiceResponseDto {
	
    private boolean result;
    private UserDto user;

    public boolean isResult() { 
    	
    	return result; 
    	
    }
    
    public void setResult(boolean result) { 
    	
    	this.result = result; 
    	
    }

    public UserDto getUser() { 
    	
    	return user; 
    	
    }
    
    public void setUser(UserDto user) { 
    	
    	this.user = user; 
    	
    }
    
}


