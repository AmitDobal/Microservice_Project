package com.amit.inventoryservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amit.inventoryservice.dto.InventoryResponse;
import com.amit.inventoryservice.model.Inventory;
import com.amit.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

	private final InventoryRepository inventoryRepository;

	@Transactional(readOnly = true)
	public List<InventoryResponse> isInStock(List<String> skuCodes) {
		return inventoryRepository.findBySkuCodeIn(skuCodes).stream().map(inventory -> mapToDto(inventory)).toList();
	}

	private InventoryResponse mapToDto(Inventory inventory) {
		return InventoryResponse.builder().skuCode(inventory.getSkuCode()).isInStock(inventory.getQuantity() > 0)
				.build();
	}
}
