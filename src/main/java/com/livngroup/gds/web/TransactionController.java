package com.livngroup.gds.web;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.DisputeTransactionResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.DisputedTransactionsResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetRecentAccountActivityInternationalResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetRecentAccountActivityResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetTransactionsInternationalResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetTransactionsResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.TransactionsResponse;
import com.livngroup.gds.domain.LivnTransactionReq;
import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.WexAppException;
import com.livngroup.gds.response.CallResponse;
import com.livngroup.gds.response.ErrorResponse;
import com.livngroup.gds.service.WebResponseService;
import com.livngroup.gds.service.WexPurchaseLogService;
import com.livngroup.gds.service.WexTransactionService;
import com.livngroup.gds.util.GdsDateUtil;
import com.livngroup.gds.util.Validator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/transact")
@Api(value="/transact")
public class TransactionController extends WexController {

	@Autowired
	WexTransactionService transactionService;

	@Autowired
	private WebResponseService responseService;
	
	@Override
	protected WexEntity getEntytyType() {
		return WexEntity.TRANSACTION;
	}

	/*
	 * GetTransactions
	 */
	@ApiResponses(value={@ApiResponse(code=200, message="", response=GetTransactionsResponse.class), 
	@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
	@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/get", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> getTransactions(
							@RequestBody LivnTransactionReq transactReq) throws WexAppException {
		ResponseEntity<Object> response;		
		
		assertNotNull("transactReq", transactReq);
		assertNotNull("transactionReq.bankNumber", transactReq.getBankNumber());
		assertNotNull("transactionReq.companyNumber", transactReq.getCompanyNumber());
		assertNotNull("transactionReq.purchaseLogUniqueID", transactReq.getPurchaseLogUniqueID());
		
		CallResponse result = transactionService.getTransactions(transactReq);
		if(result.getOk()) {
			response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
		} else {
			ErrorResponse warnRes = responseService.getErrorResponse(result, WexEntity.TRANSACTION);
			response = new ResponseEntity<Object>(warnRes, result.getStatus());
		}

		return response;
	}

	/*
	 * GetTransactionsInternational
	 */
	@ApiResponses(value={@ApiResponse(code=200, message="", response=GetTransactionsInternationalResponse.class), 
	@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
	@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/international/get", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> getTransactionsInternational(
							@RequestBody LivnTransactionReq transactReq) throws WexAppException {
		ResponseEntity<Object> response;		
		
		assertNotNull("transactReq", transactReq);
		assertNotNull("transactionReq.bankNumber", transactReq.getBankNumber());
		assertNotNull("transactionReq.companyNumber", transactReq.getCompanyNumber());
		assertNotNull("transactionReq.purchaseLogUniqueID", transactReq.getPurchaseLogUniqueID());

		CallResponse result = transactionService.getTransactionsInternational(transactReq);
		if(result.getOk()) {
			response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
		} else {
			ErrorResponse warnRes = responseService.getErrorResponse(result, WexEntity.TRANSACTION);
			response = new ResponseEntity<Object>(warnRes, result.getStatus());
		}

		return response;
	}

	/*
	 * DisputeTransaction
	 */
	@ApiResponses(value={@ApiResponse(code=200, message="", response=DisputeTransactionResponse.class), 
	@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
	@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/dispute/check", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> disputeTransaction(@RequestParam String bankNo, 
														@RequestParam String compNo, 
														@RequestParam String uniqueId) throws WexAppException {
		ResponseEntity<Object> response;		
		
		if(Validator.isNumber(bankNo) && Validator.isNumber(compNo)) {
			CallResponse result = transactionService.disputeTransaction(bankNo, compNo, uniqueId);
			if(result.getOk()) {
				response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
			} else {
				ErrorResponse warnRes = responseService.getErrorResponse(result, WexEntity.TRANSACTION);
				response = new ResponseEntity<Object>(warnRes, result.getStatus());
			}
		} else {
			ErrorResponse errRes = responseService.getErrorResponseDefault("One of input values should be a number.\nPlease check again the value of input parameter(s).");
			response = new ResponseEntity<Object>(errRes, HttpStatus.NOT_ACCEPTABLE);
		}

		return response;
	}

	/*
	 * GetDisputedTransactions
	 */
	@ApiResponses(value={@ApiResponse(code=200, message="", response=DisputedTransactionsResponse.class), 
	@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
	@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/dispute/get", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> getDisputedTransactions(@RequestParam String bankNo, 
														@RequestParam String compNo, 
														@RequestParam String uniqueId, 
														@RequestParam String fromDate) throws WexAppException {
		ResponseEntity<Object> response;		
		
		if(Validator.isNumber(bankNo) && Validator.isNumber(compNo)) {
			Calendar cDate = GdsDateUtil.getCalendarFromString(fromDate);
			CallResponse result = transactionService.getDisputedTransactions(bankNo, compNo, uniqueId, cDate);
			if(result.getOk()) {
				response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
			} else {
				ErrorResponse warnRes = responseService.getErrorResponse(result, WexEntity.TRANSACTION);
				response = new ResponseEntity<Object>(warnRes, result.getStatus());
			}
		} else {
			ErrorResponse errRes = responseService.getErrorResponseDefault("One of input values should be a number.\nPlease check again the value of input parameter(s).");
			response = new ResponseEntity<Object>(errRes, HttpStatus.NOT_ACCEPTABLE);
		}

		return response;
	}

	/*
	 * GetRecentAccountActivity
	 */
	@ApiResponses(value={@ApiResponse(code=200, message="", response=GetRecentAccountActivityResponse.class), 
	@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
	@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/recent/get", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> getRecentActivity(
							@RequestBody LivnTransactionReq transactReq) throws WexAppException {
		ResponseEntity<Object> response;		
		
		assertNotNull("transactReq", transactReq);
		assertNotNull("transactionReq.bankNumber", transactReq.getBankNumber());
		assertNotNull("transactionReq.companyNumber", transactReq.getCompanyNumber());
		assertNotNull("transactionReq.purchaseLogUniqueID", transactReq.getPurchaseLogUniqueID());

		CallResponse result = transactionService.getRecentAccountActivity(transactReq);
		if(result.getOk()) {
			response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
		} else {
			ErrorResponse warnRes = responseService.getErrorResponse(result, WexEntity.TRANSACTION);
			response = new ResponseEntity<Object>(warnRes, result.getStatus());
		}

		return response;
	}

	/*
	 * GetRecentAccountActivityInternational
	 */
	@ApiResponses(value={@ApiResponse(code=200, message="", response=GetRecentAccountActivityInternationalResponse.class), 
	@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
	@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/recent/international/get", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> getRecentActivityInternational(
								@RequestBody LivnTransactionReq transactReq) throws WexAppException {
		ResponseEntity<Object> response;		
		
		assertNotNull("transactReq", transactReq);
		assertNotNull("transactionReq.bankNumber", transactReq.getBankNumber());
		assertNotNull("transactionReq.companyNumber", transactReq.getCompanyNumber());
		assertNotNull("transactionReq.purchaseLogUniqueID", transactReq.getPurchaseLogUniqueID());

		CallResponse result = transactionService.getRecentAccountActivityInternational(transactReq);
		if(result.getOk()) {
			response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
		} else {
			ErrorResponse warnRes = responseService.getErrorResponse(result, WexEntity.TRANSACTION);
			response = new ResponseEntity<Object>(warnRes, result.getStatus());
		}

		return response;
	}

}
