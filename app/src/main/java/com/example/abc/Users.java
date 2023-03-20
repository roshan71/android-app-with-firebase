package com.example.abc;

public class Users {
    public String email;
    public String user;
    public String password;

    public Users(String email, String user, String password) {
        this.email = email;
        this.user = user;
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
