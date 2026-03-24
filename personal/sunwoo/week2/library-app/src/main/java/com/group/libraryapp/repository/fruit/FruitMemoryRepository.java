package com.group.libraryapp.repository.fruit;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class FruitMemoryRepository implements FruitRepository {

    @Override
    public void saveFruit(String name, LocalDate warehousingDate, long price) {

    }

    @Override
    public void updateFruit(long id) {

    }

    @Override
    public boolean isFruitNotExist(long id) {
        return false;
    }

    @Override
    public List<Long> findSalesAmount(String name) {
        return List.of();
    }

    @Override
    public List<Long> findNotSalesAmounts(String name) {
        return List.of();
    }
}
