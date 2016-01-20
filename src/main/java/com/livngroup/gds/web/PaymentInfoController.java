package com.livngroup.gds.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.WexAppException;
import com.livngroup.gds.response.CallResponse;
import com.livngroup.gds.response.GeneralResponse;
import com.livngroup.gds.service.WexPaymentService;
import com.livngroup.gds.service.WexResponseService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/payment")
@Api(value="/payment")
public class PaymentInfoController extends WexController {

	@Override
	protected WexEntity getEntytyType() {
		return WexEntity.PAYMENT_SCHEDULE;
	}

	@Autowired
	private WexPaymentService paymentService;
	
	@RequestMapping(value="/getSchedule", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody GeneralResponse getSchedule(@RequestParam String bankNo, 
												@RequestParam String compNo, 
												@RequestParam String uniqueId) throws WexAppException {
		assertNumber("bankNo", bankNo);
		assertNumber("compNo", compNo);
		
		CallResponse response = paymentService.getPaymentSchedule(bankNo, compNo, uniqueId);		
		
		logger.debug(response.getMessage());
		return (GeneralResponse)response;
	}

	@RequestMapping(value="/getInfoUrl", produces="application/json", method=RequestMethod.GET)
	public @ResponseBody GeneralResponse getInformationUrl(@RequestParam String bankNo, 
												@RequestParam String compNo, 
												@RequestParam String uniqueId) throws WexAppException {
		assertNumber("bankNo", bankNo);
		assertNumber("compNo", compNo);
		
		CallResponse response = paymentService.getPaymentInformationUrl(bankNo, compNo, uniqueId);		
		
		logger.debug(response.getMessage());
		return (GeneralResponse)response;
	}

}
