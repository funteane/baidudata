package com.cubead.jinjili.domain.service;

import java.util.List;

import com.cubead.jinjili.domain.model.BaiduAccount;

public interface IBaiduAccountService {
	
	List<BaiduAccount> getAllBaiduAccounts();
	
	BaiduAccount getBaiduAccount(String accountId);

}
