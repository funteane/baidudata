package com.cubead.jinjili.index.indexer;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class IndexConfig {
	
	private static Logger logger = Logger.getLogger(IndexConfig.class);

	// public static Map<String, Map<String, Map<String, String>>> getConfig() {
	// return config;
	// }
	//
	// public static void setConfig(Map<String, Map<String, Map<String,
	// String>>> config) {
	// IndexConfig.config = config;
	// }

	public static Map<String, Map<String, Map<String, String>>> config = null;
	public static String fileName;
	public static String rootPath;
	
	static {
		fileName = IndexConfig.class.getResource("/indexConfig.xml").getPath();
	}

	public static void loadConfig() {
		try {
			config = new HashMap<String, Map<String, Map<String, String>>>();

			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(new File(fileName));

			Element root = doc.getRootElement();
			@SuppressWarnings("rawtypes")
			List documentList = root.getChild("documentList").getChildren();
			for (int i = 0; i < documentList.size(); i++) {
				Element element = (Element) documentList.get(i);

				String docName = element.getAttributeValue("name").trim();

				@SuppressWarnings("rawtypes")
				List fieldList = element.getChild("fieldList").getChildren();

				Map<String, Map<String, String>> fieldMap = new HashMap<String, Map<String, String>>();

				for (int j = 0; j < fieldList.size(); j++) {

					Element field = (Element) fieldList.get(j);

					String fieldName = field.getAttributeValue("name").trim();

					Map<String, String> fieldChildrenMap = new HashMap<String, String>();
					fieldChildrenMap.put("type", field.getChildText("type").trim().toLowerCase());

					// 如果字段类型是number型，则读出小数位数
					if ("number".equals(fieldChildrenMap.get("type")))
						fieldChildrenMap.put("point", field.getChild("type").getAttributeValue("point"));

					fieldChildrenMap.put("boost", field.getChildText("boost").trim());
					fieldChildrenMap.put("is_store", field.getChildText("is_store").trim().toUpperCase());
					fieldChildrenMap.put("is_analyzed",	field.getChildText("is_analyzed").trim().toUpperCase());

					fieldMap.put(fieldName, fieldChildrenMap);

				}// for j

				config.put(docName, fieldMap);

			}// for i

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static Map<String, Map<String, String>> getCommonDocConfig() {
		return config.get("common");
	}

	public static void main(String[] args) throws Exception {
		loadConfig();
		System.out.println(config.get("common").get("campaignId").get("boost"));
	}
}
