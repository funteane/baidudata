package com.cubead.jinjili.index.indexer;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;

import com.cubead.jinjili.domain.model.AccountModel;
import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.util.Tools;

public class AccountIndexer extends Indexer{

	public AccountIndexer(IndexerManager indexerManager) {
		super(indexerManager);
	}

	@Override
	public Document convert2Document(Indexable indexable) {
		Document document = new Document();
		AccountModel account = (AccountModel) indexable;
		//Field field = new Field("planId", account..getPlanId(), Field.Store.YES ,Field.Index.NOT_ANALYZED);
//		Field field = new Field("username", account.getUsername(), Field.Store.YES ,Field.Index.NOT_ANALYZED);
//		document.add(field);
//		
//		field = new Field("password", account.getPassword(), Field.Store.YES ,Field.Index.NOT_ANALYZED);
//		document.add(field);
		
		document.add(new Field("accountId", account.getAccountId(), Field.Store.YES ,Field.Index.NOT_ANALYZED));
		
		document.add(new NumericField("payment", Field.Store.YES ,true).setDoubleValue(account.getPayment()));
		document.add(new NumericField("balance", Field.Store.YES ,true).setDoubleValue(account.getBalance()));
		document.add(new NumericField("budget", Field.Store.YES ,true).setDoubleValue(account.getBudget()));
		document.add(new NumericField("cost", Field.Store.YES ,true).setDoubleValue(account.getCost()));
		document.add(new NumericField("type", Field.Store.YES ,true).setIntValue(account.getType()));
		
		for(String excludeIp : account.getExcludeIp()){
			document.add(new Field("excludeIp", excludeIp, Field.Store.YES, Field.Index.NOT_ANALYZED));
		}
		
		for(String openDomain : account.getOpenDomains()){
			document.add(new Field("openDomains", openDomain, Field.Store.YES, Field.Index.NOT_ANALYZED));
		}
		
		for(Integer region : account.getRegions()){
			document.add(new NumericField("regions", Field.Store.YES ,true).setIntValue(region));
		}
		
		document.add(new Field("createDate", Tools.getSimpleDateString(account.getCreateDate()), Field.Store.YES, Field.Index.NOT_ANALYZED));

		document.add(new Field("indexType", account.getIndexType().name(), Field.Store.YES ,Field.Index.NOT_ANALYZED));
		
		document.add(new Field("id", Tools.generateDocId(account.getAccountId(), account.getIndexType().name(), 
				Tools.getSimpleDateString(account.getCreateDate())), Field.Store.YES ,Field.Index.NOT_ANALYZED));
		
		return document;
	}
	
	@Override
	public Indexable convert2Indexable(Document document) {
		AccountModel accountModel = new AccountModel();
		accountModel.setBalance(document.get("balance") == null ? null : Double.valueOf(document.get("balance")));
		accountModel.setBudget(document.get("budget") == null ? null : Double.valueOf(document.get("budget")));
		accountModel.setCost(document.get("cost") == null ? null : Double.valueOf(document.get("cost")));
		accountModel.setPayment(document.get("payment") == null ? null : Double.valueOf(document.get("payment")));
		accountModel.setPassword(document.get("password"));
		accountModel.setUsername(document.get("username"));
		accountModel.setType(document.get("type") == null ? 1 : Integer.parseInt(document.get("type")));
		accountModel.setAccountId(document.get("accountId"));
		
		accountModel.setCreateDate(Tools.getNormalDate(document.get("createDate")));
		
		String[] excludeIps = document.getValues("excludeIp");
		String[] openDomains = document.getValues("openDomains");
		String[] regions = document.getValues("regions");
		
		List<String> excludeIpArray = new ArrayList<String>();
		for(String excludeIp : excludeIps){
			excludeIpArray.add(excludeIp);
		}
		accountModel.setExcludeIp(excludeIpArray);

		List<String> openDomainArray = new ArrayList<String>();
		for(String openDomain : openDomains){
			openDomainArray.add(openDomain);
		}
		accountModel.setOpenDomains(openDomainArray);
		
		List<Integer> regionArray = new ArrayList<Integer>();
		for(String region : regions){
			regionArray.add(Integer.valueOf(region));
		}
		accountModel.setRegions(regionArray);
//		accountModel.setExcludeIp(excludeIp);
//		accountModel.setOpenDomains(openDomains);
//		accountModel.setRegions(regions);
		
		accountModel.setKey(document.get("id"));

		return accountModel;
	}

}
