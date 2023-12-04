package com.amit.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amit.productservice.dto.ProductRequest;
import com.amit.productservice.dto.ProductResponse;
import com.amit.productservice.model.Product;
import com.amit.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

//	@Autowired
	private final ProductRepository productRepository;

	public void createProduct(ProductRequest productRequest) {
		Product product = Product.builder().name(productRequest.getName()).description(productRequest.getDescription())
				.price(productRequest.getPrice()).build();

		productRepository.save(product);
		log.info("Product {} is saved", product.getId());
	}

	public List<ProductResponse> getAllProducts() {
		List<Product> products = productRepository.findAll();

		return products.stream().map(product -> mapToProductResponse(product)).toList();
	}

	/**
	 * @param productResponse
	 * @param product
	 */
	private ProductResponse mapToProductResponse(Product product) {
		return ProductResponse.builder().id(product.getId()).name(product.getName())
				.description(product.getDescription()).price(product.getPrice()).build();
	}
}
