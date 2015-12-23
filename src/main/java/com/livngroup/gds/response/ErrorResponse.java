package com.livngroup.gds.response;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;

import com.livngroup.gds.domain.WexEntity;
import com.livngroup.gds.exception.WexException;

public class ErrorResponse implements IWexResponse<Object> {

	private final static HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.I_AM_A_TEAPOT;
	
	public final static String URL_DEFAULT = "https://livngroup.com/onlinehelp";
	
	public final static String REMOTE_CALL_ERROR_CODE = HttpStatus.SERVICE_UNAVAILABLE + "-0001";
	public final static String INVALID_CREDENTIALS_ERROR_CODE = HttpStatus.UNAUTHORIZED + "-0001";
	
	/** Error is not Ok, it will always be false. */
	private Boolean ok = Boolean.FALSE;
	
	/** holds redundantly the HTTP error status code, so that the developer can "see" it without having to analyze the response's header */
	private HttpStatus status;
	
	/** this is an internal code specific to the API (should be more relevant for business exceptions) */
	private String code;
	
	private WexEntity wexEntity;
	
	/** short description of the error, what might have cause it and possibly a "fixing" proposal */
	private String message;
	
	/** points to an online resource, where more details can be found about the error */
	private String link;
	
	/** 
	 * detailed message, containing additional data that might be relevant to the developer. 
	 * This should only be available when the "debug" mode is switched on and could potentially 
	 * contain stack trace information or something similar 
	 */
	private String developerMessage;

	private Object result;
	
	public static final ErrorResponse DEAULT = new ErrorResponse(
			DEFAULT_HTTP_STATUS, 
			DEFAULT_HTTP_STATUS + "-0000",
			WexEntity.GENERAL,
			WexException.MESSAGE_DEFAULT, 
			URL_DEFAULT, 
			WexException.MESSAGE_DEFAULT,
			null
	);
	
	public ErrorResponse(HttpStatus status, String code, WexEntity wexEntity, String message, String link, String developerMessage, Object result) {
		super();
		this.status = status;
		this.code = code;
		this.wexEntity = wexEntity;
		this.message = message;
		this.link = link;
		this.developerMessage = developerMessage;
		this.result = result;
	}

	@Override
	public Boolean getOk() {
		return ok;
	}
	public int getStatus() {
		return ObjectUtils.defaultIfNull(status, DEFAULT_HTTP_STATUS).value();
	}
	public String getCode() {
		return code;
	}
	public WexEntity getWexEntity() {
		return wexEntity;
	}
	@Override
	public String getMessage() {
		return message;
	}
	public String getLink() {
		return link;
	}
	public String getDeveloperMessage() {
		return developerMessage;
	}
	@Override
	public Object getResult() {
		return result;
	}
	
	@Override
	public String toString() {
		return "ErrorData [ok=" + ok + ", status=" + status + ", code=" + code + ", wexEntity=" + wexEntity + ", message=" + message + "]";
	}

}
