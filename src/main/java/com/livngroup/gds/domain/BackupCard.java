package com.livngroup.gds.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="WEX_BACKUP_CARD")
public class BackupCard {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "BACKUP_CARD_ID", unique = true)
	private String uuid;
	
	@Column(name = "CARD_NUMBER", unique = true)
	private String cardNumber;
	
	@Column(name = "CVC2", unique = false)
	private String cvc2;

	@Column(name = "EXPIRATION_DATE", unique = false)
	private Date expirationDate;

	@Column(name = "CREDIT_LIMIT", unique = false)
	private BigDecimal creditLimit;

	@Column(name = "CURRENCY", unique = false)
	private Currency currency;

	@Column(name = "NAME_LINE1", unique = false)
	private String nameLine1;

	@Column(name = "NAME_LINE2", unique = false)
	private String nameLine2;

	@Column(name = "CREATE_DATE", unique = false)
	private Date createDate;
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

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
