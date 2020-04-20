package com.example.api.rest.api.dto;

import java.io.Serializable;

public class ItemDto implements Serializable {

	private static final long serialVersionUID = -7613589731777558105L;
	
	private ProductDto productDto;

	public ItemDto() {

	}

	public ProductDto getProductDto() {
		return productDto;
	}

	public void setProductDto(ProductDto productDto) {
		this.productDto = productDto;
	}

}
