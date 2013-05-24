package other;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Test11 {

	public static void main(String[] args) throws Exception {
		FileInputStream fs = new FileInputStream("d:/workbook.xlsx");
//		POIFSFileSystem ps = new POIFSFileSystem(fs);
		Workbook wb = new XSSFWorkbook(fs);
		Sheet sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(0);
		System.out.println(sheet.getLastRowNum() + "  " + row.getLastCellNum());
		FileOutputStream out = new FileOutputStream("d:/workbook.xlsx");
		row = sheet.createRow((short) (sheet.getLastRowNum() + 1));
		row.createCell((short) 0).setCellValue(22);
		row.createCell((short) 1).setCellValue(11);
		row.createCell((short) 2).setCellValue(11);
		row.createCell((short) 3).setCellValue(11);
		row.createCell((short) 4).setCellValue(11);
		row.createCell((short) 5).setCellValue(11);
		row.createCell((short) 6).setCellValue(11);
		row.createCell((short) 7).setCellValue(11);
		row.createCell((short) 8).setCellValue(11);
		row.createCell((short) 9).setCellValue(33);
		out.flush();
		wb.write(out);
		out.close();
		System.out.println(row.getPhysicalNumberOfCells() + "  "
				+ row.getLastCellNum());
	}
}