package com.livngroup.gds.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub;
import com.livngroup.gds.domain.WexUser;
import com.livngroup.gds.domain.WexUserToken;

@ManagedResource
public abstract class WexService {
	
	final protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
