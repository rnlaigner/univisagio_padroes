package com.example.myapplication.samples.authentication;

import java.io.Serializable;

/**
 * Simple interface for registration checks.
 */
public interface RegisterControl extends Serializable {

    public boolean register(String username, String password, String confirmPassword);

}
