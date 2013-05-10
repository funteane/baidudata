package com.cubead.jinjili.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubead.jinjili.common.dao.ICommonDAO;
import com.cubead.jinjili.domain.model.BaiduAccount;
import com.cubead.jinjili.util.Tools;

@Service("baiduAccountService")
public class BaiduAccountServiceImpl implements IBaiduAccountService{

	@Autowired
	private ICommonDAO commonDAO;
	
	@Override
	public List<BaiduAccount> getAllBaiduAccounts() {
		String hql = "FROM BaiduAccount ";
		List<BaiduAccount> accounts = commonDAO.find(hql);
		return accounts;
	}

	@Override
	public BaiduAccount getBaiduAccount(String accountId) {
		String hql = "FROM BaiduAccount WHERE accountId = ? ";
		List<BaiduAccount> accounts = commonDAO.find(hql, accountId);
		return Tools.empty(accounts) ? null : accounts.get(0);
	}

}
