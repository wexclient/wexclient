package com.livngroup.gds.service;

import org.springframework.stereotype.Service;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistory;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.User;
import com.livngroup.gds.domain.WexAccount;
import com.livngroup.gds.domain.WexUser;

@Service
public class WexPurchaseLogService extends WexService {

	public Object getPurchaseLogHistory(WexAccount aAccount, String uniqueId) {
		GetPurchaseLogHistoryResponse result = null;
		
		try {
			GetPurchaseLogHistoryResponseE res;
			
			GetPurchaseLogHistory reqObj = new GetPurchaseLogHistory();
			reqObj.setUser((User)wexUser);
			
			GetPurchaseLogHistoryRequest reqData = new GetPurchaseLogHistoryRequest();
			reqData.setBankNumber(aAccount.getBankNo());
			reqData.setCompanyNumber(aAccount.getComNo());
			reqData.setPurchaseLogUniqueID(uniqueId);
			
			reqObj.setRequest(reqData);
			
			res = purchaseLogServiceStub.getPurchaseLogHistory(reqObj);
			result = res.getGetPurchaseLogHistoryResult();
			
		} catch(Exception e) {
			logger.error("WEX has exception. It could be caused by Server side and network.");
			logger.debug(e);
		}
		
		return result;
	}
}
