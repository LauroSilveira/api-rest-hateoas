package com.example.api.rest.api.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

public class PurchaseDto extends RepresentationModel<PurchaseDto> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String purchaseId;
	private String transactionDate;
	private List<ProductDto> producstDto;

	public PurchaseDto() {

	}

	public PurchaseDto(OrderDtoBuilder builder) {
		this.purchaseId = builder.purchaseId;
		this.transactionDate = builder.transactionDate;
		this.producstDto = builder.producstDto;
	}

	public String getOrderId() {
		return purchaseId;
	}

	public void setOrderId(String orderId) {
		this.purchaseId = orderId;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String dateTransaction) {
		this.transactionDate = dateTransaction;
	}

	public List<ProductDto> getProducstDto() {
		return producstDto;
	}

	public void setProducstDto(List<ProductDto> producstDto) {
		this.producstDto = producstDto;
	}

	public static class OrderDtoBuilder {
		private String purchaseId;
		private String transactionDate;
		private List<ProductDto> producstDto;

		public OrderDtoBuilder purchaseId(String purchaseId) {
			this.purchaseId = purchaseId;
			return this;
		}

		public OrderDtoBuilder transactionDate(String transactionDate) {
			this.transactionDate = transactionDate;
			return this;
		}

		public OrderDtoBuilder producstDto(List<ProductDto> producstDto) {
			this.producstDto = producstDto;
			return this;
		}

		public PurchaseDto build() {
			return new PurchaseDto(this);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((producstDto == null) ? 0 : producstDto.hashCode());
			result = prime * result + ((purchaseId == null) ? 0 : purchaseId.hashCode());
			result = prime * result + ((transactionDate == null) ? 0 : transactionDate.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			OrderDtoBuilder other = (OrderDtoBuilder) obj;
			if (producstDto == null) {
				if (other.producstDto != null)
					return false;
			} else if (!producstDto.equals(other.producstDto))
				return false;
			if (purchaseId == null) {
				if (other.purchaseId != null)
					return false;
			} else if (!purchaseId.equals(other.purchaseId))
				return false;
			if (transactionDate == null) {
				if (other.transactionDate != null)
					return false;
			} else if (!transactionDate.equals(other.transactionDate))
				return false;
			return true;
		}
	}
}
