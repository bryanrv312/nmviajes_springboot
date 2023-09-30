package com.nmviajes.app;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PayPalTransactionDetails {
	
	@JsonProperty("id")
    private String orderId;

    @JsonProperty("payer.name.given_name")
    private String payerName;

    @JsonProperty("payer.email_address")
    private String payerEmail;

    @JsonProperty("purchase_units[0].amount.value")
    private double purchaseAmount;

    @JsonProperty("purchase_units[0].amount.currency_code")
    private String currencyCode;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayerEmail() {
		return payerEmail;
	}

	public void setPayerEmail(String payerEmail) {
		this.payerEmail = payerEmail;
	}

	public double getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(double purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@Override
	public String toString() {
		return "PayPalTransactionDetails [orderId=" + orderId + ", payerName=" + payerName + ", payerEmail="
				+ payerEmail + ", purchaseAmount=" + purchaseAmount + ", currencyCode=" + currencyCode + "]";
	}

    

}
