package com.amit.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.amit.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
