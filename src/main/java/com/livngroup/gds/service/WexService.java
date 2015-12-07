package com.livngroup.gds.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub;
import com.livngroup.gds.domain.WexUser;
import com.livngroup.gds.domain.WexUserToken;

public abstract class WexService {
	
	final protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("wexUser")
	protected WexUser wexUser;
	
	@Autowired
	@Qualifier("wexUserToken")
	protected WexUserToken wexUserToken;
	
	@Autowired
	@Qualifier("purchaseLogServiceStub")
	protected PurchaseLogServiceStub purchaseLogServiceStub;
	
}
