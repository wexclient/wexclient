package com.livngroup.gds.service;

import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
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
	public void testPaymentService() throws Exception {
    	CallResponse result = wexPurchaseLogServiceMock.queryPurchaseLog("1234", "1234567", "COMMIT");
        assertNull(result.getResult());
	}

}
