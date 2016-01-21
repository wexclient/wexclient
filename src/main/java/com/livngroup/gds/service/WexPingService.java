package com.livngroup.gds.service;

import org.springframework.stereotype.Service;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistory;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponseE;
import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.WexException;

@Service("wexPingService")
public class WexPingService extends WexService {

	@Override
	protected WexEntity getWexEntity() {
		return WexEntity.GENERAL;
	}

	public void ping() throws Exception {
		try {
			GetPurchaseLogHistory reqObj = new GetPurchaseLogHistory();
			reqObj.setUser(wexUser);
			reqObj.setRequest(new GetPurchaseLogHistoryRequest());
			
			GetPurchaseLogHistoryResponseE resEncap = purchaseLogServiceStub.getPurchaseLogHistory(reqObj);
			if ((resEncap == null) || (resEncap.getGetPurchaseLogHistoryResult() == null)) {
				throw new Exception(WexException.MESSAGE_REMOTE_CALL_ERROR);
			}
		} catch (Throwable exc) {
			throw new Exception(exc);
		}
	}
}
