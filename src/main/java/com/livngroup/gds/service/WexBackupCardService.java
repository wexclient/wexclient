package com.livngroup.gds.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class WexBackupCardService extends WexService {

	public Object getBackupCards(WexUserToken aToken, WexAccount aAccount, String orderId) {
		GetBackupCardsResponse result = null;
		
//		try {
//			
//			GetBackupCards reqObj = new GetBackupCards();
//			
//			reqObj.setUser((UserToken)aToken);
//			GetBackupCardsRequest reqData = new BackupCardOrderRequest();
//			
//			reqData.setBankNumber(aAccount.getBankNo());
//			reqData.setCompanyNumber(aAccount.getComNo());
//			reqData.setPurchaseLogUniqueID(orderId);
//			reqObj.setRequest(reqData);
//			
//			result = purchaseLogServiceStub.getBackupCards(reqObj);
//			
//		} catch(java.rmi.RemoteException e) {
//			logger.error("WEX has exception. It could be caused by Server side and network.");
//			logger.debug(e);
//		}
		
		return result;
	}
	
}
