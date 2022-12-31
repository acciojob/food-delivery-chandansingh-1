package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.OrderDetailsResponse;
import com.driver.service.impl.OrderServiceImpl;
import com.driver.shared.dto.OrderDto;
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
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	OrderServiceImpl orderService;
	@GetMapping(path="/{id}")
	public OrderDetailsResponse getOrder(@PathVariable String id) throws Exception{

		OrderDto orderDto=orderService.getOrderById(id);

		OrderDetailsResponse response= OrderDetailsResponse.builder()
				.userId(orderDto.getUserId())
				.orderId(orderDto.getOrderId())
				.status(orderDto.isStatus())
				.items(orderDto.getItems())
				.cost(orderDto.getCost())
				.build();
		return response;
	}
	
	@PostMapping()
	public OrderDetailsResponse createOrder(@RequestBody OrderDetailsRequestModel order) {
		String orderId = "Abcdyde"+Math.random()*86388;
		boolean status = false;

		OrderDto orderDto = OrderDto.builder()
				.orderId(orderId)
				.status(status)
				.cost(order.getCost())
				.items(order.getItems())
				.userId(order.getUserId())
				.build();

		orderService.createOrder(orderDto);

		OrderDetailsResponse response=OrderDetailsResponse.builder()
				.orderId(orderId)
				.status(status)
				.cost(order.getCost())
				.items(order.getItems())
				.userId(order.getUserId())
				.build();
		return response;
	}
		
	@PutMapping(path="/{id}")
	public OrderDetailsResponse updateOrder(@PathVariable String id, @RequestBody OrderDetailsRequestModel order) throws Exception{

		OrderDto orderDto= OrderDto.builder()
				.orderId(id)
				.status(false)
				.cost(order.getCost())
				.items(order.getItems())
				.userId(order.getUserId())
				.build();

		orderService.updateOrderDetails(id, orderDto);

		OrderDetailsResponse response=OrderDetailsResponse.builder()
				.orderId(id)
				.status(orderDto.isStatus())
				.cost(orderDto.getCost())
				.items(orderDto.getItems())
				.userId(orderDto.getUserId())
				.build();
		return response;
	}
	
	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteOrder(@PathVariable String id) throws Exception {
		orderService.deleteOrder(id);

		OperationStatusModel result=OperationStatusModel.builder()
				.operationResult("Successful").operationName("Delete").build();
		return result;
	}
	
	@GetMapping()
	public List<OrderDetailsResponse> getOrders() {
		List<OrderDto> list = orderService.getOrders();
		List<OrderDetailsResponse> ansList = new ArrayList<>();
		for(OrderDto orderDto : list) {
			OrderDetailsResponse response=OrderDetailsResponse.builder()
					.orderId(orderDto.getOrderId())
					.status(orderDto.isStatus())
					.cost(orderDto.getCost())
					.items(orderDto.getItems())
					.userId(orderDto.getUserId())
					.build();

			ansList.add(response);
		}
		return ansList;
	}
}
