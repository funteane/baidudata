package com.cubead.jinjili.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "BAIDU_ACCOUNT")
public class BaiduAccount implements Serializable{

	private static final long serialVersionUID = 2777091924549590199L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "ACCOUNT_ID")
	private String accountId;
	
	@Column(name = "USER_NAME")
	private String userName;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "TOKEN")
	private String token;
	
	@Column(name = "FIRST_DOWNLOAD")
	private String firstDownload = "1";
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getFirstDownload() {
		return firstDownload;
	}

	public void setFirstDownload(String firstDownload) {
		this.firstDownload = firstDownload;
	}
	
	
	

}
