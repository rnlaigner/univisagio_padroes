package com.example.myapplication.samples.authentication;

import com.example.myapplication.samples.backend.DataService;

/**
 * Default mock implementation of {@link AccessControl}. This implementation
 * accepts any string as a password, and considers the user "admin" as the only
 * administrator.
 */
public class BasicRegisterControl implements RegisterControl {

    @Override
    public boolean register(String username, String password, String confirmPassword) {
    	
    	if(!password.equals(confirmPassword))
    		return false;
    	
        if (DataService.get().userExists(username))
            return false;
        
        DataService.get().addUser(username, password);

        CurrentUser.set(username);
        return true;
    }

}
