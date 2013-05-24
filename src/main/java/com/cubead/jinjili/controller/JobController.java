package com.cubead.jinjili.controller;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cubead.jinjili.common.dao.ICommonService;
import com.cubead.jinjili.common.dao.PageBean;
import com.cubead.jinjili.domain.model.BaiduAccount;
import com.cubead.jinjili.domain.model.TaskModel;
import com.cubead.jinjili.domain.service.IBaiduAccountService;
import com.cubead.jinjili.domain.service.ITaskService;
import com.cubead.jinjili.job.manual.DownloadSingleManualJob;

@Controller
public class JobController {
	
	@Autowired
	private ITaskService taskService;
	@Autowired
	private ICommonService commonService;
	@Autowired
	private IBaiduAccountService baiduAccountService;
	@Autowired
	private DownloadSingleManualJob downloadManualJob;

	@RequestMapping
	public ModelAndView toJobs(int pageNo, TaskModel taskModel){
		PageBean<TaskModel> pageBean = new PageBean<TaskModel>();
		pageBean.setCurrentPage(pageNo);
		pageBean.setLength(20);
		
		ModelAndView modelAndView = new ModelAndView("job/jobs");
		modelAndView.addObject("pageBean", taskService.getAllTasks(pageBean, taskModel));
		
		return modelAndView;
	}
	
	@RequestMapping
	public ModelAndView rexecute(Long id, Long accountId){
		Calendar calendar = Calendar.getInstance();
		TaskModel taskModel = commonService.get(TaskModel.class, id);
		calendar.setTime(taskModel.getDataStartDate());
		Date startDate = calendar.getTime();
		calendar.setTime(taskModel.getDataEndDate());
		Date endDate = calendar.getTime();
		
		BaiduAccount baiduAccount = baiduAccountService.getBaiduAccount(String.valueOf(accountId));
		
		downloadManualJob.setTaskModel(taskModel);
		downloadManualJob.setBaiduAccount(baiduAccount);
		downloadManualJob.setStartDate(startDate);
		downloadManualJob.setEndDate(endDate);
		
		downloadManualJob.execute();
		
		ModelAndView modelAndView = new ModelAndView("redirect:/job/toJobs.do?pageNo=1");
		return modelAndView;
	}

}
