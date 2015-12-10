package com.livngroup.gds.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.livngroup.gds.exception.WexException;
import com.livngroup.gds.response.CallResponse;
import com.livngroup.gds.response.GeneralResponse;
import com.livngroup.gds.service.WexPaymentService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/payment")
@Api(value="/payment")
public class PaymentInfoController extends WexController {

	@Autowired
	private WexPaymentService paymentService;
	
	@RequestMapping(value="/getSchedule", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody GeneralResponse getSchedule(@RequestParam String bankNo, 
												@RequestParam String compNo, 
												@RequestParam String uniqueId) throws WexException {
		
		CallResponse response = paymentService.getPaymentSchedule(bankNo, compNo, uniqueId);		
		
		logger.debug(response.getMessage());
		return (GeneralResponse)response;
	}

	@RequestMapping(value="/getInfoUrl", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody GeneralResponse getInformationUrl(@RequestParam String bankNo, 
												@RequestParam String compNo, 
												@RequestParam String uniqueId) throws WexException {
		
		CallResponse response = paymentService.getPaymentInformationUrl(bankNo, compNo, uniqueId);		
		
		logger.debug(response.getMessage());
		return (GeneralResponse)response;
	}

}
