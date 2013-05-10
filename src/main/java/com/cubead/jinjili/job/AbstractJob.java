package com.cubead.jinjili.job;

import org.apache.log4j.Logger;

public abstract class AbstractJob {
	
	protected  final Logger logger = Logger.getLogger(AbstractJob.class);

	protected boolean enable = true;

	protected abstract void execute();

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	
	

}