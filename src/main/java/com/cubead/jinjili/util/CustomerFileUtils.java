package com.cubead.jinjili.util;

import java.io.File;

import com.cubead.jinjili.common.ApplicationProperties;

public class CustomerFileUtils {

	public static String createDownFileName(String accountName, String indexType, String startDate) {
		return getDownloadRootPath() + File.separator + "baidu" + File.separator + indexType + File.separator + startDate + File.separator + accountName + ".txt";
	}

	private static String getDownloadRootPath() {
		return ApplicationProperties.getProproperty("download.temp.dir");
	}
}
