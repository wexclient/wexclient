package com.livngroup.gds.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetBackupCardsInternationalResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetBackupCardsResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetDisputedTransactionsResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPaymentScheduleResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.OrderBackupCardsResponse;
import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.WexException;
import com.livngroup.gds.exception.WexRuntimeException;
import com.livngroup.gds.response.ErrorResponse;

@Aspect
@Component
public class WexPurchaseLogServiceSecurityMonitor {

	private static final String RESPONCE_CODE_INVALID_CREDENTIALS = "InvalidCredentials";
	private static final String RESPONCE_CODE_INVALID_USER_CREDENTIALS = "InvalidUserCredentials";
	
	final protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@AfterReturning(
			pointcut="execution(public * com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.*(..))",
			returning="result")
	public void invalidLogonCredentialsCheck(JoinPoint joinPoint, Object result) throws WexRuntimeException {
			
		String wexResponceCode = "";
		WexEntity wexEntity = WexEntity.GENERAL; 
		Object errorResult = null;
		
		if (result instanceof GetBackupCardsResponse) {
			GetBackupCardsResponse wexResponse = (GetBackupCardsResponse) result;
			wexResponceCode = wexResponse.getGetBackupCardsResult().getResponseCode().getValue();
			wexEntity = WexEntity.BACKUP_CARD;
		} else if (result instanceof OrderBackupCardsResponse) {
			OrderBackupCardsResponse wexResponse = (OrderBackupCardsResponse) result;
			wexResponceCode = wexResponse.getOrderBackupCardsResult().getResponseCode().getValue();
			wexEntity = WexEntity.BACKUP_CARD;
		} else if (result instanceof GetPaymentScheduleResponse) {
			GetPaymentScheduleResponse wexResponse = (GetPaymentScheduleResponse) result;
			wexResponceCode = wexResponse.getResponseCode().getValue();
			wexEntity = WexEntity.PAYMENT_SCHEDULE;
		} else if (result instanceof GetBackupCardsInternationalResponse) {
			GetBackupCardsInternationalResponse wexResponse = (GetBackupCardsInternationalResponse) result;
			wexResponceCode = wexResponse.getGetBackupCardsInternationalResult().getResponseCode().getValue();
			wexEntity = WexEntity.BACKUP_CARD_INTERNATIONAL;
		} else if (result instanceof GetDisputedTransactionsResponse) {
			GetDisputedTransactionsResponse wexResponse = (GetDisputedTransactionsResponse) result;
			wexResponceCode = wexResponse.getGetDisputedTransactionsResult().getResponseCode().getValue();
			wexEntity = WexEntity.DISPUTED_TRANSACTION;
		} else if (result instanceof GetPurchaseLogHistoryResponseE) {
			GetPurchaseLogHistoryResponseE wexResponse = (GetPurchaseLogHistoryResponseE) result;
			wexResponceCode = wexResponse.getGetPurchaseLogHistoryResult().getResponseCode().getValue();
			wexEntity = WexEntity.PURCHASE_LOG;
		} else {
			wexResponceCode = "";
			wexEntity = WexEntity.GENERAL; 
		} 
		
		if (RESPONCE_CODE_INVALID_CREDENTIALS.equals(wexResponceCode) 
				|| RESPONCE_CODE_INVALID_USER_CREDENTIALS.equals(wexResponceCode)) {
			throw new WexRuntimeException(new ErrorResponse(
							HttpStatus.UNAUTHORIZED, 
							ErrorResponse.INVALID_CREDENTIALS_ERROR_CODE, 
							wexEntity, 
							WexException.MESSAGE_INVALID_CREDENTIALS, 
							ErrorResponse.URL_DEFAULT, 
							"",
							errorResult));
		}
	}

}
