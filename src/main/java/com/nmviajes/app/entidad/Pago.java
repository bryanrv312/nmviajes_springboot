package com.nmviajes.app.entidad;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pagos") 
public class Pago {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @Column(name = "orderId")
    private String orderId;
    
    @Column(name = "payerName")
    private String payerName;

    @Column(name = "PayerEmail")
	private String PayerEmail;
    
    @Column(name = "purchaseAmount")
	private String purchaseAmount;
    
    @Column(name = "currencyCode")
	private String currencyCode;
    
    @Column(name = "mensajePago")
	private String mensajePago;

    public Pago() {
    }
    
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
		return PayerEmail;
	}

	public void setPayerEmail(String payerEmail) {
		PayerEmail = payerEmail;
	}

	public String getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(String purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	


	public String getMensajePago() {
		return mensajePago;
	}


	public void setMensajePago(String mensajePago) {
		this.mensajePago = mensajePago;
	}


	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Pago other = (Pago) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


	@Override
	public String toString() {
		return "Pago [id=" + id + ", orderId=" + orderId + ", payerName=" + payerName + ", PayerEmail=" + PayerEmail
				+ ", purchaseAmount=" + purchaseAmount + ", currencyCode=" + currencyCode + "]";
	}

	

  

    
    
    
    
}
