package com.livngroup.gds.domain;

import java.math.BigDecimal;

public class LivnPurchaseLogUpdateReq {

	public static final String UDF_RESERVATION_ID = "Reservation ID";
	public static final String UDF_LEAD_PASSENGER_NAME = "Lead Passenger Name";
	public static final String UDF_INVOICE_NUMBER = "Invoice Number";
	
	String	bankNumber;
	String	companyNumber;
	String	purchaseLogUniqueID;
	BigDecimal	amount;
	String	activeFromDate;
	String	activeToDate;
	String	deliveryMethod;
	String	DeliveryAddress;
	BigDecimal	creditLimit;
	String	reservationId;
	String	leadPassengerName;
	String	invoiceNumber;
	public String getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	public String getCompanyNumber() {
		return companyNumber;
	}
	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}
	public String getPurchaseLogUniqueID() {
		return purchaseLogUniqueID;
	}
	public void setPurchaseLogUniqueID(String purchaseLogUniqueID) {
		this.purchaseLogUniqueID = purchaseLogUniqueID;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getActiveFromDate() {
		return activeFromDate;
	}
	public void setActiveFromDate(String activeFromDate) {
		this.activeFromDate = activeFromDate;
	}
	public String getActiveToDate() {
		return activeToDate;
	}
	public void setActiveToDate(String activeToDate) {
		this.activeToDate = activeToDate;
	}
	public String getDeliveryMethod() {
		return deliveryMethod;
	}
	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	public String getDeliveryAddress() {
		return DeliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		DeliveryAddress = deliveryAddress;
	}
	public String getReservationId() {
		return reservationId;
	}
	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}
	public String getLeadPassengerName() {
		return leadPassengerName;
	}
	public void setLeadPassengerName(String leadPassengerName) {
		this.leadPassengerName = leadPassengerName;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public BigDecimal getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}
}
