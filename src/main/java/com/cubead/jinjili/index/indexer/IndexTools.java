package com.cubead.jinjili.index.indexer;
//package com.cubead.jinjili.index;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import com.cubead.datacenter.util.SecurityUtil;
//import com.cubead.searchapi.data.vo.Creative;
//import com.cubead.searchapi.data.vo.Keyword;
//import com.cubead.searchapi.data.vo.Plan;
//import com.cubead.searchapi.data.vo.Quality;
//import com.cubead.searchapi.data.vo.Unit;
//
//
//public class IndexTools {
//	
//	//private static Log log = LogFactory.getLog(IndexTools.class);
//	
//	public static Map<String, String> convertPlan(Plan plan, 
//			String tenantId, String searchEngine) {
//		Map<String, String> data = new HashMap<String, String>();
//		data.put("searchEngine", searchEngine);
//		data.put("tenantId", tenantId);
//		data.put("campaignBiddingStrategy", "CPC");// 百度所有数据均为CPC
//
//		data.put("campaignId", plan.getPlanId());
//		data.put("campaign", plan.getPlanName());
//
//		if (plan.getBudget() == null)
//			data.put("campaignBudget", "0.00");
//		else
//			data.put("campaignBudget", String.format("%.2f", plan.getBudget()));
//
//		if (plan.getRegionTarget() == null || plan.getRegionTarget().length == 0)
//			data.put("campaignRegionTarget", "");
//		else {
//			// String[] array=temp.get("regionTarget").split("\\|\\|");
//			StringBuffer sb = new StringBuffer();
//			for (String region : plan.getRegionTarget()) {
//				sb.append(region).append(",");
//			}
//			if (sb.toString().length() > 0)
//				data.put("campaignRegionTarget", sb.toString().substring(0, sb.toString().length() - 1));
//		}
//
//		if (plan.getNegativeWords() == null || plan.getNegativeWords().length == 0)
//			data.put("campaignNegativeWords", "");
//		else {
//			// String[] array=temp.get("negativeWords").split("\\|\\|");
//			StringBuffer sb = new StringBuffer();
//			for (String word : plan.getNegativeWords()) {
//				sb.append(word).append(",");
//			}
//			if (sb.toString().length() > 0)
//				data.put("campaignNegativeWords", sb.toString().substring(0, sb.toString().length() - 1));
//		}
//
//		if (plan.getExactNegativeWords() == null || plan.getExactNegativeWords().length == 0)
//			data.put("campaignExactNegativeWords", "");
//		else {
//			// String[] array=temp.get("exactNegativeWords").split("\\|\\|");
//			StringBuffer sb = new StringBuffer();
//			for (String word : plan.getExactNegativeWords()) {
//				sb.append(word).append(",");
//			}
//			if (sb.toString().length() > 0)
//				data.put("campaignExactNegativeWords", sb.toString().substring(0, sb.toString().length() - 1));
//		}
//
//		if (plan.getStatus() == null || "".equals(plan.getStatus()))
//			data.put("campaignStatus", "");
//		else
//			data.put("campaignStatus", plan.getStatus());
//
//		if (plan.getPause() == null)
//			data.put("campaignPause", "");
//		else
//			data.put("campaignPause", plan.getPause() + "");
//
//		// 索引中的id
//		String id = getKey_Plan(data);
//		data.put("id", id);
//
//		return data;
//
//	}
//
//	public static Map<String, String> convertUnit(Unit unit, 
//			String tenantId, String searchEngine) {
//		Map<String, String> data = new HashMap<String, String>();
//		data.put("searchEngine", searchEngine);
//		data.put("tenantId", tenantId);
//		data.put("adGroupBiddingStrategy", "CPC");// 百度所有数据均为CPC
//		data.put("campaignId", unit.getPlanId());
//		data.put("adGroupId", unit.getUnitId());
//		data.put("adGroup", unit.getUnitName());
//
//		if (unit.getMaxPrice() == null)
//			data.put("adGroupMaxPrice", "0.00");
//		else
//			data.put("adGroupMaxPrice", String.format("%.2f", unit.getMaxPrice()));
//
//		if (unit.getNegativeWords() == null || unit.getNegativeWords().length == 0)
//			data.put("adGroupNegativeWords", "");
//		else {
//			// String[] array=temp.get("negativeWords").split("\\|\\|");
//			StringBuffer sb = new StringBuffer();
//			for (String word : unit.getNegativeWords()) {
//				sb.append(word).append(",");
//			}
//			if (sb.toString().length() > 0)
//				data.put("adGroupNegativeWords", sb.toString().substring(0, sb.toString().length() - 1));
//		}
//
//		if (unit.getExactNegativeWords() == null || unit.getExactNegativeWords().length == 0)
//			data.put("adGroupExactNegativeWords", "");
//		else {
//			// String[] array=temp.get("exactNegativeWords").split("\\|\\|");
//			StringBuffer sb = new StringBuffer();
//			for (String word : unit.getExactNegativeWords()) {
//				sb.append(word).append(",");
//			}
//			if (sb.toString().length() > 0)
//				data.put("adGroupExactNegativeWords", sb.toString().substring(0, sb.toString().length() - 1));
//		}
//
//		if (unit.getStatus() == null || "".equals(unit.getStatus()))
//			data.put("adGroupStatus", "");
//		else
//			data.put("adGroupStatus", unit.getStatus());
//
//		if (unit.getPause() == null)
//			data.put("adGroupPause", "");
//		else
//			data.put("adGroupPause", unit.getPause() + "");
//
//		// 索引中的id
//		String id = getKey_Unit(data);
//		data.put("id", id);
//
//		return data;
//
//	}
//
//	public static Map<String, String> convertKeyword(Keyword keyword, Quality quality,
//			String tenantId, String searchEngine) {
//
//		Map<String, String> data = new HashMap<String, String>();
//		data.put("searchEngine", searchEngine);
//		data.put("tenantId", tenantId);
//		data.put("keywordBiddingStrategy", "CPC");// 百度所有数据均为CPC
//		data.put("campaignId", keyword.getPlanId());
//		data.put("adGroupId", keyword.getUnitId());
//		data.put("keywordId", keyword.getKeywordId());
//		data.put("keyword", keyword.getKeyword());
//		data.put("quality", quality.getQuality()+"");
//
//		if (keyword.getPrice() == null)
//			data.put("keywordPrice", "0.00");
//		else
//			data.put("keywordPrice", String.format("%.2f", keyword.getPrice()));
//
//		if (keyword.getLinkUrl() == null)
//			data.put("keywordLinkUrl", "");
//		else
//			data.put("keywordLinkUrl", keyword.getLinkUrl());
//
//		if (keyword.getMatchType() == null || "".equals(keyword.getMatchType()))
//			data.put("keywordMatchType", "");
//		else
//			data.put("keywordMatchType",keyword.getMatchType());
//
//		if (keyword.getPause() == null)
//			data.put("keywordPause", "");
//		else
//			data.put("keywordPause", keyword.getPause() + "");
//
//		if (keyword.getStatus() == null || "".equals(keyword.getStatus()))
//			data.put("keywordStatus", "");
//		else
//			data.put("keywordStatus", keyword.getStatus());
//
//		// 索引中的id
//		String id = getKey_Keyword(data);
//		data.put("id", id);
//
//		return data;
//	}
//	
//	
//	public static Map<String, String> convertCreative(Creative creative, 
//			String tenantId, String searchEngine) {
//		Map<String,String> data=new HashMap<String,String>();
//		data.put("searchEngine",searchEngine);
//		data.put("tenantId",tenantId);
//		data.put("campaignId", creative.getPlanId());
//		data.put("adGroupId", creative.getUnitId());
//		data.put("creativeId", creative.getId());
//		data.put("headline", creative.getHeadline());
//		
//		data.put("description1", creative.getDescription1());
//		data.put("description2", creative.getDescription2());
//		
//		data.put("url", creative.getUrl());
//		data.put("displayUrl", creative.getDisplayUrl());
//		
//		if(creative.getPause()==null)
//			data.put("creativePause", "");
//		else
//			data.put("creativePause", ""+creative.getPause());
//
//		//索引中的id
//		String id=getKey_Creative(data);
//		data.put("id", id);
//		
//		return data;
//	}
//
//	public static String getKey_Plan(Map<String, String> data) {
//		StringBuffer sb_key = new StringBuffer();
//		sb_key.append(data.get("tenantId")).append(",");
//		sb_key.append(data.get("searchEngine")).append(",");
//		sb_key.append(data.get("campaignId"));
//		String key = sb_key.toString();
//		return SecurityUtil.MD5(key);
//	}
//
//	public static String getKey_Unit(Map<String, String> data) {
//		StringBuffer sb_key = new StringBuffer();
//		sb_key.append(data.get("tenantId")).append(",");
//		sb_key.append(data.get("searchEngine")).append(",");
//		sb_key.append(data.get("campaignId")).append(",");
//		sb_key.append(data.get("adGroupId"));
//		String key = sb_key.toString();
//		return SecurityUtil.MD5(key);
//	}
//
//	public static String getKey_Keyword(Map<String, String> data) {
//		StringBuffer sb_key = new StringBuffer();
//		sb_key.append(data.get("tenantId")).append(",");
//		sb_key.append(data.get("searchEngine")).append(",");
//		sb_key.append(data.get("campaignId")).append(",");
//		sb_key.append(data.get("adGroupId")).append(",");
//		sb_key.append(data.get("keywordId"));
//		String key = sb_key.toString();
//		return SecurityUtil.MD5(key);
//	}
//
//	public static String getKey_Creative(Map<String, String> data) {
//		StringBuffer sb_key = new StringBuffer();
//		sb_key.append(data.get("tenantId")).append(",");
//		sb_key.append(data.get("searchEngine")).append(",");
//		sb_key.append(data.get("campaignId")).append(",");
//		sb_key.append(data.get("adGroupId")).append(",");
//		sb_key.append(data.get("creativeId"));
//		String key = sb_key.toString();
//		return SecurityUtil.MD5(key);
//	}
//	
//	public static String getKey_Account(Map<String, String> data){
//		StringBuffer sb_key = new StringBuffer();
//		sb_key.append(data.get("accountId")).append(",");
//		sb_key.append(data.get("excludeIp")).append(",");
//		sb_key.append(data.get("openDomains")).append(",");
//		sb_key.append(data.get("statDate"));
//		String key = sb_key.toString();
//		return SecurityUtil.MD5(key);
//	}
//	
//	public static int parseInt(String s){
//		try {
//			int result=Integer.parseInt(s);
//			return result;
//		} catch (NumberFormatException e) {
//			return -1;
//		}
//	}
//	
////	public static void optimizeIndex(String[] tenantIds,String[] indexTypes){
////		for (String type : indexTypes) {
////			for (String tenantId : tenantIds) {
////				String indexDir=ConfigUtil.getIndexDir(tenantId, type);
////				File dirFile=new File(indexDir);
////				if(dirFile.exists()){
////					CommonIndexer indexer=null;
////					try{
////						indexer=new CommonIndexer(tenantId, type);
////						log.info("optimize "+indexDir+"...");
////						indexer.optimize();
////					}
////					catch (Exception e) {
////						log.error(e.getMessage(),e);
////					}
////					finally{
////						if(indexer!=null)
////							indexer.close();
////					}
////				}
////				else
////					log.warn(indexDir+" doesn't exist!!!");
////				
////				
////			}
////		}
////	}
//	
//	public static void main(String[] args) throws Exception {
////		Config.loadConfigXML();
////		TenantDAO.iniUserList();
////		String[] tenantIds=TenantDAO.getAllBaiduTenantId();
////		
////		IndexTools.optimizeIndex(tenantIds, Constants.INDEX_ROI_TYPES);
//	}
//}
