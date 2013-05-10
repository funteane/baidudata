package com.cubead.jinjili.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cubead.jinjili.common.web.BaseController;
import com.cubead.jinjili.job.schedule.KeywordDailyReportJob;
import com.cubead.jinjili.util.Constants;

@Controller
public class ReportController extends BaseController{
	
	@Autowired
	KeywordDailyReportJob keywordDailyReportJob;
	
	@RequestMapping
	public ModelAndView reportList(){
		String directoryName = Constants.REPORT_DOWNLOAD_PATH;
		File directory = new File(directoryName);
		ModelAndView modelAndView = new ModelAndView("report/reportList");
		modelAndView.addObject("fileNames", directory.list());
		return modelAndView;
	}
	
	@RequestMapping
	public ModelAndView rexecute(){
		ModelAndView modelAndView = new ModelAndView("redirect:/report/reportList.do");
		keywordDailyReportJob.execute();
		return modelAndView;
	}

}
