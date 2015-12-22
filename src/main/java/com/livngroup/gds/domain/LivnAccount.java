package com.livngroup.gds.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="LIVN_ACCOUNT")
public class LivnAccount {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "LIVN_ACCOUNT_ID", unique = true)
    private String id;
    
	@Column(name = "USER_NAME", unique = true)
	private String username;
    
	@Column(name = "PASSWORD", unique = false)
	private String password;
    
	@Column(name = "ROLE", unique = false)
	private String role;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}
