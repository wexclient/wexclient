package com.livngroup.gds.web;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.livngroup.gds.LivnDemoApplication;
import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.WexRuntimeException;
import com.livngroup.gds.response.CallResponse;
import com.livngroup.gds.response.ErrorResponse;
import com.livngroup.gds.service.WexBackupCardService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LivnDemoApplication.class)
@WebAppConfiguration
public class BackupCardControllerTest {
	
	@Mock(name = "wexBackupCardService")
	private WexBackupCardService backupCardServiceMock;
	
	@InjectMocks
	private BackupCardController backupCardController;
	
	private MockMvc mockMvc;
	
	private final String BANK_NO = "bankNo";
	private final String COMP_NO = "compNo";
	private final String ORDER_ID = "orderId";
	private final String ERROR_CODE = "errorCode";
	private final WexEntity WEX_ENTITY = WexEntity.BACKUP_CARD;
	private final String ERROR_MESSAGE = "errorMessage"; 
	private final String ERROR_LINK = "errorLink";
	private final String ERROR_DEV_MESSAGE = "developerMessage";	
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(backupCardController).build();
	}  
	
	@Test
	public void testGetBackupCards() throws Exception {
		
		CallResponse success = new CallResponse();
		success.setOk(true);
		success.setMessage(CallResponse.SUCCESS);
		when(backupCardServiceMock.getBackupCards(BANK_NO, COMP_NO, ORDER_ID)).thenReturn(success);
		
		mockMvc.perform(get(String.format("/backupcard/get?bankNo=%s&compNo=%s&orderId=%s", BANK_NO, COMP_NO, ORDER_ID)))
		.andExpect(status().isOk())
		.andExpect(content().json("{'ok': true, 'message': 'Success'}", false));
		 
		verify(backupCardServiceMock, times(1)).getBackupCards(BANK_NO, COMP_NO, ORDER_ID);
		verifyNoMoreInteractions(backupCardServiceMock);
	}
	
	@Test
	public void testGetBackupCardsNotFound() throws Exception {
		
		CallResponse failure = new CallResponse();
		failure.setOk(false);
		failure.setMessage(CallResponse.FAILURE);
		when(backupCardServiceMock.getBackupCards(BANK_NO, COMP_NO, ORDER_ID)).thenReturn(failure);

		mockMvc.perform(get(String.format("/backupcard/get?bankNo=%s&compNo=%s&orderId=%s", BANK_NO, COMP_NO, ORDER_ID)))
		.andExpect(status().isOk())
		.andExpect(content().json("{'ok': false, 'message': 'Failure'}", false));
		 
		verify(backupCardServiceMock, times(1)).getBackupCards(BANK_NO, COMP_NO, ORDER_ID);
		verifyNoMoreInteractions(backupCardServiceMock);
	}
	
	@Test
	public void testGetBackupCardsExceptionThrown() throws Exception {
		
		WexRuntimeException exceptionThrow = new WexRuntimeException(
				new ErrorResponse(HttpStatus.UNAUTHORIZED, ERROR_CODE, WEX_ENTITY, ERROR_MESSAGE, ERROR_LINK, ERROR_DEV_MESSAGE, null));
		when(backupCardServiceMock.getBackupCards(BANK_NO, COMP_NO, ORDER_ID)).thenThrow(exceptionThrow);

		mockMvc.perform(get(String.format("/backupcard/get?bankNo=%s&compNo=%s&orderId=%s", BANK_NO, COMP_NO, ORDER_ID)))
		.andExpect(status().isUnauthorized())
		.andExpect(content().json("{'ok': false}", false))
		.andExpect(content().json("{'status': " + HttpStatus.UNAUTHORIZED.value() + "}", false))
		.andExpect(content().json("{'wexEntity': '" + WEX_ENTITY + "'}", false))
		.andExpect(content().json("{'message': '" + ERROR_MESSAGE + "'}", false))
		.andExpect(content().json("{'link': '" + ERROR_LINK + "'}", false))
		.andExpect(content().json("{'developerMessage': '" + ERROR_DEV_MESSAGE + "'}", false));
		 
		verify(backupCardServiceMock, times(1)).getBackupCards(BANK_NO, COMP_NO, ORDER_ID);
		verifyNoMoreInteractions(backupCardServiceMock);
	}
	
}
