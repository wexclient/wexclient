package com.livngroup.gds.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.livngroup.gds.domain.LivnBaseReq;
import com.livngroup.gds.domain.LivnTransactionReq;
import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.WexAppException;
import com.livngroup.gds.response.CallResponse;
import com.livngroup.gds.response.ErrorResponse;
import com.livngroup.gds.response.GeneralResponse;
import com.livngroup.gds.service.WexPaymentService;
import com.livngroup.gds.service.WebResponseService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/payment")
@Api(value="/payment")
public class PaymentInfoController extends WexController {

	@Autowired
	private WexPaymentService paymentService;
	
	@Autowired
	private WebResponseService responseService;

	@Override
	protected WexEntity getEntytyType() {
		return WexEntity.PAYMENT_SCHEDULE;
	}

	@RequestMapping(value="/getSchedule", produces="application/json", method=RequestMethod.POST)
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
			ErrorResponse warnRes = responseService.getErrorResponse(result, WexEntity.TRANSACTION);
			response = new ResponseEntity<Object>(warnRes, result.getStatus());
		}
		
		return response;
	}

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
			ErrorResponse warnRes = responseService.getErrorResponse(result, WexEntity.TRANSACTION);
			response = new ResponseEntity<Object>(warnRes, result.getStatus());
		}
		
		return response;
	}

}
