package com.cubead.jinjili.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cubead.jinjili.common.ApplicationProperties;
import com.cubead.jinjili.domain.model.CommonInfo;
import com.cubead.jinjili.domain.model.CommonInfo.InfoType;
import com.cubead.jinjili.domain.model.Indexable;
import com.cubead.jinjili.domain.model.KeywordModel;
import com.cubead.jinjili.domain.model.RoiModel;
import com.cubead.jinjili.domain.service.ICommonInfoService;
import com.cubead.jinjili.util.Constants;
import com.cubead.jinjili.util.Tools;

public class ReportManager {
	
	public static Workbook createWorkbook(String fileName) throws Exception{
		String path = ApplicationProperties.getProproperty("base.report.download.path");
		File file = new File(path.concat(fileName));
		if (file.exists()) {
			file.delete();
		}
		Workbook wb=new XSSFWorkbook();
		Sheet sheet=wb.createSheet("关键词每日报表");
		
		int counter = 0;
		Row row=sheet.createRow(0);
		for (String header : Constants.keywordReportHeaders) {
			Cell cell = row.createCell(counter);
			cell.setCellValue(header);
			counter++;
		}
		OutputStream os=new FileOutputStream(path.concat(fileName));
		wb.write(os);
		os.flush();
		os.close();
		
		return wb;
	}
	
	public static void create07ExcelFile(String fileName, List<Indexable> indexables, Map<String, Indexable> indexableMap, 
			Map<String, String> commonInfoMap) throws Exception{
		String path = ApplicationProperties.getProproperty("base.report.download.path");
		FileInputStream fs =new FileInputStream(path.concat(fileName));
//		POIFSFileSystem ps=new POIFSFileSystem(fs);
		Workbook wb=new XSSFWorkbook(fs);
			
		Sheet sheet = wb.getSheetAt(0);
	    Row row;
		
		for (int rowIndex = 0, length = indexables.size(); rowIndex < length; rowIndex++) {
			RoiModel roiModel = (RoiModel) indexables.get(rowIndex);
			KeywordModel keywordModel = (KeywordModel) indexableMap.get(roiModel.getKeywordId());
		    row=sheet.createRow(sheet.getLastRowNum() + 1);
			Cell cell = row.createCell(0);
			cell.setCellValue(Tools.getNormalDateString(roiModel.getCreateDate()));
			cell = row.createCell(1);
			cell.setCellValue(roiModel.getAccountName());
			cell = row.createCell(2);
			cell.setCellValue(roiModel.getPlanName());
			cell = row.createCell(3);
			cell.setCellValue(roiModel.getUnitName());
			cell = row.createCell(4);
			cell.setCellValue(roiModel.getKeyword());
			cell = row.createCell(5);
			if (!Tools.empty(keywordModel)) {
				cell.setCellValue(keywordModel.getPrice());//
				cell = row.createCell(6);
				cell.setCellValue(keywordModel.getLinkUrl());
				cell = row.createCell(7);
				cell.setCellValue(Constants.matchTypeMap.get(keywordModel.getMatchType()));
				cell = row.createCell(8);
				cell.setCellValue(Constants.pauseMap.get(String.valueOf(keywordModel.getPause())));
				cell = row.createCell(9);
				cell.setCellValue(Constants.statusMap.get(keywordModel.getStatus()));
				cell = row.createCell(10);
				cell.setCellValue(Constants.qualityMap.get(String.valueOf(keywordModel.getQuality())));
				cell = row.createCell(11);
			}
			cell.setCellValue(roiModel.getPlatform());
			cell = row.createCell(12);
			cell.setCellValue(roiModel.getImpression());
			cell = row.createCell(13);
			cell.setCellValue(roiModel.getClick());
			cell = row.createCell(14);
			cell.setCellValue(roiModel.getCost());
			cell = row.createCell(15);
			cell.setCellValue(roiModel.getCtr());
			cell = row.createCell(16);
			cell.setCellValue(roiModel.getCpc());
			cell = row.createCell(17);
			cell.setCellValue(roiModel.getCpm());
			cell = row.createCell(18);
			cell.setCellValue(roiModel.getAvgRank());
			cell = row.createCell(19);
			cell.setCellValue(roiModel.getConversion());
			
			/**
			 * D_01_us_09F_dx_p01F_09F_121107
			 * D_01_国家_公司_词类_p01F_投放区域_121107
			 * 大区：通过计划名称公司字段判断
			 * 开业年份 ：通过计划名称公司字段判断
			 */
			String planName = roiModel.getPlanName();
			String[] values = planName.split("_");
			cell = row.createCell(20);
			cell.setCellValue(commonInfoMap.get(InfoType.COUNTRY.name().concat("_").concat(values[2])));
			cell = row.createCell(21);
			cell.setCellValue(commonInfoMap.get(InfoType.COMPANY.name().concat("_").concat(values[3])));
			cell = row.createCell(22);
			cell.setCellValue(commonInfoMap.get(InfoType.KEYWORD.name().concat("_").concat(values[4])));
			cell = row.createCell(23);
			cell.setCellValue(commonInfoMap.get(InfoType.AREA.name().concat("_").concat(values[6])));
			cell = row.createCell(24);
			cell.setCellValue(commonInfoMap.get(InfoType.REGION.name().concat("_").concat(values[3])));
			cell = row.createCell(25);
			cell.setCellValue(commonInfoMap.get(InfoType.OPENNING.name().concat("_").concat(values[3])));
		}
		
		OutputStream os= new FileOutputStream(path.concat(fileName));
		os.flush();
		wb.write(os);
		os.close();
		
	}
	
	
	public static Map<String, String> getCommonInfoMap(ICommonInfoService commonInfoService){
		Map<String, String> commonInfoMap = new HashMap<String, String>();
		
		List<CommonInfo> commonInfos = commonInfoService.getAllCommonInfos();
		if (Tools.empty(commonInfos)) {
			return commonInfoMap;
		}
		
		for (CommonInfo commonInfo : commonInfos) {
			commonInfoMap.put(commonInfo.getInfoType().name().concat("_").concat(commonInfo.getKey()), commonInfo.getValue());
		}
		
		return commonInfoMap;
	}

}
