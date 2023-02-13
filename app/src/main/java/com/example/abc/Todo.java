package com.example.abc;

public class Todo {

    private String title;
    private String description;

    public Todo(int id, String title, String description) {

        this.title = title;
        this.description = description;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
