package com.livngroup.gds.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("wex")
public class WexProperties {
	private String wsdl = "https://services.encompass-suite.com/services/PurchaseLogService.asmx?wsdl";

	public String getWsdl() {
		return wsdl;
	}

	public void setWsdl(String wsdl) {
		this.wsdl = wsdl;
	}
}
