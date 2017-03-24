package com.feliperrm.wikiolap.models;

import java.io.Serializable;

/**
 * Created by felip on 24/03/2017.
 */

public class User implements Serializable {

    private String name;
    private String picture;
    private String email;

    public User() {
    }

    public User(String name, String picture, String email) {
        this.name = name;
        this.picture = picture;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
