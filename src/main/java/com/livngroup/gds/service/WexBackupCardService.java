package com.livngroup.gds.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetBackupCards;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetBackupCardsResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistory;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.User;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.UserToken;
import com.livngroup.gds.domain.WexAccount;
import com.livngroup.gds.domain.WexUser;
import com.livngroup.gds.domain.WexUserToken;

@Service
public class WexBackupCardService {
	@Autowired
	private WexClientFactory clientFactory;
	protected static Logger log = Logger.getLogger("WexBackupCardService");

	public Object getBackupCards(WexUserToken aToken, WexAccount aAccount, String orderId) {
		GetBackupCardsResponse result = null;
		
		PurchaseLogServiceStub wexService = clientFactory.getWexClient();
//		try {
//			
//			GetBackupCards reqObj = new GetBackupCards();
//			
//			reqObj.setUser((UserToken)aToken);
//			GetBackupCardsRequest reqData = new GetBackupCardsRequest();
//			reqData.setBankNumber(aAccount.getBankNo());
//			reqData.setCompanyNumber(aAccount.getComNo());
//			reqData.setPurchaseLogUniqueID(orderId);
//			reqObj.setRequest(reqData);
//			
//			result = wexService.getBackupCards(reqObj);
//			
//		} catch(java.rmi.RemoteException e) {
//			log.error("WEX has exception. It could be caused by Server side and network.");
//			log.debug(e);
//		}
		
		return result;
	}
	
}
