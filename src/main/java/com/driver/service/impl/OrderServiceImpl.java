package com.driver.service.impl;

import com.driver.io.entity.OrderEntity;
import com.driver.io.repository.OrderRepository;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Override
    public OrderDto createOrder(OrderDto order) {

        OrderEntity orderEntity=OrderEntity.builder()
                .userId(order.getUserId())
                .cost(order.getCost())
                .items(order.getItems())
                .orderId(order.getOrderId())
                .status(order.isStatus())
                .build();
        orderRepository.save(orderEntity);

        return null;
    }

    @Override
    public OrderDto getOrderById(String orderId) throws Exception {

        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        OrderDto orderDto=OrderDto.builder()
                .id(orderEntity.getId())
                .orderId(orderEntity.getOrderId())
                .items(orderEntity.getItems())
                .userId(orderEntity.getUserId())
                .cost(orderEntity.getCost())
                .status(orderEntity.isStatus())
                .build();
        return orderDto;
    }

    @Override
    public OrderDto updateOrderDetails(String orderId, OrderDto order) throws Exception {
        OrderEntity existingOrderEntity=orderRepository.findByOrderId(orderId);
        OrderEntity orderEntity=OrderEntity.builder()
                .id(existingOrderEntity.getId())
                .userId(order.getUserId())
                .cost(order.getCost())
                .items(order.getItems())
                .orderId(order.getOrderId())
                .status(order.isStatus())
                .build();

        orderRepository.save(orderEntity);
        return order;
    }

    @Override
    public void deleteOrder(String orderId) throws Exception {
        OrderEntity orderEntity=orderRepository.findByOrderId(orderId);
        orderRepository.delete(orderEntity);
    }

    @Override
    public List<OrderDto> getOrders() {
       List<OrderEntity>  list= (List<OrderEntity>) orderRepository.findAll();
       List<OrderDto> ansList=new ArrayList<>();
       for(OrderEntity orderEntity: list){
           OrderDto orderDto=OrderDto.builder()
                   .id(orderEntity.getId())
                   .orderId(orderEntity.getOrderId())
                   .items(orderEntity.getItems())
                   .userId(orderEntity.getUserId())
                   .cost(orderEntity.getCost())
                   .status(orderEntity.isStatus())
                   .build();
           ansList.add(orderDto);
       }
        return ansList;
    }
}