package com.group.libraryapp.dto.fruit.response;

import com.group.libraryapp.domain.user.Fruit;

import java.time.LocalDate;

public class FruitListResponse {
    private final String name;
    private final long price;
    private final LocalDate warehousingDate;

    public FruitListResponse(Fruit fruit) {
        this.name = fruit.getName();
        this.price = fruit.getPrice();
        this.warehousingDate = fruit.getWarehousingDate();
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public LocalDate getWarehousingDate() {
        return warehousingDate;
    }
}

