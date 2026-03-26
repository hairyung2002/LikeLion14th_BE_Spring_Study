package com.group.libraryapp.dto.book.request;

public class SaveBookRequest {
    private String Name;

    public SaveBookRequest(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
