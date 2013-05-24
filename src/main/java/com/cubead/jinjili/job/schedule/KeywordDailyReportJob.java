package com.cubead.jinjili.job.schedule;


import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubead.jinjili.job.AbstractJob;
import com.cubead.jinjili.job.manual.MakeReportManualJob;

@Service
public class KeywordDailyReportJob extends AbstractJob{
	
	@Autowired
	private MakeReportManualJob makeReportManualJob;

	@Override
	public void execute() {
		
		if (!enable) {
			return;
		}
		
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		Date endDate = calendar.getTime();
		Date startDate = calendar.getTime();
		
		makeReportManualJob.setStartDate(startDate);
		makeReportManualJob.setEndDate(endDate);
		makeReportManualJob.makeKeywordDailyReport();
		
	}

}
