package com.amit.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amit.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
