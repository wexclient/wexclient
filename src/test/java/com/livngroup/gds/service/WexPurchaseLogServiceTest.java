package com.livngroup.gds.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.ArrayOfString;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.PurchaseLogResponseCodeEnum;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.QueryPurchaseLogsResponse;
import com.aocsolutions.encompasswebservices.PurchaseLogServiceStub.QueryPurchaseLogsResponseE;
import com.livngroup.gds.LivnDemoApplication;
import com.livngroup.gds.domain.WexUser;
import com.livngroup.gds.response.CallResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LivnDemoApplication.class)
@WebIntegrationTest("server.port=8080")
public class WexPurchaseLogServiceTest {

	private final String BANK_NO = "bankNo";
	private final String COMP_NO = "compNo";
	private final String LOG_STATUS = "Open";
	private final String AMOUNT = "100";
	private final String AMOUNT2 = "1000";
	private final String CURRENCY = "AUD";
	private final String CREATE_DATE = "2016-01-01";
	private final String CREATE_DATE2 = "2016-01-21";
	
	@Mock(name = "wexUser")
	protected WexUser wexUser;
	
	@Mock(name = "purchaseLogServiceStub")
	private PurchaseLogServiceStub purchaseLogServiceStubMock;

	@InjectMocks
    private WexPurchaseLogService wexPurchaseLogService;
	
    @Before
    public void doSetup() throws Exception {
    	MockitoAnnotations.initMocks(this);   
    }

	@Test
	public void testQueryPurchaseLogsSuccess() throws Exception {
		
    	when(purchaseLogServiceStubMock.queryPurchaseLogs(any())).thenReturn(createResponseObject(PurchaseLogResponseCodeEnum.Success));
		
		CallResponse callResponce = wexPurchaseLogService.queryPurchaseLogs(BANK_NO, COMP_NO, LOG_STATUS, AMOUNT, AMOUNT2, CURRENCY, CREATE_DATE, CREATE_DATE2);
		
    	verify(purchaseLogServiceStubMock, times(1)).queryPurchaseLogs(any());
		verifyNoMoreInteractions(purchaseLogServiceStubMock);
    	
		assertTrue(callResponce.getOk());
		assertEquals(HttpStatus.OK, callResponce.getStatus());
		
	}

	@Test
	public void testQueryPurchaseLogsFailed() throws Exception {
		
    	when(purchaseLogServiceStubMock.queryPurchaseLogs(any())).thenReturn(createResponseObject(PurchaseLogResponseCodeEnum.Failed));
		
		CallResponse callResponce = wexPurchaseLogService.queryPurchaseLogs(BANK_NO, COMP_NO, LOG_STATUS, AMOUNT, AMOUNT2, CURRENCY, CREATE_DATE, CREATE_DATE2);
		
    	verify(purchaseLogServiceStubMock, times(1)).queryPurchaseLogs(any());
		verifyNoMoreInteractions(purchaseLogServiceStubMock);
    	
		assertFalse(callResponce.getOk());
		assertEquals(HttpStatus.BAD_REQUEST, callResponce.getStatus());
		
	}

	@Test
	public void testQueryPurchaseLogsServiceUnavailable() throws Exception {
		
    	when(purchaseLogServiceStubMock.queryPurchaseLogs(any())).thenReturn(null);
		
		CallResponse callResponce = wexPurchaseLogService.queryPurchaseLogs(BANK_NO, COMP_NO, LOG_STATUS, AMOUNT, AMOUNT2, CURRENCY, CREATE_DATE, CREATE_DATE2);
		
    	verify(purchaseLogServiceStubMock, times(1)).queryPurchaseLogs(any());
		verifyNoMoreInteractions(purchaseLogServiceStubMock);
    	
		assertFalse(callResponce.getOk());
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, callResponce.getStatus());
		
	}

	@Ignore // AOP does not work with Mockito
	@Test
	public void testQueryPurchaseLogsServiceInvalidCredentials() throws Exception {
		
    	when(purchaseLogServiceStubMock.queryPurchaseLogs(any())).thenReturn(createResponseObject(PurchaseLogResponseCodeEnum.InvalidUserCredentials));
		
		CallResponse callResponce = wexPurchaseLogService.queryPurchaseLogs(BANK_NO, COMP_NO, LOG_STATUS, AMOUNT, AMOUNT2, CURRENCY, CREATE_DATE, CREATE_DATE2);
		
    	verify(purchaseLogServiceStubMock, times(1)).queryPurchaseLogs(any());
		verifyNoMoreInteractions(purchaseLogServiceStubMock);
    	
		assertFalse(callResponce.getOk());
		assertEquals(HttpStatus.UNAUTHORIZED, callResponce.getStatus());
		
	}

	private QueryPurchaseLogsResponseE createResponseObject(PurchaseLogResponseCodeEnum resultCode) {
		QueryPurchaseLogsResponseE response = new QueryPurchaseLogsResponseE();
		QueryPurchaseLogsResponse purchaseLogsResponseResponse = new QueryPurchaseLogsResponse();
		purchaseLogsResponseResponse.setPurchaseLogUniqueIds(new ArrayOfString());
		response.setQueryPurchaseLogsResult(purchaseLogsResponseResponse);
		response.getQueryPurchaseLogsResult().setResponseCode(resultCode);
		return response;
	}

}
