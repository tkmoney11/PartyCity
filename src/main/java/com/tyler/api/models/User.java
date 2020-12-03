package com.tyler.api.models;

import java.util.Date;

public class User {
    private int id;
    private String firstName;
    private String lastName;
//    private Date createdAt;
    private String email;
    private String username;
    private String password;
    private boolean administrator;

    public User(int id, String firstName, String lastName, String email, String username, String password, boolean administrator) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
//        this.createdAt = createdAt;
        this.email = email;
        this.username = username;
        this.password = password;
        this.administrator = administrator;
    }

    public User() {
        this.id = -1;
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.username = null;
        this.password = null;
        this.administrator = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

//    public Date getCreatedAt() { return createdAt; }

//    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", administrator=" + administrator +
                '}';
    }


}
