package com.group.libraryapp.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FruitRepository extends JpaRepository<Fruit, Long> {

    @Query("select f from Fruit f where f.isSold = false and f.price >= :price")
    List<Fruit> findAllNotSoldByPriceGte(@Param("price") long price);

    @Query("select f from Fruit f where f.isSold = false and f.price <= :price")
    List<Fruit> findAllNotSoldByPriceLte(@Param("price") long price);

    long countByName(String name);

    @Query("select coalesce(sum(f.price), 0) from Fruit f where f.name = :name and f.isSold = true")
    long getSalesAmountByName(@Param("name") String name);

    @Query("select coalesce(sum(f.price), 0) from Fruit f where f.name = :name and f.isSold = false")
    long getNotSalesAmountByName(@Param("name") String name);
}

