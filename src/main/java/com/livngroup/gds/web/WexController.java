package com.livngroup.gds.web;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub;
import com.google.gson.Gson;
import com.livngroup.gds.domain.WexUser;

public abstract class WexController {
	
	final protected Logger logger = Logger.getLogger(this.getClass());
	
	final static private Gson GSON = new Gson();  
	
	@Autowired
	@Qualifier("wexUser")
	protected WexUser wexUser;
	
	@Autowired
	@Qualifier("purchaseLogServiceStub")
	protected PurchaseLogServiceStub purchaseLogServiceStub;
	
	protected String convertJson(Map<String, String> map) {
        return GSON.toJson(map);
    }
	
}
