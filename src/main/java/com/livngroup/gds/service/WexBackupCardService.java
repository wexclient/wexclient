package com.livngroup.gds.service;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.ArrayOfBackupCardOrderBlock;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.BackupCardInternationalResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.BackupCardOrderBlock;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.BackupCardOrderRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.BackupCardOrderResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.BackupCardRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.BackupCardResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.BackupCardResponseCode;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.CreatePurchaseLog;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.CreatePurchaseLogRequest;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.CreatePurchaseLogResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.CreatePurchaseLogResponseE;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetBackupCards;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetBackupCardsInternational;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetBackupCardsInternationalResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetBackupCardsResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetBackupCardsWithoutWaiting;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetBackupCardsWithoutWaitingInternational;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetBackupCardsWithoutWaitingInternationalResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetBackupCardsWithoutWaitingResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPresetBackupCards;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPresetBackupCardsResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPresetBackupCardsWithImagePdf;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPresetBackupCardsWithImagePdfResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.OrderBackupCards;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.OrderBackupCardsResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.PurchaseLogResponseCodeEnum;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.User;
import com.livngroup.gds.domain.BackupCard;
import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.ExceptionFactory;
import com.livngroup.gds.exception.WexAppException;
import com.livngroup.gds.repositories.BackupCardRepository;
import com.livngroup.gds.response.CallResponse;

@Service
@Qualifier("wexBackupCardService")
public class WexBackupCardService extends WexService {
	
	@Autowired
	GdsDbService gdsDbService;

	@Autowired
	private BackupCardRepository backupCardRepository;

	@Override
	protected WexEntity getWexEntity() {
		return WexEntity.BACKUP_CARD;
	}
	
	@Autowired
	private CallResponseService callResponseService;

	/*
	 * GetBackupCards
	 */
	@Transactional()
	public CallResponse getBackupCards(String bankNo, String compNo, String orderId) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetBackupCardsResponse resEncap;
			BackupCardResponse result;
			
			GetBackupCards reqObj = new GetBackupCards();
			BackupCardRequest reqData = new BackupCardRequest();
			
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setOrderID(orderId);

			reqObj.setUser(wexUserToken);
			reqObj.setRequest(reqData);
			
			resEncap = purchaseLogServiceStub.getBackupCards(reqObj);
			if(resEncap != null && resEncap.getGetBackupCardsResult() != null) {
				result = resEncap.getGetBackupCardsResult();

				BackupCardResponseCode resultCode = result.getResponseCode();
				if(BackupCardResponseCode.Success.equals(resultCode)) {
					response = callResponseService.getCallSuccessResponse(result);
				} else {
					response = callResponseService.getCallFailResponse(resultCode.getValue(), result.getDescription());
				}
			} else {
				response = callResponseService.getCallFailDefaultResponse();
			}
			
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.BACKUP_CARD);
		}
		
		// dummy code to test persistence
		// in future "getBackupCards" will have to go to local database
		// instead of calling WEX for data
		BackupCard backupCard = new BackupCard();
		backupCard.setCardNumber(new Date().toString());
		backupCard.setCvc2("123");
		backupCard.setNameLine1("name line one");
		backupCard.setCreateDate(new Date());
		Calendar expirationDate = Calendar.getInstance();
		expirationDate.add(Calendar.YEAR, 1);
		backupCard.setCurrency(Currency.getInstance("AUD"));
    	backupCard.setCreditLimit(new BigDecimal("10000.00"));
    	backupCardRepository.save(backupCard);			
        
        List<BackupCard> cardList = backupCardRepository.findAll();
        logger.debug("Card list:");
        cardList.forEach(n -> logger.debug(n.getUuid() + ":" + n.getCardNumber()));
		
		return response;
	}

	/* 
	 * GetBackupCardsInternational 
	 */
	public CallResponse getBackupCardsInternational(String bankNo, String compNo, String orderId) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetBackupCardsInternationalResponse resEncap;
			BackupCardInternationalResponse result;
			
			BackupCardRequest reqData = new BackupCardRequest();
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setOrderID(orderId);

			GetBackupCardsInternational reqObj = new GetBackupCardsInternational();
			reqObj.setUser(wexUserToken);
			reqObj.setRequest(reqData);
			
			resEncap = purchaseLogServiceStub.getBackupCardsInternational(reqObj);
			if(resEncap != null && resEncap.getGetBackupCardsInternationalResult() != null) {
				result = resEncap.getGetBackupCardsInternationalResult();

				BackupCardResponseCode resultCode = result.getResponseCode();
				if(BackupCardResponseCode.Success.equals(resultCode)) {
					response = callResponseService.getCallSuccessResponse(result);
				} else {
					response = callResponseService.getCallFailResponse(resultCode.getValue(), result.getDescription());
				}
			} else {
				response = callResponseService.getCallFailDefaultResponse();
			}
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.BACKUP_CARD);
		}
		
		return response;
	}

	/* 
	 * GetBackupCardsWithoutWating 
	 */
	public CallResponse getBackupCardsWithoutWating(String bankNo, String compNo, String orderId) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetBackupCardsWithoutWaitingResponse resEncap;
			BackupCardResponse result;
			
			BackupCardOrderRequest reqData = new BackupCardOrderRequest();
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setOrderID(orderId);

			GetBackupCardsWithoutWaiting reqObj = new GetBackupCardsWithoutWaiting();
			reqObj.setUser(wexUser);
			reqObj.setRequest(reqData);
			
			resEncap = purchaseLogServiceStub.getBackupCardsWithoutWaiting(reqObj);
			if(resEncap != null && resEncap.getGetBackupCardsWithoutWaitingResult() != null) {
				result = resEncap.getGetBackupCardsWithoutWaitingResult();

				BackupCardResponseCode resultCode = result.getResponseCode();
				if(BackupCardResponseCode.Success.equals(resultCode)) {
					response = callResponseService.getCallSuccessResponse(result);
				} else {
					response = callResponseService.getCallFailResponse(resultCode.getValue(), result.getDescription());
				}
			} else {
				response = callResponseService.getCallFailDefaultResponse();
			}
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.BACKUP_CARD);
		}
		
		return response;
	}

	/* 
	 * GetBackupCardsWithoutWaitingInternational 
	 */
	public CallResponse getBackupCardsWithoutWaitingInternational(
							String bankNo, String compNo, String orderId) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetBackupCardsWithoutWaitingInternationalResponse resEncap;
			BackupCardInternationalResponse result;
			
			BackupCardOrderRequest reqData = new BackupCardOrderRequest();
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setOrderID(orderId);

			GetBackupCardsWithoutWaitingInternational reqObj = new GetBackupCardsWithoutWaitingInternational();
			reqObj.setUser(wexUser);
			reqObj.setRequest(reqData);
			
			resEncap = purchaseLogServiceStub.getBackupCardsWithoutWaitingInternational(reqObj);
			if(resEncap != null && resEncap.getGetBackupCardsWithoutWaitingInternationalResult() != null) {
				result = resEncap.getGetBackupCardsWithoutWaitingInternationalResult();

				BackupCardResponseCode resultCode = result.getResponseCode();
				if(BackupCardResponseCode.Success.equals(resultCode)) {
					response = callResponseService.getCallSuccessResponse(result);
				} else {
					response = callResponseService.getCallFailResponse(resultCode.getValue(), result.getDescription());
				}
			} else {
				response = callResponseService.getCallFailDefaultResponse();
			}
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.BACKUP_CARD);
		}
		
		return response;
	}

	/* 
	 * GetPresetBackupCards 
	 */
	public CallResponse getPresetBackupCards(String bankNo, String compNo, String orderId) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetPresetBackupCardsResponse resEncap;
			BackupCardResponse result;
			
			BackupCardOrderRequest reqData = new BackupCardOrderRequest();
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setOrderID(orderId);

			GetPresetBackupCards reqObj = new GetPresetBackupCards();
			reqObj.setUser(wexUserToken);
			reqObj.setRequest(reqData);
			
			resEncap = purchaseLogServiceStub.getPresetBackupCards(reqObj);
			if(resEncap != null && resEncap.getGetPresetBackupCardsResult() != null) {
				result = resEncap.getGetPresetBackupCardsResult();

				BackupCardResponseCode resultCode = result.getResponseCode();
				if(BackupCardResponseCode.Success.equals(resultCode)) {
					response = callResponseService.getCallSuccessResponse(result);
				} else {
					response = callResponseService.getCallFailResponse(resultCode.getValue(), result.getDescription());
				}
			} else {
				response = callResponseService.getCallFailDefaultResponse();
			}
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.BACKUP_CARD);
		}
		
		return response;
	}

	/* 
	 * GetPresetBackupCardsWithImagePdf
	 */
	public CallResponse getPresetBackupCardsWithImagePdf(String bankNo, String compNo, String orderId) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			GetPresetBackupCardsWithImagePdfResponse resEncap;
			BackupCardResponse result;
			
			BackupCardOrderRequest reqData = new BackupCardOrderRequest();
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setOrderID(orderId);

			GetPresetBackupCardsWithImagePdf reqObj = new GetPresetBackupCardsWithImagePdf();
			reqObj.setUser(wexUserToken);
			reqObj.setRequest(reqData);
			
			resEncap = purchaseLogServiceStub.getPresetBackupCardsWithImagePdf(reqObj);
			if(resEncap != null && resEncap.getGetPresetBackupCardsWithImagePdfResult() != null) {
				result = resEncap.getGetPresetBackupCardsWithImagePdfResult();

				BackupCardResponseCode resultCode = result.getResponseCode();
				if(BackupCardResponseCode.Success.equals(resultCode)) {
					response = callResponseService.getCallSuccessResponse(result);
				} else {
					response = callResponseService.getCallFailResponse(resultCode.getValue(), result.getDescription());
				}
			} else {
				response = callResponseService.getCallFailDefaultResponse();
			}
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.PURCHASE_LOG);
		}
		
		return response;
	}

	/*
	 * OrderBackupCards
	 */
	public CallResponse orderBackupCards(String bankNo, String compNo, String orderId) throws WexAppException {
		CallResponse response = new CallResponse();
		
		try {
			OrderBackupCardsResponse resEncap;
			BackupCardOrderResponse result;
			
			BackupCardOrderRequest reqData = new BackupCardOrderRequest();
			reqData.setBankNumber(bankNo);
			reqData.setCompanyNumber(compNo);
			reqData.setOrderID(orderId);

			ArrayOfBackupCardOrderBlock orderArray = new ArrayOfBackupCardOrderBlock();
			BackupCardOrderBlock aOrder = new BackupCardOrderBlock();
			/*
			 * 
			 */
			aOrder.setBillingCurrency("");
			aOrder.setCreditLimit(new BigDecimal(5000));
			aOrder.setQuantity(150);
			BackupCardOrderBlock[] orderBlocks = {aOrder};
			orderArray.setBackupCardOrderBlock(orderBlocks);

			reqData.setOrderBlocks(orderArray);

			OrderBackupCards reqObj = new OrderBackupCards();
			reqObj.setUser(wexUserToken);
			reqObj.setRequest(reqData);
			
			resEncap = purchaseLogServiceStub.orderBackupCards(reqObj);
			if(resEncap != null && resEncap.getOrderBackupCardsResult() != null) {
				result = resEncap.getOrderBackupCardsResult();

				BackupCardResponseCode resultCode = result.getResponseCode();
				if(BackupCardResponseCode.Success.equals(resultCode)) {
					response = callResponseService.getCallSuccessResponse(result);
				} else {
					response = callResponseService.getCallFailResponse(resultCode.getValue(), result.getDescription());
				}
			} else {
				response = callResponseService.getCallFailDefaultResponse();
			}
		} catch(RemoteException exc) {
			throw ExceptionFactory.createServiceUnavailableForEntityException(exc, WexEntity.BACKUP_CARD);
		}
		
		return response;
	}

}
