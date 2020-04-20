package com.example.api.rest.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "Product")
public class Product extends RepresentationModel<Product> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	private String description;
	private BigDecimal value;

	@ManyToOne
	@JoinColumn(name = "purchaseId")
	private Purchase purchase;

	public Product() {

	}

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	public Product(ProductBuilder builder) {
		this.productId = builder.productId;
		this.description = builder.description;
		this.value = builder.value;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public static class ProductBuilder {
		private Long productId;
		private String description;
		private BigDecimal value;

		public ProductBuilder productId(Long productId) {
			this.productId = productId;
			return this;
		}

		public ProductBuilder description(String description) {
			this.description = description;
			return this;
		}

		public ProductBuilder value(BigDecimal value) {
			this.value = value;
			return this;
		}

		public Product build() {
			return new Product(this);
		}

	}
}
