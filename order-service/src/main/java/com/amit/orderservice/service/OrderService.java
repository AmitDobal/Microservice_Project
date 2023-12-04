package com.amit.orderservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

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
	private final WebClient webClient;

	public void placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());

		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToDto)
				.toList();

		order.setOrderLinerItemsList(orderLineItems);

		List<String> skuCodes = order.getOrderLinerItemsList().stream().map(OrderLineItems::getSkuCode).toList();

		/*
		 * Call inventory service, and place order if product is in stock
		 */
		Boolean result = webClient.get()
				.uri("http://localhost:8082/api/inventory",
						uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build()) // http://localhost:8082/api/inventory?skuCode=iphone-13&skuCode=iphone13-red
				.retrieve().bodyToMono(Boolean.class).block();
		if (result) {
			orderRepository.save(order);
		} else {
			throw new IllegalArgumentException("Product is not in stock, please try again later.");
		}

	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		return OrderLineItems.builder().price(orderLineItemsDto.getPrice()).quantity(orderLineItemsDto.getQuantity())
				.skuCode(orderLineItemsDto.getSkuCode()).build();
	}
}
