package com.livngroup.gds.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.DeleteAuthorizationResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.InstantApprovalResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.InternalBackupInventoryInfoResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.ResendNotificationResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.RetrieveSecureCodeAuthPinResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.TransactionsResponse;
import com.livngroup.gds.domain.LivnBaseReq;
import com.livngroup.gds.domain.LivnInstantApprovalReq;
import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.WexAppException;
import com.livngroup.gds.response.CallResponse;
import com.livngroup.gds.response.ErrorResponse;
import com.livngroup.gds.service.WebResponseService;
import com.livngroup.gds.service.WexGeneralService;
import com.livngroup.gds.service.WexTransactionService;
import com.livngroup.gds.util.Validator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/general")
@Api(value="/general")
public class GeneralController extends WexController {

	@Autowired
	WexGeneralService generalService;

	@Autowired
	private WebResponseService responseService;
	
	@Override
	protected WexEntity getEntytyType() {
		return WexEntity.TRANSACTION;
	}
	
	/*
	 * DeleteAuthorization
	 */
	@ApiResponses(value={@ApiResponse(code=200, message="", response=DeleteAuthorizationResponse.class), 
	@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
	@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/auth/delete", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> deleteAuthorization(@RequestParam String bankNo, 
														@RequestParam String compNo, 
														@RequestParam String uniqueId) throws WexAppException {
		ResponseEntity<Object> response;
		
		if(Validator.isNumber(bankNo) && Validator.isNumber(compNo)) {
			CallResponse result = generalService.deleteAuthorization(bankNo, compNo, null, null, uniqueId, null);
			if(result.getOk()) {
				response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
			} else {
				ErrorResponse warnRes = responseService.getErrorResponse(result, WexEntity.GENERAL);
				response = new ResponseEntity<Object>(warnRes, result.getStatus());
			}
		} else {
			ErrorResponse errRes = responseService.getErrorResponseDefault("One of input values should be a number.\nPlease check again the value of input parameter(s).");
			response = new ResponseEntity<Object>(errRes, HttpStatus.NOT_ACCEPTABLE);
		}

		return response;
	}

	/*
	 * DeleteInstantApproval
	 */
	@ApiResponses(value={@ApiResponse(code=200, message="", response=InstantApprovalResponse.class), 
	@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
	@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/approval/delete", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> deleteInstantApproval(
							@RequestBody LivnInstantApprovalReq approvalReq) throws WexAppException {
		ResponseEntity<Object> response;
		
		assertNotNull("approvalReq", approvalReq);
		assertNotNull("approvalReq.bankNumber", approvalReq.getBankNumber());
		assertNotNull("approvalReq.companyNumber", approvalReq.getCompanyNumber());
		assertNotNull("approvalReq.purchaseLogUniqueID", approvalReq.getPurchaseLogUniqueID());

		CallResponse result = generalService.deleteInstantApproval(approvalReq);
		if(result.getOk()) {
			response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
		} else {
			ErrorResponse warnRes = responseService.getErrorResponse(result, WexEntity.GENERAL);
			response = new ResponseEntity<Object>(warnRes, result.getStatus());
		}

		return response;
	}

	/*
	 * InstantApproval
	 */
	@ApiResponses(value={@ApiResponse(code=200, message="", response=InstantApprovalResponse.class), 
	@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
	@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/approval/check", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> instantApproval(@RequestBody LivnInstantApprovalReq approvalReq) throws WexAppException {
		ResponseEntity<Object> response;
		
		assertNotNull("approvalReq", approvalReq);
		assertNotNull("approvalReq.bankNumber", approvalReq.getBankNumber());
		assertNotNull("approvalReq.companyNumber", approvalReq.getCompanyNumber());
		assertNotNull("approvalReq.purchaseLogUniqueID", approvalReq.getPurchaseLogUniqueID());

		CallResponse result = generalService.instantApproval(approvalReq);
		if(result.getOk()) {
			response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
		} else {
			ErrorResponse warnRes = responseService.getErrorResponse(result, WexEntity.GENERAL);
			response = new ResponseEntity<Object>(warnRes, result.getStatus());
		}

		return response;
	}

	/*
	 * GetBackupInventoryInfo
	 */
	@ApiResponses(value={@ApiResponse(code=200, message="", response=InternalBackupInventoryInfoResponse.class), 
	@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
	@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/inventory-info/get", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> getBackupInventoryInfo(@RequestParam String bankNo, 
														@RequestParam String compNo, 
														@RequestParam String orderId,
														@RequestParam int lookBackDays) throws WexAppException {
		ResponseEntity<Object> response;
		
		if(Validator.isNumber(bankNo) && Validator.isNumber(compNo)) {
			CallResponse result = generalService.getInternalBackupInventoryInfo(bankNo, compNo, orderId, lookBackDays);
			if(result.getOk()) {
				response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
			} else {
				ErrorResponse warnRes = responseService.getErrorResponse(result, WexEntity.GENERAL);
				response = new ResponseEntity<Object>(warnRes, result.getStatus());
			}
		} else {
			ErrorResponse errRes = responseService.getErrorResponseDefault("One of input values should be a number.\nPlease check again the value of input parameter(s).");
			response = new ResponseEntity<Object>(errRes, HttpStatus.NOT_ACCEPTABLE);
		}

		return response;
	}

	/*
	 * GetBackupInventoryInfo
	 */
	@ApiResponses(value={@ApiResponse(code=200, message="", response=ResendNotificationResponse.class), 
	@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
	@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/notification/resend", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> resendNotification(@RequestBody LivnBaseReq resendReq) throws WexAppException {
		ResponseEntity<Object> response;
		
		assertNotNull("resendReq", resendReq);
		assertNotNull("resendReq.bankNumber", resendReq.getBankNumber());
		assertNotNull("resendReq.companyNumber", resendReq.getCompanyNumber());
		assertNotNull("resendReq.purchaseLogUniqueID", resendReq.getPurchaseLogUniqueID());

		CallResponse result = generalService.resendNotification(resendReq);
			if(result.getOk()) {
				response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
			} else {
				ErrorResponse warnRes = responseService.getErrorResponse(result, WexEntity.GENERAL);
				response = new ResponseEntity<Object>(warnRes, result.getStatus());
			}

		return response;
	}

	/*
	 * RetrieveSecureCodeAuthPin
	 */
	@ApiResponses(value={@ApiResponse(code=200, message="", response=RetrieveSecureCodeAuthPinResponse.class), 
	@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
	@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/secure-code/retrieve", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> retrieveSecureCodeAuthPin(@RequestBody LivnBaseReq retrieveReq) throws WexAppException {
		ResponseEntity<Object> response;
		
		assertNotNull("retrieveReq", retrieveReq);
		assertNotNull("retrieveReq.bankNumber", retrieveReq.getBankNumber());
		assertNotNull("retrieveReq.companyNumber", retrieveReq.getCompanyNumber());
		assertNotNull("retrieveReq.purchaseLogUniqueID", retrieveReq.getPurchaseLogUniqueID());

		CallResponse result = generalService.retrieveSecureCodeAuthPin(retrieveReq);
			if(result.getOk()) {
				response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
			} else {
				ErrorResponse warnRes = responseService.getErrorResponse(result, WexEntity.GENERAL);
				response = new ResponseEntity<Object>(warnRes, result.getStatus());
			}

		return response;
	}

	/*
	 * RetrieveSecureCodeAuthPin
	 */
	@ApiResponses(value={@ApiResponse(code=200, message="", response=RetrieveSecureCodeAuthPinResponse.class), 
	@ApiResponse(code=400, message="WEX Error Reason", response=ErrorResponse.class),
	@ApiResponse(code=406, message="Not acceptable", response=ErrorResponse.class)})
	@RequestMapping(value="/checklog/submit", produces="application/json", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> submitCheckLog(@RequestBody LivnBaseReq retrieveReq) throws WexAppException {
		ResponseEntity<Object> response;
		
		assertNotNull("retrieveReq", retrieveReq);
		assertNotNull("retrieveReq.bankNumber", retrieveReq.getBankNumber());
		assertNotNull("retrieveReq.companyNumber", retrieveReq.getCompanyNumber());
		assertNotNull("retrieveReq.purchaseLogUniqueID", retrieveReq.getPurchaseLogUniqueID());

		CallResponse result = generalService.retrieveSecureCodeAuthPin(retrieveReq);
			if(result.getOk()) {
				response = new ResponseEntity<Object>(result.getResult(), result.getStatus());
			} else {
				ErrorResponse warnRes = responseService.getErrorResponse(result, WexEntity.GENERAL);
				response = new ResponseEntity<Object>(warnRes, result.getStatus());
			}

		return response;
	}

}
