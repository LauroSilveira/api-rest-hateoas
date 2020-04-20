package com.example.api.rest.api.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.rest.api.dto.PurchaseDto;
import com.example.api.rest.api.dto.ProductDto;
import com.example.api.rest.api.model.Purchase;
import com.example.api.rest.api.model.PurchaseStatus;
import com.example.api.rest.api.model.Product;
import com.example.api.rest.api.repository.PruchaseRepository;

@Service
public class PurchaseService {

	private static final Logger LOG = LoggerFactory.getLogger(PurchaseService.class);

	@Autowired
	private PruchaseRepository repository;

	public void savePurchase(PurchaseDto orderDto) {
		repository.saveAndFlush(mappingOrderDtoToEntity(orderDto));
		LOG.info("Order with ID {} saved", orderDto.getOrderId());
	}

	public Optional<PurchaseDto> getPurchaseById(String id) {
		return mappingEntityToDto(repository.findById((Long.valueOf(id))));

	}

	private Optional<PurchaseDto> mappingEntityToDto(Optional<Purchase> order) {
		return order.map(o -> new PurchaseDto.OrderDtoBuilder().purchaseId(String.valueOf(o.getPurchaseId()))
				.transactionDate(o.getTransactionDate().toString()).producstDto(mappingProductsToDto(o.getProducts()))
				.build());
	}

	private List<ProductDto> mappingProductsToDto(List<Product> products) {
		return products.stream()
				.map(p -> new ProductDto.ProductDtoBuilder()
						.productId(String.valueOf(p.getProductId()))
						.description(p.getDescription())
						.value(String.valueOf(p.getValue())).build())
				.collect(Collectors.toList());
	}

	private Purchase mappingOrderDtoToEntity(PurchaseDto dto) {
		return new Purchase.PurchaseBuilder().orderStatus(PurchaseStatus.PREPARING)
				.transactionDate(parseToDateTime(dto.getTransactionDate()))
				.products(mappingProdcts(dto.getProducstDto())).build();
	}

	private List<Product> mappingProdcts(List<ProductDto> producstDto) {
		return producstDto.stream().map(dto -> new Product.ProductBuilder()
				.description(dto.getDescription())
				.value(new BigDecimal(dto.getValue()))
				.build()).collect(Collectors.toList());
	}

	public static LocalDateTime parseToDateTime(String transactionDate) {
		Instant instant = Instant.parse(transactionDate);
		return LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));
	}

	public List<ProductDto> mappingDtoToEntity(Purchase entity) {
		return entity.getProducts().stream()
				.map(e -> new ProductDto.ProductDtoBuilder().productId(String.valueOf(e.getProductId()))
						.description(e.getDescription()).value(String.valueOf(e.getValue())).build())
				.collect(Collectors.toList());
	}

	public boolean deletePurchaseById(String orderId) {
		Optional<Purchase> orderRetrieved = repository.findById(Long.valueOf(orderId));
		if (orderRetrieved.isEmpty()) {
			return false;
		} else {
			repository.deleteById(Long.valueOf(orderId));
			return true;
		}
	}
	
	public Optional<PurchaseDto> updatePurchaseById(PurchaseDto request, String id) {
		 return  mappingEntityToDto(repository.findById(Long.valueOf(id)).map(e -> {
			 e.setOrderStatus(PurchaseStatus.TRANSIT);
			 e.setTransactionDate(parseToDateTime(request.getTransactionDate()));
			 e.setProducts(updateProdcts(request.getProducstDto()));
			 return repository.saveAndFlush(e);
		 }));
	}

//	public Optional<Purchase> updatePurchaseById(PurchaseDto request, String id) {
//		 return  repository.findById(Long.valueOf(id)).map(e -> {
//			 e.setOrderStatus(OrderStatus.TRANSIT);
//			 e.setTransactionDate(parseToDateTime(request.getTransactionDate()));
//			 e.setProducts(updateProdcts(request.getProducstDto()));
//			 return repository.saveAndFlush(e);
//		 });
//	}
	
	private List<Product> updateProdcts(List<ProductDto> producstDto) {
		return producstDto.stream().map(dto -> new Product.ProductBuilder()
				.productId(Long.valueOf(dto.getProductId()))
				.description(dto.getDescription())
				.value(new BigDecimal(dto.getValue()))
				.build()).collect(Collectors.toList());
	}

	public Optional<List<PurchaseDto>> getAllPurchases() {
		return Optional.of(mappingListPurchaseToDto(repository.findAll()));
		
	}

	private List<PurchaseDto> mappingListPurchaseToDto(List<Purchase> purchases) {
		return purchases.stream().map(pur -> new PurchaseDto.OrderDtoBuilder()
				.purchaseId(String.valueOf(pur.getPurchaseId()))
				.transactionDate(pur.getTransactionDate().toString())
				.producstDto(pur.getProducts().stream().map(pu -> new ProductDto.ProductDtoBuilder()
						.productId(String.valueOf(pu.getProductId()))
						.description(pu.getDescription())
						.value(String.valueOf(pu.getValue()))
						.build()).collect(Collectors.toList()))
				.build()).collect(Collectors.toList());
	}
}
