package com.livngroup.gds.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.CancelPurchaseLogResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.CreatePurchaseLogResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.QueryPurchaseLogsResponse;
import com.livngroup.gds.domain.LivnPurchaseLog;
import com.livngroup.gds.domain.LivnPurchaseLogUpdateReq;
import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.WexAppException;
import com.livngroup.gds.response.CallResponse;
import com.livngroup.gds.response.ErrorResponse;
import com.livngroup.gds.response.GeneralResponse;
import com.livngroup.gds.service.WexPurchaseLogService;
import com.livngroup.gds.service.WebResponseService;
import com.livngroup.gds.util.Validator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/purchase")
@Api(value="/purchase")
public class PurchaseLogController extends WexController {
	
	@Autowired
	WexPurchaseLogService wexService;

	@Autowired
	private WebResponseService responseService;
	
	@Override
	protected WexEntity getEntytyType() {
		return WexEntity.PURCHASE_LOG;
	}
	
	/* CreatePurchaseLog */
	@ApiResponses(value={@ApiResponse(code=200, message="", response=CreatePurchaseLogResponse.class), 
			@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
			@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/createLog", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody GeneralResponse createPurchaseLog(@RequestBody LivnPurchaseLog purchaseLog) throws WexAppException {
		
		assertNotNull("purchaseLog", purchaseLog);
		assertNotEmpty("purchaseLog.invoiceNumber", purchaseLog.getInvoiceNumber());
		assertNotEmpty("purchaseLog.leadPassengerName", purchaseLog.getLeadPassengerName());
		assertNotEmpty("purchaseLog.reservationId", purchaseLog.getReservationId());
		assertPositiveNumber("purchaseLog.amount", purchaseLog.getAmount());
		
		CallResponse response = wexService.createPurchaseLog(purchaseLog);

		logger.debug(response.getMessage());
		return (GeneralResponse) response;
	}
	
	/* CancelPurchaseLog */
	@ApiResponses(value={@ApiResponse(code=200, message="", response=CancelPurchaseLogResponse.class), 
			@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
			@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/cancelLog/{uniqueId}", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody GeneralResponse cancelPurchaseLog(@PathVariable("uniqueId") String uniqueId) throws WexAppException {
		
		assertNotEmpty("uniqueId", uniqueId);

		CallResponse response = wexService.cancelPurchaseLog(uniqueId);

		logger.debug(response.getMessage());
		return (GeneralResponse) response;
	}
	
	/*
	 * GetPurchaseLogHistory
	 */
	@ApiResponses(value={@ApiResponse(code=200, message="", response=GetPurchaseLogHistoryResponse.class), 
			@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
			@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/historyLog", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> getHistoryLog(@RequestParam String uniqueId) throws WexAppException {

		assertNotEmpty("uniqueId", uniqueId);

		ResponseEntity<Object> response;
		CallResponse result = wexService.getPurchaseLogHistory(uniqueId);
		if(result.getOk()) {
			response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
		} else {
			ErrorResponse warnRes = responseService.getErrorResponse(result, WexEntity.PURCHASE_LOG);
			response = new ResponseEntity<Object>(warnRes, result.getStatus());
		}
		
		return response;
	}
	
	@ApiResponses(value={@ApiResponse(code=200, message="", response=GetPurchaseLogHistoryResponse.class), 
			@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
			@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/retrieveLog/{purchaseLogId}", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> retrieveLog(@PathVariable("purchaseLogId") String purchaseLogId) throws WexAppException {

		assertNotEmpty("purchaseLogId", purchaseLogId);

		ResponseEntity<Object> response;
		CallResponse result = wexService.retrievePurchaseLog(purchaseLogId);
		if(result.getOk()) {
			response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
		} else {
			ErrorResponse warnRes = responseService.getErrorResponse(result, WexEntity.PURCHASE_LOG);
			response = new ResponseEntity<Object>(warnRes, result.getStatus());
		}
		
		return response;
	}
	
	/*
	 * QueryPurchaseLogs
	 */
	@ApiResponses(value={@ApiResponse(code=200, message="", response=QueryPurchaseLogsResponse.class), 
			@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
			@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/queryLog", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> queryPurchaseLog(@RequestParam String bankNo, 
													@RequestParam String compNo, 
													@RequestParam(value="status", required=false) String status,
													@RequestParam(value="fromAmount", required=false) String fromAmount,
													@RequestParam(value="toAmount", required=false) String toAmount,
													@RequestParam(value="currency", required=false) String currency,
													@RequestParam(value="fromDate", required=false) String fromDate,
													@RequestParam(value="toDate", required=false) String toDate) throws WexAppException {
		ResponseEntity<Object> response;
		if(Validator.isNumber(bankNo) && Validator.isNumber(compNo)) {
			CallResponse result = wexService.queryPurchaseLogs(bankNo, compNo, status, fromAmount, toAmount, currency, fromDate, toDate);
			if(result.getOk()) {
				response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
			} else {
				ErrorResponse warnRes = new ErrorResponse(
						result.getStatus(), 
						result.getStatus().toString(),
						WexEntity.PURCHASE_LOG,
						result.getMessage(), 
						ErrorResponse.URL_DEFAULT, 
						null, 
						result.getResult());
				response = new ResponseEntity<Object>(warnRes, result.getStatus());
			}
		} else {
			ErrorResponse errRes = new ErrorResponse(
					HttpStatus.NOT_ACCEPTABLE, 
					HttpStatus.NOT_ACCEPTABLE.toString(),
					WexEntity.PURCHASE_LOG,
					"One of input values should be a number.\nPlease check again the value of input parameter(s).", 
					ErrorResponse.URL_DEFAULT, 
					null, 
					null);
			response = new ResponseEntity<Object>(errRes, HttpStatus.NOT_ACCEPTABLE);
		}
		
		return response;
	}

	/* CreatePurchaseLog */
	@ApiResponses(value={@ApiResponse(code=200, message="", response=CreatePurchaseLogResponse.class), 
			@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
			@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/update", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody GeneralResponse updatePurchaseLog(@RequestBody LivnPurchaseLogUpdateReq updateLog) throws WexAppException {
		
		assertNotNull("updateLog", updateLog);
		assertNotEmpty("updateLog.invoiceNumber", updateLog.getInvoiceNumber());
		assertNotEmpty("updateLog.leadPassengerName", updateLog.getLeadPassengerName());
		assertNotEmpty("updateLog.reservationId", updateLog.getReservationId());
		assertPositiveNumber("updateLog.amount", updateLog.getAmount());
		
		CallResponse response = wexService.updatePurchaseLog(updateLog);

		logger.debug(response.getMessage());
		return (GeneralResponse) response;
	}

}
