package com.livngroup.gds.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.livngroup.gds.response.ErrorResponse;

@RunWith(Parameterized.class)
public class WexExceptionTest {

	@Parameters
    public static Collection<WexException> data() {
        return Arrays.asList(
            	new WexAppException(null),
            	new WexAppException(new Exception(), null),
            	new WexRuntimeException(null),
            	new WexRuntimeException(new Exception(), null)
        	
        );
    }
	
	private WexException exceptionToTest;
	
	public WexExceptionTest(WexException exceptionToTest) {
		this.exceptionToTest = exceptionToTest;
	}
	
    @Test
    public void wexExceptionMustNotHaveErrorResponceNull() {
     	assertNotNull(exceptionToTest.getErrorResponse());
    	assertEquals(exceptionToTest.getErrorResponse(), ErrorResponse.DEAULT);
    	assertTrue(StringUtils.containsAny(exceptionToTest.getMessage(), ErrorResponse.DEAULT.getMessage()));
    }
}
