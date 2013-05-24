package com.cubead.jinjili.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cubead.jinjili.domain.model.BaiduAccount;
import com.cubead.jinjili.domain.service.IBaiduAccountService;
import com.cubead.jinjili.job.manual.DownloadGroupManualJob;
import com.cubead.jinjili.util.Tools;



@Controller
public class HistoryDataController {
	@Autowired
	private IBaiduAccountService baiduAccountService;
	
	@Autowired
	private DownloadGroupManualJob downloadGroupManualJob;
	
	@RequestMapping
	public ModelAndView historyAccountData(String accountId, String start, String end){
		ModelAndView modelAndView = new ModelAndView();
		
		BaiduAccount baiduAccount = baiduAccountService.getBaiduAccount(accountId);
		if (Tools.empty(baiduAccount)) {
			modelAndView.addObject("result", "没有找到".concat(accountId).concat("账户"));
			return modelAndView;
		}
		
		Date startDate = Tools.getNormalDate(start);
		Date endDate = Tools.getNormalDate(end);
		
		List<BaiduAccount> accounts = new ArrayList<BaiduAccount>();
		accounts.add(baiduAccount);
		downloadGroupManualJob.setAccounts(accounts);
		downloadGroupManualJob.setStartDate(startDate);
		downloadGroupManualJob.setEndDate(endDate);
		downloadGroupManualJob.setDownloadAccount(true);
		downloadGroupManualJob.setDownloadRoi(true);
		downloadGroupManualJob.execute();
		
		return modelAndView;
	}
	
	@RequestMapping
	public ModelAndView historyData(String start, String end){
		ModelAndView modelAndView = new ModelAndView();
		
		Date startDate = Tools.getNormalDate(start);
		Date endDate = Tools.getNormalDate(end);
		
		List<BaiduAccount> accounts = baiduAccountService.getAllBaiduAccounts();
	
		downloadGroupManualJob.setAccounts(accounts);
		downloadGroupManualJob.setStartDate(startDate);
		downloadGroupManualJob.setEndDate(endDate);
		downloadGroupManualJob.setDownloadAccount(true);
		downloadGroupManualJob.setDownloadRoi(true);
		downloadGroupManualJob.execute();
		
		return modelAndView;
	}
	
	
	@RequestMapping
	public ModelAndView historyRoi(String start, String end){
		ModelAndView modelAndView = new ModelAndView("redirect:/job/toJobs.do?pageNo=1");
		Date startDate = Tools.getNormalDate(start);
		Date endDate = Tools.getNormalDate(end);
		List<BaiduAccount> accounts = baiduAccountService.getAllBaiduAccounts();
		downloadGroupManualJob.setAccounts(accounts);
		downloadGroupManualJob.setEndDate(endDate);
		downloadGroupManualJob.setStartDate(startDate);
		downloadGroupManualJob.setDownloadRoi(true);
		downloadGroupManualJob.setDownloadAccount(false);
		downloadGroupManualJob.execute();
		return modelAndView;
	}
	
	
	@RequestMapping
	public ModelAndView historyAccount(String start, String end){
		ModelAndView modelAndView = new ModelAndView("redirect:/job/toJobs.do?pageNo=1");
		Date startDate = Tools.getNormalDate(start);
		Date endDate = Tools.getNormalDate(end);
		List<BaiduAccount> accounts = baiduAccountService.getAllBaiduAccounts();
		downloadGroupManualJob.setAccounts(accounts);
		downloadGroupManualJob.setEndDate(endDate);
		downloadGroupManualJob.setStartDate(startDate);
		downloadGroupManualJob.setDownloadAccount(true);
		downloadGroupManualJob.setDownloadRoi(false);
		downloadGroupManualJob.execute();
		return modelAndView;
	}
	
	@RequestMapping
	public ModelAndView toHistoryData(){
		ModelAndView modelAndView = new ModelAndView("historydata/historyData");
		return modelAndView;
	}
	
	

}
