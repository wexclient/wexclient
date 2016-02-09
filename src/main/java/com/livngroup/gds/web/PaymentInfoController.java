package com.livngroup.gds.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.DeleteAuthorizationResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPaymentInformationUrlResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.GetPaymentScheduleResponse;
import com.livngroup.gds.domain.LivnBaseReq;
import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.WexAppException;
import com.livngroup.gds.response.CallResponse;
import com.livngroup.gds.response.ErrorResponse;
import com.livngroup.gds.service.WexPaymentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/payment")
@Api(value="/payment")
public class PaymentInfoController extends WexController {

	@Autowired
	private WexPaymentService paymentService;
	
	@Override
	protected WexEntity getEntytyType() {
		return WexEntity.PAYMENT_SCHEDULE;
	}

	@ApiResponses(value={@ApiResponse(code=200, message="", response=GetPaymentScheduleResponse.class), 
	@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
	@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/getSchedule", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> getSchedule(@RequestParam String bankNo, 
														@RequestParam String compNo, 
														@RequestParam String uniqueId) throws WexAppException {
		ResponseEntity<Object> response;		
		
		assertNotNull("bankNo", bankNo);
		assertNotNull("compNo", compNo);
		assertNotNull("uniqueId", uniqueId);

		LivnBaseReq baseReq = new LivnBaseReq(bankNo, compNo, uniqueId);

		CallResponse result = paymentService.getPaymentSchedule(baseReq);		
		if(result.getOk()) {
			response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
		} else {
			ErrorResponse warnRes = getErrorResponse(result, WexEntity.TRANSACTION);
			response = new ResponseEntity<Object>(warnRes, result.getStatus());
		}
		
		return response;
	}

	@ApiResponses(value={@ApiResponse(code=200, message="", response=GetPaymentInformationUrlResponse.class), 
	@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
	@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/getInfoUrl", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> getInformationUrl(@RequestParam String bankNo, 
																@RequestParam String compNo, 
																@RequestParam String uniqueId) throws WexAppException {
		ResponseEntity<Object> response;		
		
		assertNotNull("bankNo", bankNo);
		assertNotNull("compNo", compNo);
		assertNotNull("uniqueId", uniqueId);

		LivnBaseReq baseReq = new LivnBaseReq(bankNo, compNo, uniqueId);
		
		CallResponse result = paymentService.getPaymentInformationUrl(baseReq);		
		if(result.getOk()) {
			response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
		} else {
			ErrorResponse warnRes = getErrorResponse(result, WexEntity.TRANSACTION);
			response = new ResponseEntity<Object>(warnRes, result.getStatus());
		}
		
		return response;
	}

}
