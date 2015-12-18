package com.livngroup.gds.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.BackupCardResponseCode;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetBackupCardsInternationalResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetBackupCardsResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetDisputedTransactionsResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPaymentScheduleResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.OrderBackupCardsResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.PurchaseLogResponseCodeEnum;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.QueryPurchaseLogsResponseE;
import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.WexException;
import com.livngroup.gds.exception.WexRuntimeException;
import com.livngroup.gds.response.ErrorResponse;

@Aspect
@Component
public class WexPurchaseLogServiceSecurityMonitor {

	final protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@AfterReturning(
			pointcut="execution(public * com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.*(..))",
			returning="result")
	public void invalidLogonCredentialsCheck(JoinPoint joinPoint, Object result) throws WexRuntimeException {
			
		WexEntity wexEntity = WexEntity.GENERAL; 
		boolean isInvalidCredentials = false;
		Object errorResult = null;
		
		if (result instanceof GetBackupCardsResponse) {
			GetBackupCardsResponse wexResponse = (GetBackupCardsResponse) result;
			isInvalidCredentials = equalCodes(BackupCardResponseCode.InvalidCredentials, wexResponse.getGetBackupCardsResult().getResponseCode());
			wexEntity = WexEntity.BACKUP_CARD;
			errorResult = wexResponse.getGetBackupCardsResult();
		} else if (result instanceof OrderBackupCardsResponse) {
			OrderBackupCardsResponse wexResponse = (OrderBackupCardsResponse) result;
			isInvalidCredentials = equalCodes(BackupCardResponseCode.InvalidCredentials, wexResponse.getOrderBackupCardsResult().getResponseCode());
			wexEntity = WexEntity.BACKUP_CARD;
			errorResult = wexResponse.getOrderBackupCardsResult();
		} else if (result instanceof GetPaymentScheduleResponse) {
			GetPaymentScheduleResponse wexResponse = (GetPaymentScheduleResponse) result;
			isInvalidCredentials = equalCodes(PurchaseLogResponseCodeEnum.InvalidUserCredentials, wexResponse.getResponseCode());
			wexEntity = WexEntity.PAYMENT_SCHEDULE;
			errorResult = wexResponse.getPaymentSchedule();
		} else if (result instanceof GetBackupCardsInternationalResponse) {
			GetBackupCardsInternationalResponse wexResponse = (GetBackupCardsInternationalResponse) result;
			isInvalidCredentials = equalCodes(BackupCardResponseCode.InvalidCredentials, wexResponse.getGetBackupCardsInternationalResult().getResponseCode());
			wexEntity = WexEntity.BACKUP_CARD_INTERNATIONAL;
			errorResult = wexResponse.getGetBackupCardsInternationalResult();
		} else if (result instanceof GetDisputedTransactionsResponse) {
			GetDisputedTransactionsResponse wexResponse = (GetDisputedTransactionsResponse) result;
			isInvalidCredentials = equalCodes(PurchaseLogResponseCodeEnum.InvalidUserCredentials, wexResponse.getGetDisputedTransactionsResult().getResponseCode());
			wexEntity = WexEntity.DISPUTED_TRANSACTION;
			errorResult = wexResponse.getGetDisputedTransactionsResult();
		} else if (result instanceof GetPurchaseLogHistoryResponseE) {
			GetPurchaseLogHistoryResponseE wexResponse = (GetPurchaseLogHistoryResponseE) result;
			isInvalidCredentials = equalCodes(PurchaseLogResponseCodeEnum.InvalidUserCredentials, wexResponse.getGetPurchaseLogHistoryResult().getResponseCode());
			wexEntity = WexEntity.PURCHASE_LOG;
			errorResult = wexResponse.getGetPurchaseLogHistoryResult();
		} else if (result instanceof QueryPurchaseLogsResponseE) {
			QueryPurchaseLogsResponseE wexResponse = (QueryPurchaseLogsResponseE) result;
			isInvalidCredentials = equalCodes(PurchaseLogResponseCodeEnum.InvalidUserCredentials, wexResponse.getQueryPurchaseLogsResult().getResponseCode());
			wexEntity = WexEntity.PURCHASE_LOG;
			errorResult = wexResponse.getQueryPurchaseLogsResult();
		} else {
			isInvalidCredentials = false;
			wexEntity = WexEntity.GENERAL; 
			errorResult = null;
		} 
		
		if (isInvalidCredentials) {
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

	private <T> boolean equalCodes(T left, T right) {
		return left.equals(right);
	}
	
}
