package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.UserResponse;
import com.driver.service.impl.FoodServiceImpl;
import com.driver.shared.dto.FoodDto;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foods")
public class FoodController {

	@Autowired
	FoodServiceImpl foodService;
	@GetMapping(path="/{id}")
	public FoodDetailsResponse getFood(@PathVariable String id) throws Exception{

		FoodDto foodDto=foodService.getFoodById(id);
		FoodDetailsResponse response=FoodDetailsResponse.builder().foodId(foodDto.getFoodId())
				.foodCategory(foodDto.getFoodCategory())
				.foodPrice(foodDto.getFoodPrice())
				.foodName(foodDto.getFoodName()).build();
		return response;
	}

	@PostMapping("/create")
	public FoodDetailsResponse createFood(@RequestBody FoodDetailsRequestModel foodDetails) {
		String foodId = foodDetails.getFoodName()+foodDetails.getFoodCategory()+Math.random()*1976;
		FoodDto foodDto= FoodDto.builder()
				.foodId(foodId)
				.foodCategory(foodDetails.getFoodCategory())
				.foodPrice(foodDetails.getFoodPrice())
				.foodName(foodDetails.getFoodName()).build();

		foodService.createFood(foodDto);

		FoodDetailsResponse response=FoodDetailsResponse.builder().foodId(foodId)
				.foodCategory(foodDetails.getFoodCategory())
				.foodPrice(foodDetails.getFoodPrice())
				.foodName(foodDetails.getFoodName()).build();
		return response;
	}

	@PutMapping(path="/{id}")
	public FoodDetailsResponse updateFood(@PathVariable String id, @RequestBody FoodDetailsRequestModel foodDetails) throws Exception{
		FoodDto foodDto= FoodDto.builder()
				.foodId(id)
				.foodCategory(foodDetails.getFoodCategory())
				.foodPrice(foodDetails.getFoodPrice())
				.foodName(foodDetails.getFoodName()).build();
		foodService.updateFoodDetails(id, foodDto);

		FoodDetailsResponse response=FoodDetailsResponse.builder().foodId(foodDto.getFoodId())
				.foodCategory(foodDto.getFoodCategory())
				.foodPrice(foodDto.getFoodPrice())
				.foodName(foodDto.getFoodName()).build();
		return response;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteFood(@PathVariable String id) throws Exception{
		foodService.deleteFoodItem(id);
		OperationStatusModel result=OperationStatusModel.builder()
				.operationResult("Successful").operationName("Delete").build();
		return result;
	}
	
	@GetMapping()
	public List<FoodDetailsResponse> getFoods() {
		List<FoodDto> list=foodService.getFoods();
		List<FoodDetailsResponse> ansList=new ArrayList<>();
		for(FoodDto foodDto: list) {
			FoodDetailsResponse foodResponse= FoodDetailsResponse.builder()
					.foodId(foodDto.getFoodId())
					.foodCategory(foodDto.getFoodCategory())
					.foodPrice(foodDto.getFoodPrice())
					.foodName(foodDto.getFoodName()).build();

			ansList.add(foodResponse);
		}
		return ansList;
	}
}
