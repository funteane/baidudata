package com.cubead.jinjili.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class BaseMultiActionController extends MultiActionController {
	public ModelAndView handleException(HttpServletRequest request,HttpServletResponse response,Throwable ex) throws Throwable{		
		logger.error(ex.getMessage(),ex);
		throw ex;
	}
}
