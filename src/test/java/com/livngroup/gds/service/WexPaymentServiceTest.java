package com.livngroup.gds.service;

import static org.junit.Assert.*;

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
import com.livngroup.gds.service.WexPaymentService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LivnDemoApplication.class)
@WebIntegrationTest("server.port=8080")
public class WexPaymentServiceTest {

	@Mock
    private WexPaymentService wexPaymentServiceMock;

    @Before
    public void doSetup() {
    	MockitoAnnotations.initMocks(this);    	
    }

    @Test
	public void testPaymentService() {
    	try {
    		CallResponse result = wexPaymentServiceMock.getPaymentInformationUrl("1234", "1234567", "12345");
	        assertNull(result.getResult());
    	} catch(Exception e) {}
	}

}
