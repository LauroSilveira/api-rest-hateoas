package com.example.api.rest.api.dto;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

public class ProductDto extends RepresentationModel<ProductDto> implements Serializable {

	private static final long serialVersionUID = 1L;
	private String productId;
	private String description;
	private String value;

	public ProductDto() {
	}

	public ProductDto(ProductDtoBuilder builder) {
		this.productId = builder.productId;
		this.description = builder.description;
		this.value = builder.value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public static class ProductDtoBuilder {
		private String productId;
		private String description;
		private String value;

		public ProductDtoBuilder productId(String productId) {
			this.productId = productId;
			return this;
		}

		public ProductDtoBuilder description(String description) {
			this.description = description;
			return this;
		}

		public ProductDtoBuilder value(String value) {
			this.value = value;
			return this;
		}

		public ProductDto build() {
			return new ProductDto(this);
		}

	}
}
