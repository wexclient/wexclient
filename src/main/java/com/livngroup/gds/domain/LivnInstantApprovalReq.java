package com.livngroup.gds.domain;

import java.math.BigDecimal;

public class LivnInstantApprovalReq {
	String	bankNumber;
	String	companyNumber;
	String	purchaseLogUniqueID;
	String	accountNumber;
	BigDecimal	uppperBound;
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
	public BigDecimal getUppperBound() {
		return uppperBound;
	}
	public void setUppperBound(BigDecimal uppperBound) {
		this.uppperBound = uppperBound;
	}
}