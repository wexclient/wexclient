package com.livngroup.gds.domain;

public class LivnBaseReq {
	String	bankNumber;
	String	companyNumber;
	String	purchaseLogUniqueID;
	
	public LivnBaseReq() {}
	
	public LivnBaseReq(String bankNumber, String companyNumber, String purchaseLogUniqueID) {
		this.bankNumber = bankNumber;
		this.companyNumber = companyNumber;
		this.purchaseLogUniqueID = purchaseLogUniqueID;
	}
	
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
}
