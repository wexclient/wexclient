package com.livngroup.gds.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.livngroup.gds.LivnDemoApplication;
import com.livngroup.gds.response.CallResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LivnDemoApplication.class)
@WebIntegrationTest("server.port=8080")
public class WexPurchaseLogServiceTest {

	@Mock
    private WexPurchaseLogService wexPurchaseLogServiceMock;

    @Before
    public void doSetup() {
    	MockitoAnnotations.initMocks(this);    	
    }

	@Test
	public void testPurchaseLogService() throws Exception {
    	when(wexPurchaseLogServiceMock.queryPurchaseLog("1234", "1234567", "COMMIT")).thenReturn(new CallResponse());

//    	verify(wexPurchaseLogServiceMock).queryPurchaseLog("1234", "1234567", "COMMIT");
    	assertFalse(wexPurchaseLogServiceMock.queryPurchaseLog("1234", "1234567", "COMMIT").getOk());
	}

	@Test
	public void testPurchaseLogServiceWithCredential() throws Exception {
        when(wexPurchaseLogServiceMock.queryPurchaseLog("1234", "1234567", "COMMIT")).thenReturn(new CallResponse(true, "Success", HttpStatus.OK));

        assertTrue(wexPurchaseLogServiceMock.queryPurchaseLog("1234", "1234567", "COMMIT").getOk());
        assertEquals(wexPurchaseLogServiceMock.queryPurchaseLog("1234", "1234567", "COMMIT").getStatus(), HttpStatus.OK);
	}
}
