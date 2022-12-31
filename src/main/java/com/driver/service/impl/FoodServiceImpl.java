package com.driver.service.impl;

import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodRepository foodRepository;
    @Override
    public FoodDto createFood(FoodDto food) {
        FoodEntity foodEntity=FoodEntity.builder()
                .foodId(food.getFoodId())
                .foodCategory(food.getFoodCategory())
                .foodName(food.getFoodName())
                .foodPrice(food.getFoodPrice()).build();
        foodRepository.save(foodEntity);

        return null;
    }

    @Override
    public FoodDto getFoodById(String foodId) throws Exception {

        FoodEntity foodEntity=foodRepository.findByFoodId(foodId);

        FoodDto foodDto=FoodDto.builder()
                .id(foodEntity.getId())
                .foodId(foodEntity.getFoodId())
                .foodName(foodEntity.getFoodName())
                .foodPrice(foodEntity.getFoodPrice())
                .foodCategory(foodEntity.getFoodCategory()).build();

        return foodDto;
    }

    @Override
    public FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws Exception {

        FoodEntity existingFoodEntity=foodRepository.findByFoodId(foodId);
        FoodEntity foodEntity=FoodEntity.builder()
                .id(existingFoodEntity.getId())
                .foodName(foodDetails.getFoodName())
                .foodPrice(foodDetails.getFoodPrice())
                .foodCategory(foodDetails.getFoodCategory()).foodId(foodId).build();
        foodRepository.save(foodEntity);
        return foodDetails;
    }

    @Override
    public void deleteFoodItem(String id) throws Exception {
        foodRepository.delete(foodRepository.findByFoodId(id));
    }

    @Override
    public List<FoodDto> getFoods() {
        Iterable<FoodEntity> list = foodRepository.findAll();
        List<FoodDto> ansList=new ArrayList<>();
        for(FoodEntity foodEntity: list) {
            FoodDto foodDto=FoodDto.builder()
                    .id(foodEntity.getId())
                    .foodCategory(foodEntity.getFoodId())
                    .foodPrice(foodEntity.getFoodPrice())
                    .foodName(foodEntity.getFoodName())
                    .foodId(foodEntity.getFoodId()).build();
            ansList.add(foodDto);
        }
        return ansList;
    }
}
