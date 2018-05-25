package hrp.test.tools.utility.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExcelDataPut {
	private String fieldName = null;
	private String writeSomething = null;

	/**
	 * 写入EXCEL
	 *
	 * @param fileNamePath
	 *            文件路径
	 * @param sheetName
	 *            sheet页码
	 * @return 集合
	 * @throws Exception
	 *             使用IO流
	 */

	public Object[] putExcelData(String fileNamePath, String sheetName) throws Exception {
		// 获取Excel表格地址（只能传入2003版）
		File directory = new File(".");
		String sourceFile = directory.getCanonicalPath() + "\\src\\main\\resources\\" + fileNamePath + ".xls";
		Workbook wb = null;
		Sheet sheet = null;
		wb = new HSSFWorkbook(new FileInputStream(sourceFile));
		sheet = wb.getSheet(sheetName);
		Iterator<Cell> it = sheet.getRow(2).cellIterator();
		int i = 0;
		int rows = 0;
		Map<String, Integer> colMap = new HashMap<String, Integer>();
		while (it.hasNext()) {
			Cell cell = it.next();
			String colName = cell.getStringCellValue();
			colMap.put(colName, i);
			i++;
			rows = i++;
		}
		Cell cell = null;
		for (int r = 3; r <= rows; r++) {
			cell = sheet.getRow(r).getCell(colMap.get(fieldName));
			cell.setCellValue(writeSomething);
		}
		wb.close();
		wb.write(new FileOutputStream(sourceFile));
		return new Object[] { cell };

	}

	public void writeSomething(String fieldName, String writeSomething) {
		this.fieldName = fieldName;
		this.writeSomething = writeSomething;
	}

}
