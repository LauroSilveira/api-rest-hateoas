package com.example.api.rest.api.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "Purchase")
public class Purchase extends RepresentationModel<Purchase> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long purchaseId;
	private LocalDateTime transactionDate;
	private PurchaseStatus orderStatus;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "purchaseId")
	private List<Product> products;

	public Purchase() {

	}

	public Purchase(PurchaseBuilder builder) {
		this.purchaseId = builder.purchaseId;
		this.transactionDate = builder.transactionDate;
		this.orderStatus = builder.orderStatus;
		this.products = builder.products;
	}

	public Long getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	public PurchaseStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(PurchaseStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public static class PurchaseBuilder {
		private Long purchaseId;
		private LocalDateTime transactionDate;
		private PurchaseStatus orderStatus;
		private List<Product> products;

		public PurchaseBuilder purchaseId(Long purchaseId) {
			this.purchaseId = purchaseId;
			return this;
		}

		public PurchaseBuilder transactionDate(LocalDateTime transactionDate) {
			this.transactionDate = transactionDate;
			return this;
		}

		public PurchaseBuilder orderStatus(PurchaseStatus orderStatus) {
			this.orderStatus = orderStatus;
			return this;
		}

		public PurchaseBuilder products(List<Product> products) {
			this.products = products;
			return this;
		}

		public Purchase build() {
			return new Purchase(this);
		}

	}
}
