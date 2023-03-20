package com.example.abc;

public class Task {

    public String userId;

    public String title;
    public String desc;

    public Task(String title, String desc,String userId) {
        this.title = title;
        this.desc = desc;
        this.userId=userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
