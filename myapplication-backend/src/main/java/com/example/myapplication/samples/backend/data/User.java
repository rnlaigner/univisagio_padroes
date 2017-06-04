package com.example.myapplication.samples.backend.data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class User implements Serializable {

    @NotNull
    private int id;
    @NotNull
    private String name;
    
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
    public String toString() {
        return getName();
    }
}
