package com.cubead.jinjili.index.parser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cubead.jinjili.domain.model.AccountModel;
import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.index.indexer.IndexMangerFactory.IndexType;
import com.cubead.jinjili.util.Constants;
import com.cubead.jinjili.util.Tools;


public class AccountCsvFileParser extends CsvFileParser{

	//account down file structure
	//userId	balance	    cost	    payment	    budget	regionTarget	                                                excludeIp	openDomains
	//5421714	32957.02	77442.98	100363.63	51.0	26000||20000||14000||17000||12000||33000||27000||11000||30000	-	        camel.com.cn
	public AccountCsvFileParser(String fileName, Date createDate) {
		super(fileName, createDate);
	}

	@Override
	public Map<String, String> next() {
		Map<String, String> tempMap = super.next();

		Map<String, String> data = new HashMap<String, String>();
		data.put("searchEngine", Constants.SEARCH_ENGINE_BAIDU);
//		data.put("keywordBiddingStrategy", "CPC"); // 百度所有数据均为CPC
		data.put("balance", tempMap.get("balance"));
		data.put("cost", tempMap.get("cost"));
		data.put("payment", tempMap.get("payment"));
		data.put("budget", tempMap.get("budget"));
		data.put("regionTarget", tempMap.get("regionTarget"));
		data.put("accountId", tempMap.get("userId"));
		data.put("excludeIp", tempMap.get("excludeIp"));
		data.put("openDomains", tempMap.get("openDomains"));
		data.put("createDate", Tools.getNormalDateString(getCreateDate()));
		return data;
	}
	
	@Override
	public Indexable nextIndexable() {
		Map<String, String> tempMap = super.next();
		AccountModel accountModel = new AccountModel();
		accountModel.setBalance(Tools.empty(tempMap.get("balance")) ? null : Double.parseDouble(tempMap.get("balance")));
		accountModel.setBudget(Tools.empty(tempMap.get("budget")) ? null : Double.parseDouble(tempMap.get("budget")));
		accountModel.setCost(Tools.empty(tempMap.get("cost")) ? null : Double.parseDouble(tempMap.get("cost")));
		accountModel.setCreateDate(getCreateDate());
		accountModel.setPayment(tempMap.get("payment") == null ? null : Double.parseDouble(tempMap.get("payment")));
		accountModel.setAccountId(tempMap.get("userId"));
		accountModel.setType(1);
		
		String excludeIps = tempMap.get("excludeIp");
		List<String> excludeIpList = new ArrayList<String>();
		if (!Tools.empty(excludeIps)) {
			Tools.array2List(excludeIps.trim().split("\\|\\|"), excludeIpList);
		}
		accountModel.setExcludeIp(excludeIpList);
		
		String openDomains = tempMap.get("openDomains");
		List<String> openDomainList = new ArrayList<String>();
		if (!Tools.empty(openDomains)) {
			Tools.array2List(openDomains.trim().split("\\|\\|"), openDomainList);
		}
		accountModel.setOpenDomains(openDomainList);
		
		String regionTargets = tempMap.get("regionTarget");
		List<Integer> regionArray = new ArrayList<Integer>();
		if (!Tools.empty(regionTargets)) {
			String[] regions = regionTargets.trim().split("\\|\\|");
			for(String region : regions){
				regionArray.add(Integer.valueOf(region));
			}
		}
		accountModel.setRegions(regionArray);
		
		accountModel.setIndexType(IndexType.ACCOUNT);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		accountModel.setCreateDate(calendar.getTime());
		
		

		return accountModel;
	}
}
