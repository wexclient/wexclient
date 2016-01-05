package com.livngroup.gds.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.CancelPurchaseLogResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.CreatePurchaseLogResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPurchaseLogHistoryResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.QueryPurchaseLogsResponse;
import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.WexAppException;
import com.livngroup.gds.response.CallResponse;
import com.livngroup.gds.response.ErrorResponse;
import com.livngroup.gds.response.GeneralResponse;
import com.livngroup.gds.service.WexPurchaseLogService;
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

	@Override
	protected WexEntity getEntytyType() {
		return WexEntity.PURCHASE_LOG;
	}
	
	@ApiResponses(value={@ApiResponse(code=200, message="", response=CreatePurchaseLogResponse.class), 
			@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
			@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/createLog", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody GeneralResponse createPurchaseLog(@RequestParam String bankNo, 
														@RequestParam String compNo, 
														@RequestParam String amount) throws WexAppException {
		assertNumber("bankNo", bankNo);
		assertNumber("compNo", compNo);
		
		CallResponse response = wexService.createPurchaseLog(bankNo, compNo, amount);

		logger.debug(response.getMessage());
		return (GeneralResponse) response;
	}
	
	@ApiResponses(value={@ApiResponse(code=200, message="", response=CancelPurchaseLogResponse.class), 
			@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
			@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/cancelLog", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody GeneralResponse cancelPurchaseLog(@RequestParam String bankNo, 
														@RequestParam String compNo, 
														@RequestParam String uniqueId) throws WexAppException {
		assertNumber("bankNo", bankNo);
		assertNumber("compNo", compNo);

		CallResponse response = wexService.cancelPurchaseLog(bankNo, compNo, uniqueId);

		logger.debug(response.getMessage());
		return (GeneralResponse)response;
	}
	
	@ApiResponses(value={@ApiResponse(code=200, message="", response=GetPurchaseLogHistoryResponse.class), 
			@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
			@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/historyLog", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody GeneralResponse getHistoryLog(@RequestParam String bankNo, 
														@RequestParam String compNo, 
														@RequestParam String uniqueId) throws WexAppException {
		assertNumber("bankNo", bankNo);
		assertNumber("compNo", compNo);

		CallResponse response = wexService.getPurchaseLogHistory(bankNo, compNo, uniqueId);

		logger.debug(response.getMessage());
		return (GeneralResponse)response;
	}
	
	@ApiResponses(value={@ApiResponse(code=200, message="", response=QueryPurchaseLogsResponse.class), 
			@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
			@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/queryLog", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> queryPurchaseLog(@RequestParam String bankNo, 
															@RequestParam String compNo, 
															@RequestParam String uniqueId) throws WexAppException {
		ResponseEntity<Object> response;
		if(Validator.isNumber(bankNo) && Validator.isNumber(compNo)) {
			CallResponse result = wexService.queryPurchaseLogs(bankNo, compNo, uniqueId);
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

}
