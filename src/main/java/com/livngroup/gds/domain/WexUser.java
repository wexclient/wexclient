package com.livngroup.gds.domain;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.UserToken;

public class WexUser extends com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.User {

	private static final long serialVersionUID = 1L;

	public UserToken convertToUserToken() {
		UserToken userToken = new UserToken();
		userToken.setUsername(this.getUsername());
		userToken.setPassword(this.getPassword());
		userToken.setOrgGroupLoginId(this.getOrgGroupLoginId());
		return userToken;
	}
}
