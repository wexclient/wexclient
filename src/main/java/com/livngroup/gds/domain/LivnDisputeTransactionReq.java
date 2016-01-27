package com.livngroup.gds.domain;

import java.math.BigDecimal;

public class LivnDisputeTransactionReq {
	String		bankNumber;
	String		companyNumber;
	String		accountNumber;
	String		transactionRefenceNumber;
	String		purchaseLogUniqueID;
	BigDecimal	disputeAmount;
	String		disputeRecpients;
	int			disputeReason;
	Boolean		closeDispute;
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
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getTransactionRefenceNumber() {
		return transactionRefenceNumber;
	}
	public void setTransactionRefenceNumber(String transactionRefenceNumber) {
		this.transactionRefenceNumber = transactionRefenceNumber;
	}
	public String getPurchaseLogUniqueID() {
		return purchaseLogUniqueID;
	}
	public void setPurchaseLogUniqueID(String purchaseLogUniqueID) {
		this.purchaseLogUniqueID = purchaseLogUniqueID;
	}
	public BigDecimal getDisputeAmount() {
		return disputeAmount;
	}
	public void setDisputeAmount(BigDecimal disputeAmount) {
		this.disputeAmount = disputeAmount;
	}
	public String getDisputeRecpients() {
		return disputeRecpients;
	}
	public void setDisputeRecpients(String disputeRecpients) {
		this.disputeRecpients = disputeRecpients;
	}
	public int getDisputeReason() {
		return disputeReason;
	}
	public void setDisputeReason(int disputeReason) {
		this.disputeReason = disputeReason;
	}
	public Boolean getCloseDispute() {
		return closeDispute;
	}
	public void setCloseDispute(Boolean closeDispute) {
		this.closeDispute = closeDispute;
	}
}
