package com.livngroup.gds.domain;

import java.math.BigDecimal;

public class LivnPurchaseLog {

	public static final String UDF_RESERVATION_ID = "Reservation ID";
	public static final String UDF_LEAD_PASSENGER_NAME = "Lead Passenger Name";
	public static final String UDF_INVOICE_NUMBER = "Invoice Number";
	
	BigDecimal amount;
	String reservationId;
	String leadPassengerName;
	String invoiceNumber;
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
	
	
}
