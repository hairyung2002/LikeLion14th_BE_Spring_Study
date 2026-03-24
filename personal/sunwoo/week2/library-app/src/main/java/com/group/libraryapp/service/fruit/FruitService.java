package com.group.libraryapp.service.fruit;

import com.group.libraryapp.domain.user.Fruit;
import com.group.libraryapp.domain.user.FruitRepository;
import com.group.libraryapp.dto.fruit.request.FruitCreateRequest;
import com.group.libraryapp.dto.fruit.request.FruitUpdateRequest;
import com.group.libraryapp.dto.fruit.response.FruitCountResponse;
import com.group.libraryapp.dto.fruit.response.FruitListResponse;
import com.group.libraryapp.dto.fruit.response.FruitSalesAmountResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FruitService {
    private final FruitRepository fruitRepository;

    public FruitService(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    @Transactional
    public void saveFruit(FruitCreateRequest request) {
        fruitRepository.save(new Fruit(request.getName(), request.getWarehousingDate(), request.getPrice()));
    }

    @Transactional
    public void updateFruit(FruitUpdateRequest request) {
        Fruit fruit = fruitRepository.findById(request.getId())
                .orElseThrow(IllegalArgumentException::new);

        fruit.updateSold(request.isSold());
    }

    @Transactional(readOnly = true)
    public FruitCountResponse getFruitCount(String name) {
        return new FruitCountResponse(fruitRepository.countByName(name));
    }

    @Transactional(readOnly = true)
    public List<FruitListResponse> getFruitList(String option, long price) {
        List<Fruit> fruits;
        if ("GTE".equals(option)) {
            fruits = fruitRepository.findAllNotSoldByPriceGte(price);
        } else if ("LTE".equals(option)) {
            fruits = fruitRepository.findAllNotSoldByPriceLte(price);
        } else {
            throw new IllegalArgumentException("option must be GTE or LTE");
        }

        return fruits.stream()
                .map(FruitListResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FruitSalesAmountResponse getFruitSalesAmount(String name) {
        long salesAmount = fruitRepository.getSalesAmountByName(name);
        long notSalesAmount = fruitRepository.getNotSalesAmountByName(name);

        return new FruitSalesAmountResponse(salesAmount, notSalesAmount);
    }

}
