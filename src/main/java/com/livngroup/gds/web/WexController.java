package com.livngroup.gds.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub;
import com.livngroup.gds.domain.WexUser;

public abstract class WexController {
	
	final protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("wexUser")
	protected WexUser wexUser;
	
	@Autowired
	@Qualifier("purchaseLogServiceStub")
	protected PurchaseLogServiceStub purchaseLogServiceStub;
	
}
