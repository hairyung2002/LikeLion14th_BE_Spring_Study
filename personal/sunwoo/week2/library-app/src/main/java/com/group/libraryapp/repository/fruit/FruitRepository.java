package com.group.libraryapp.repository.fruit;

import java.time.LocalDate;
import java.util.List;

public interface FruitRepository {
    void saveFruit(String name, LocalDate warehousingDate, long price);

    void updateFruit(long id);

    boolean isFruitNotExist(long id);

    List<Long> findSalesAmount(String name);

    List<Long> findNotSalesAmounts(String name);



}
