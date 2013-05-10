//package com.cubead.jinjili.util;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.cubead.searchapi.client.data.ApiContext;
//import com.cubead.searchapi.client.type.Method;
//import com.cubead.searchapi.client.type.Network;
//
//public class BaiduUtil {
//
//	private static Log log = LogFactory.getLog(BaiduUtil.class);
//
//	private static <T> ApiContext getApiContext(BaiduAccount baiduAccount) {
//		ApiContext baiduApiContext = new ApiContext(baiduAccount.getAccountName(), baiduAccount.getAccountPwd(), Network.BAIDU, Method.POST, baiduAccount.getToken());
//		return baiduApiContext;
//	}
//}
