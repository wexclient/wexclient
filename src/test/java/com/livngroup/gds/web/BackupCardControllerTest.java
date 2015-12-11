package com.livngroup.gds.web;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.livngroup.gds.LivnDemoApplication;
import com.livngroup.gds.response.CallResponse;
import com.livngroup.gds.service.WexBackupCardService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LivnDemoApplication.class)
@WebAppConfiguration
public class BackupCardControllerTest {
	
	@Autowired 
	WebApplicationContext webApplicationContext; 

	@Mock
	private WexBackupCardService backupCardService;

	@Autowired
	@InjectMocks
	private BackupCardController backupCardController;
	
    private MockMvc mockMvc;

    private final String BANK_NO = "bankNo";
    private final String COMP_NO = "compNo";
    private final String ORDER_ID = "orderId";
    private final String ORDER_ID_NOT_FOUND = "orderId_NOT_FOUND";
    
    @Before
    public void setup() throws Exception {
    	MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        
        CallResponse success = new CallResponse();
        success.setOk(true);
        success.setMessage(CallResponse.SUCCESS);
		when(backupCardService.getBackupCards(BANK_NO, COMP_NO, ORDER_ID)).thenReturn(success);

		CallResponse failure = new CallResponse();
		failure.setOk(false);
		failure.setMessage(CallResponse.FAILURE);
		when(backupCardService.getBackupCards(BANK_NO, COMP_NO, ORDER_ID_NOT_FOUND)).thenReturn(failure);
    }  
    
	@Test
	public void testGetBackupCards() throws Exception {
		mockMvc.perform(get(String.format("/backupcard/get?bankNo=%s&compNo=%s&orderId=%s", BANK_NO, COMP_NO, ORDER_ID)))
		.andExpect(status().isOk())
		.andExpect(content().json("{\'ok': true, 'message': 'Success'}", false));
		 
		verify(backupCardService, times(1)).getBackupCards(BANK_NO, COMP_NO, ORDER_ID);
		verifyNoMoreInteractions(backupCardService);
	}
    
	@Test
	public void testGetBackupCardsNotFound() throws Exception {
		mockMvc.perform(get(String.format("/backupcard/get?bankNo=%s&compNo=%s&orderId=%s", BANK_NO, COMP_NO, ORDER_ID_NOT_FOUND)))
		.andExpect(status().isOk())
		.andExpect(content().json("{\'ok': false, 'message': 'Failure'}", false));
		 
		verify(backupCardService, times(1)).getBackupCards(BANK_NO, COMP_NO, ORDER_ID_NOT_FOUND);
		verifyNoMoreInteractions(backupCardService);
	}
	
}
