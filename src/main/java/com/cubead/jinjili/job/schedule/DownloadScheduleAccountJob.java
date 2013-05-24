package com.cubead.jinjili.job.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubead.jinjili.domain.model.BaiduAccount;
import com.cubead.jinjili.domain.service.IBaiduAccountService;
import com.cubead.jinjili.job.AbstractJob;
import com.cubead.jinjili.job.manual.DownloadGroupManualJob;

@Service
public class DownloadScheduleAccountJob extends AbstractJob{

	
	@Autowired
	private IBaiduAccountService baiduAccountService;
	@Autowired
	private DownloadGroupManualJob downloadGroupManualJob;

	@Override
	public void execute() {
		if (!enable) {
			return;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		Date endDate = calendar.getTime();
		Date startDate = calendar.getTime();
		downloadGroupManualJob.setStartDate(startDate);
		downloadGroupManualJob.setEndDate(endDate);
		downloadGroupManualJob.setDownloadAccount(true);
		downloadGroupManualJob.setDownloadRoi(false);
		
		List<BaiduAccount> accounts = baiduAccountService.getAllBaiduAccounts();
		downloadGroupManualJob.setAccounts(accounts);
		downloadGroupManualJob.execute();
		
		
	}
	
}
