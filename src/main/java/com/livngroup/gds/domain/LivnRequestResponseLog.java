package com.livngroup.gds.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="LIVN_REQ_RESP_LOG")
public class LivnRequestResponseLog {
	
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "LIVN_REQ_RESP_LOG_ID", unique = true)
    private String id;
	
	@Column(name = "LIVN_USER", unique = false)
	private String livnUser;
	
	@Column(name = "URL", unique = false)
	private String url;
	
	@Column(name = "EXECUTION_TIME", unique = false)
	private long executionTime;
	
	@Lob
	@Column(name = "INPUT_JSON_PARAMS", unique = false, length = 10000)
	private String inputJsonParms;
	
	@Lob
	@Column(name = "INPUT_SOAP_BODY", unique = false, length = 10000)
	private String inputSoapBody;
	
	@Column(name = "SUCCESS", unique = false)
	private boolean success;
	
	@Lob
	@Column(name = "RESULT_SOAP_BODY", unique = false, length = 10000)
	private String resultSoap;
	
	@Lob
	@Column(name = "RESULT_JSON_BODY", unique = false, length = 10000)
	private String resultJson;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLivnUser() {
		return livnUser;
	}

	public void setLivnUser(String livnUser) {
		this.livnUser = livnUser;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	public String getInputJsonParms() {
		return inputJsonParms;
	}

	public void setInputJsonParms(String inputJsonParms) {
		this.inputJsonParms = inputJsonParms;
	}

	public String getInputSoapBody() {
		return inputSoapBody;
	}

	public void setInputSoapBody(String inputSoapBody) {
		this.inputSoapBody = inputSoapBody;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResultSoap() {
		return resultSoap;
	}

	public void setResultSoap(String resultSoap) {
		this.resultSoap = resultSoap;
	}

	public String getResultJson() {
		return resultJson;
	}

	public void setResultJson(String resultJson) {
		this.resultJson = resultJson;
	}
	
	
}
