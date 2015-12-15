package com.livngroup.gds.web;

import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.QueryPurchaseLogsResponse;
import com.livngroup.gds.exception.WexException;
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
	
	@ApiResponses(@ApiResponse(code=402, message="Input amount is not number", response=CallResponse.class))
	@RequestMapping(value="/createLog", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody GeneralResponse createPurchaseLog(@RequestParam String bankNo, 
														@RequestParam String compNo, 
														@RequestParam String amount) throws WexException {
		CallResponse response = wexService.createPurchaseLog(bankNo, compNo, amount);

		logger.debug(response.getMessage());
		return (GeneralResponse)response;
	}
	
	@ApiResponses(value={@ApiResponse(code=200, message="", response=QueryPurchaseLogsResponse.class), 
			@ApiResponse(code=400, message="WEX Response Reason", response=ErrorResponse.class),
			@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/cancelLog", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody GeneralResponse cancelPurchaseLog(@RequestParam String bankNo, 
														@RequestParam String compNo, 
														@RequestParam String uniqueId) throws WexException {
		CallResponse response = wexService.cancelPurchaseLog(bankNo, compNo, uniqueId);

		logger.debug(response.getMessage());
		return (GeneralResponse)response;
	}
	
	@ApiResponses(value={@ApiResponse(code=200, message="", response=QueryPurchaseLogsResponse.class), 
			@ApiResponse(code=400, message="WEX Response Reason", response=ErrorResponse.class),
			@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/historyLog", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody GeneralResponse getHistoryLog(@RequestParam String bankNo, 
														@RequestParam String compNo, 
														@RequestParam String uniqueId) throws WexException {
		CallResponse response = wexService.getPurchaseLogHistory(bankNo, compNo, uniqueId);

		logger.debug(response.getMessage());
		return (GeneralResponse)response;
	}
	
	@ApiResponses(value={@ApiResponse(code=200, message="", response=QueryPurchaseLogsResponse.class), 
			@ApiResponse(code=400, message="WEX Response Reason", response=ErrorResponse.class),
			@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/queryLog", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> queryPurchaseLog(@RequestParam String bankNo, 
															@RequestParam String compNo, 
															@RequestParam String uniqueId) throws WexException {
		ResponseEntity<?> response;
		if(Validator.isNumber(bankNo) && Validator.isNumber(compNo)) {
			CallResponse result = wexService.queryPurchaseLog(bankNo, compNo, uniqueId);
			if(result.getOk()) {
				response = new ResponseEntity<>(result.getResult(), result.getStatus());
			} else {
				ErrorResponse warnRes = new ErrorResponse(false, result.getMessage());
				response = new ResponseEntity<>(warnRes, result.getStatus());
			}
		} else {
			ErrorResponse errRes = new ErrorResponse(false, "One of input values should be a number.\nPlease check again the value of input parameter(s).");
			response = new ResponseEntity<>(errRes, HttpStatus.NOT_ACCEPTABLE);
		}
		
		return response;
	}

}
