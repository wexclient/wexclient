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
import com.livngroup.gds.domain.LivnBaseReq;
import com.livngroup.gds.domain.LivnPurchaseLog;
import com.livngroup.gds.domain.LivnTransactionReq;
import com.livngroup.gds.domain.LivnDisputeTransactionReq;
import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.WexAppException;
import com.livngroup.gds.response.CallResponse;
import com.livngroup.gds.response.ErrorResponse;
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
													@RequestParam String bankNo, 
													@RequestParam String compNo, 
													@RequestParam String uniqueId) throws WexAppException {
		ResponseEntity<Object> response;		
		
		assertNotNull("bankNo", bankNo);
		assertNotNull("compNo", compNo);
		assertNotNull("uniqueId", uniqueId);

		LivnTransactionReq transactReq = new LivnTransactionReq(bankNo, compNo, uniqueId);
		
		CallResponse result = transactionService.getTransactions(transactReq);
		if(result.getOk()) {
			response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
		} else {
			ErrorResponse warnRes = getErrorResponse(result, WexEntity.TRANSACTION);
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
													@RequestParam String bankNo, 
													@RequestParam String compNo, 
													@RequestParam String uniqueId) throws WexAppException {
		ResponseEntity<Object> response;		
		
		assertNotNull("bankNo", bankNo);
		assertNotNull("compNo", compNo);
		assertNotNull("uniqueId", uniqueId);

		LivnTransactionReq transactReq = new LivnTransactionReq(bankNo, compNo, uniqueId);

		CallResponse result = transactionService.getTransactionsInternational(transactReq);
		if(result.getOk()) {
			response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
		} else {
			ErrorResponse warnRes = getErrorResponse(result, WexEntity.TRANSACTION);
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
	@RequestMapping(value="/dispute/create", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> disputeTransaction(@RequestBody LivnDisputeTransactionReq disputeReq) throws WexAppException {
		ResponseEntity<Object> response;		
		
		assertNotNull("disputeReq", disputeReq);
		assertNotEmpty("disputeReq.getBankNumber", disputeReq.getBankNumber());
		assertNotEmpty("disputeReq.getCompanyNumber", disputeReq.getCompanyNumber());
		assertNotEmpty("disputeReq.getPurchaseLogUniqueID", disputeReq.getPurchaseLogUniqueID());
		assertPositiveNumber("disputeReq.getDisputeReason", disputeReq.getDisputeReason());
		assertPositiveNumber("disputeReq.getDisputeAmount", disputeReq.getDisputeAmount());

		CallResponse result = transactionService.disputeTransaction(disputeReq);
		if(result.getOk()) {
			response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
		} else {
			ErrorResponse warnRes = getErrorResponse(result, WexEntity.TRANSACTION);
			response = new ResponseEntity<Object>(warnRes, result.getStatus());
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
													@RequestParam(value="openOnly", required=false) String openOnly,
													@RequestParam(value="fromDate", required=false) String fromDate,
													@RequestParam(value="toDate", required=false) String toDate) throws WexAppException {
		ResponseEntity<Object> response;		
		
		assertNotNull("bankNo", bankNo);
		assertNotNull("compNo", compNo);
		assertNotNull("uniqueId", uniqueId);
		if(fromDate != null) assertDateFormat("fromDate", fromDate);
		if(toDate != null) assertDateFormat("toDate", toDate);

		CallResponse result = transactionService.getDisputedTransactions(bankNo, compNo, uniqueId, openOnly, fromDate, toDate);
		if(result.getOk()) {
			response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
		} else {
			ErrorResponse warnRes = getErrorResponse(result, WexEntity.TRANSACTION);
			response = new ResponseEntity<Object>(warnRes, result.getStatus());
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
														@RequestParam String bankNo, 
														@RequestParam String compNo, 
														@RequestParam String uniqueId) throws WexAppException {
		ResponseEntity<Object> response;		
		
		
		assertNotNull("bankNo", bankNo);
		assertNotNull("compNo", compNo);
		assertNotNull("uniqueId", uniqueId);

		LivnTransactionReq transactReq = new LivnTransactionReq(bankNo, compNo, uniqueId);

		CallResponse result = transactionService.getRecentAccountActivity(transactReq);
		if(result.getOk()) {
			response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
		} else {
			ErrorResponse warnRes = getErrorResponse(result, WexEntity.TRANSACTION);
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
													@RequestParam String bankNo, 
													@RequestParam String compNo, 
													@RequestParam String uniqueId) throws WexAppException {
		ResponseEntity<Object> response;		
		
		assertNotNull("bankNo", bankNo);
		assertNotNull("compNo", compNo);
		assertNotNull("uniqueId", uniqueId);

		LivnTransactionReq transactReq = new LivnTransactionReq(bankNo, compNo, uniqueId);

		CallResponse result = transactionService.getRecentAccountActivityInternational(transactReq);
		if(result.getOk()) {
			response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
		} else {
			ErrorResponse warnRes = getErrorResponse(result, WexEntity.TRANSACTION);
			response = new ResponseEntity<Object>(warnRes, result.getStatus());
		}

		return response;
	}

}
