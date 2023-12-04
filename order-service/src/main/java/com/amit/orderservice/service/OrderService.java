package com.amit.orderservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amit.orderservice.dto.OrderLineItemsDto;
import com.amit.orderservice.dto.OrderRequest;
import com.amit.orderservice.model.Order;
import com.amit.orderservice.model.OrderLineItems;
import com.amit.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final OrderRepository orderRepository;

	public void placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());

		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToDto)
				.toList();

		order.setOrderLinerItemsList(orderLineItems);

		orderRepository.save(order);

	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		return OrderLineItems.builder().price(orderLineItemsDto.getPrice()).quantity(orderLineItemsDto.getQuantity())
				.skuCode(orderLineItemsDto.getSkuCode()).build();
	}
}
