package com.livngroup.gds.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public class BackupCardInfo {
	private String cardNumber;
	private String cvc2;
	private Date expirationDate;
	private BigDecimal creditLimit;
	private Currency currency;
	private String nameLine1;
	private String nameLine2;
	private Date createDate;
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCvc2() {
		return cvc2;
	}
	public void setCvc2(String cvc2) {
		this.cvc2 = cvc2;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public BigDecimal getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public String getNameLine1() {
		return nameLine1;
	}
	public void setNameLine1(String nameLine1) {
		this.nameLine1 = nameLine1;
	}
	public String getNameLine2() {
		return nameLine2;
	}
	public void setNameLine2(String nameLine2) {
		this.nameLine2 = nameLine2;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
