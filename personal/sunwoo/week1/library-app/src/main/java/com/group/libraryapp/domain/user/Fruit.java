package com.group.libraryapp.domain.user;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Fruit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(name = "warehousingDate", nullable = false)
    private LocalDate warehousingDate;

    @Column(nullable = false)
    private long price;

    @Column(name = "is_sold", nullable = false)
    private boolean isSold;

    protected Fruit() {
    }

    public Fruit(String name, LocalDate warehousingDate, Long price) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다.", name));
        }
        this.name = name;
        this.warehousingDate = warehousingDate;
        this.price = price;
        this.isSold = false;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getWarehousingDate() {
        return warehousingDate;
    }

    public long getPrice() {
        return price;
    }

    public boolean isSold() {
        return isSold;
    }

    public void updateSold(boolean isSold) {
        this.isSold = isSold;
    }

}
