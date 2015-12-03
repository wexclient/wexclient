package com.livngroup.gds.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub;

@Component
public class WexClientFactory {

//	final static private String WEX_WSDL_URL = "https://services.encompass-suite.com/services/PurchaseLogService.asmx?wsdl";
	@Autowired
	WexProperties wexProperty;
	PurchaseLogServiceStub wexService;
	
	public WexClientFactory() {
		try {
//			wexService = new PurchaseLogServiceStub(WEX_WSDL_URL);
			wexService = new PurchaseLogServiceStub(wexProperty.getWsdl());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public PurchaseLogServiceStub getWexClient() {
		try {
			if(wexService == null) {
//				wexService = new PurchaseLogServiceStub(WEX_WSDL_URL);
				wexService = new PurchaseLogServiceStub(wexProperty.getWsdl());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return this.wexService;
	}
}
