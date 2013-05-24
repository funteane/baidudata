package com.cubead.jinjili.controller;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cubead.jinjili.common.ApplicationProperties;
import com.cubead.jinjili.common.web.BaseController;
import com.cubead.jinjili.job.manual.MakeReportManualJob;
import com.cubead.jinjili.job.schedule.KeywordDailyReportJob;
import com.cubead.jinjili.util.Tools;

@Controller
public class ReportController extends BaseController{
	
	@Autowired
	KeywordDailyReportJob keywordDailyReportJob;
	
	@Autowired
	MakeReportManualJob makeReportManualJob;
	
	@RequestMapping
	public ModelAndView reportList(){
		String directoryName = ApplicationProperties.getProproperty("base.report.download.path");
		File directory = new File(directoryName);
		ModelAndView modelAndView = new ModelAndView("report/reportList");
		String[] fileNames = directory.list();
		ArrayUtils.reverse(fileNames);
		modelAndView.addObject("fileNames", fileNames);
		return modelAndView;
	}
	
	@RequestMapping
	public ModelAndView rexecute(){
		ModelAndView modelAndView = new ModelAndView("redirect:/report/reportList.do");
		keywordDailyReportJob.execute();
		return modelAndView;
	}
	
	@RequestMapping
	public ModelAndView makeKeywordDailyReport(String start, String end){
		ModelAndView modelAndView = new ModelAndView("redirect:/report/reportList.do");
		Date startDate = Tools.getNormalDate(start);
		Date endDate = Tools.getNormalDate(end);
		makeReportManualJob.setStartDate(startDate);
		makeReportManualJob.setEndDate(endDate);
		makeReportManualJob.makeKeywordDailyReport();
		return modelAndView;		
	}

}
