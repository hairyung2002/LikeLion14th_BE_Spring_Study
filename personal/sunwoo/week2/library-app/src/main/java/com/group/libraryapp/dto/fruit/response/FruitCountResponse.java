package com.group.libraryapp.dto.fruit.response;

public class FruitCountResponse {
    private final long count;

    public FruitCountResponse(long count) {
        this.count = count;
    }

    public long getCount() {
        return count;
    }
}

