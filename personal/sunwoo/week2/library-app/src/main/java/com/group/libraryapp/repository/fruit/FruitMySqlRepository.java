package com.group.libraryapp.repository.fruit;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Primary
public class FruitMySqlRepository implements FruitRepository {

    private final JdbcTemplate jdbcTemplate;
    public FruitMySqlRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveFruit(String name, LocalDate warehousingDate, long price) {
        String sql = "INSERT INTO fruit (name, warehousingDate, price) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, name, warehousingDate, price);
    }

    @Override
    public void updateFruit(long id) {
        String sql = "UPDATE fruit SET is_sold = 1 WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean isFruitNotExist(long id) {
        String readSql = "SELECT * FROM fruit WHERE id = ?";
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, id).isEmpty();
    }

    @Override
    public List<Long> findSalesAmount(String name) {
        String sales = "SELECT price FROM fruit WHERE name = ? and is_sold = 1";
        return jdbcTemplate.query(sales, (rs, rowNum) -> rs.getLong("price"), name);
    }

    @Override
    public List<Long> findNotSalesAmounts(String name) {
        String notsales = "SELECT price FROM fruit WHERE name = ? and is_sold = 0";
        return jdbcTemplate.query(notsales, (rs, rowNum) -> rs.getLong("price"), name);
    }
}
