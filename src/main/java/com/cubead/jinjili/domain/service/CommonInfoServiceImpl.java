package com.cubead.jinjili.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubead.jinjili.common.dao.ICommonDAO;
import com.cubead.jinjili.domain.model.CommonInfo;

@Service("commonInfoService")
public class CommonInfoServiceImpl implements ICommonInfoService{
	
	@Autowired
	private ICommonDAO commonDAO;

	@Override
	public List<CommonInfo> getAllCommonInfos() {
		String hql = "FROM CommonInfo ";
		return commonDAO.find(hql);
	}

}
