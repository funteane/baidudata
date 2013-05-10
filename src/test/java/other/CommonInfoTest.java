package other;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cubead.jinjili.common.dao.ICommonService;
import com.cubead.jinjili.domain.model.CommonInfo;
import com.cubead.jinjili.domain.model.CommonInfo.InfoType;
import com.cubead.jinjili.util.Constants;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CommonInfoTest {
	
	@Autowired
	ICommonService commonService;
	
	@Ignore
	@Test
	public void insertInfo() throws Exception{
		InputStream is = new FileInputStream("d:/temp/index/info.xlsx");
		Workbook workbook = WorkbookFactory.create(is);
		Sheet sheet = workbook.getSheetAt(0);
		System.out.println(sheet.getLastRowNum());
		for (int i = 0, rows = sheet.getLastRowNum(); i <= rows; i++) {
			Row row = sheet.getRow(i);
			CommonInfo commonInfo = new CommonInfo();
			commonInfo.setKey(row.getCell(0).getStringCellValue());
			commonInfo.setValue(row.getCell(1).getStringCellValue());
			commonInfo.setInfoType(InfoType.valueOf(row.getCell(2).getStringCellValue()));
			commonService.saveOrupdate(commonInfo);
		}
		
	}
	
	@Test
	public void testPlanName(){
		String planName = "D_01_us_09F_dx_p01F_09F_121107";
		String[] values = planName.split("_");
		for (String string : values) {
			System.out.println(string);
		}
	}
	
	@Test
	public void testFileName(){
		String directoryName = Constants.REPORT_DOWNLOAD_PATH;
		File directory = new File(directoryName);
		String[] fileNames = directory.list();
		for (String string : fileNames) {
			System.out.println(string);
		}
	}

	@Test
	public void testList(){
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 1000; i++) {
			list.add(String.valueOf(i));
		}
		
		for(int i = 0; i < 5; i++){
			System.out.println(list.subList(5 * i, 5 * (i + 1) ));
		}
		
		
	}
}
