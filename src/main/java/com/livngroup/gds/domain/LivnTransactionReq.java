package com.livngroup.gds.domain;

public class LivnTransactionReq {
	String	bankNumber;
	String	companyNumber;
	String	purchaseLogUniqueID;
	String	accountNumber;
	int		maxReturned;
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
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public int getMaxReturned() {
		return maxReturned;
	}
	public void setMaxReturned(int maxReturned) {
		this.maxReturned = maxReturned;
	}
}
