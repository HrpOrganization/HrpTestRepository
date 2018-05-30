package hrp.test.tools.utility.excel;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ExcelOperation {
    /**
     * 获得Excel2003表格中的数据（使用在@DataProvider中进行调用）
     *
     * @param fileNamePath EXCEL文件名
     * @param sheetName    EXCEL标签名
     * @return EXCEL的所有数据MAP
     * @throws BiffException 使用getWorkbook类
     * @throws IOException   使用File类
     */
    public static Object[][] getExcelData(String fileNamePath, String sheetName, String keyField)
            throws IOException, BiffException {
        // 获取Excel表格地址（只能传入2003版）
        File directory = new File(".");
        String sourceFile = directory.getCanonicalPath() + "\\src\\main\\resources\\" + fileNamePath + ".xls";
        // 读取EXCEL表格位置
        Workbook workbook = Workbook.getWorkbook(new File(sourceFile));
        // 取到目标页签
        Sheet sheet = workbook.getSheet(sheetName);
        // 取值一行
        int rows = sheet.getRows();
        // 取值一列
        int columns = sheet.getColumns();
        // 为了返回值是Object[][],定义一个多行单列的二维数组
//        HashMap<String, String>[][] arrmap = new HashMap[rows - 3][1];
        HashMap<String,String>[][] arrmap = new HashMap[rows - 3][1];
        // 对数组中所有元素hashmap进行初始化
        if (rows > 3) {
            for (int i = 0; i < rows - 3; i++) {
                arrmap[i][0] = new HashMap<>();
            }
        } else {
            System.out.println("excel中没有数据");
        }
        // 获得首行的列名，作为hashmap的key值
        ArrayList<String> arrkey = new ArrayList<String>();
        for (int c = 0; c < columns; c++) {
            String cellvalue = sheet.getCell(c, 2).getContents();
            arrkey.add(cellvalue);
        }
        // 拆分Key
        String[] keyName = keyField.split(",");
        List<String> keyList = Arrays.asList(keyName);
        // 遍历所有的单元格的值添加到hashmap中
        for (int r = 3; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                // 遍历Key，如果有Key输入，则返回Key的坐标
                String cell = arrkey.get(c);
                if (checkKey(cell, keyList)) {
                    int getRowNum = sheet.getCell(c, r).getRow();
                    int getColNum = sheet.getCell(c, r).getColumn();
                    arrmap[r - 3][0].put(cell, getRowNum + "," + getColNum);
                } else {
                    String cellvalue = sheet.getCell(c, r).getContents();
                    // 确认是否分离","
                    arrmap[r - 3][0].put(cell, cellvalue);
                }
            }
        }
        return arrmap;
    }

    /**
     * 遍历列表对比是否值为确定的值（判断工具）
     *
     * @param inputKey 参照值（对比值）
     * @param keys     一组值
     * @return 是否匹配
     */
    private static boolean checkKey(String inputKey, List<String> keys) {
        for (String key : keys) {
            if (inputKey.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 往指定EXCEL表格的指定行列中写入值
     *
     * @param fileNamePath   文件地址后缀
     * @param sheetName      sheet頁名
     * @param coordinate     Excel坐标
     * @param writeSomething 写入的值
     * @return 返回填写值
     * @throws IOException 使用IO流
     */
    public static String setExcelData(String fileNamePath, String sheetName, String coordinate, String writeSomething)
            throws IOException {
        // 获取Excel表格地址（只能传入2003版）
        File directory = new File(".");
        String sourceFile = directory.getCanonicalPath() + "\\src\\main\\resources\\" + fileNamePath + ".xls";
        org.apache.poi.ss.usermodel.Workbook wb = null;
        org.apache.poi.ss.usermodel.Sheet sheet = null;
        String[] TargetCoordinate = coordinate.split(",");
        int row = Integer.valueOf(TargetCoordinate[0]);
        int col = Integer.valueOf(TargetCoordinate[1]);
        wb = new HSSFWorkbook(new FileInputStream(sourceFile));
        sheet = wb.getSheet(sheetName);
        org.apache.poi.ss.usermodel.Cell cell = sheet.getRow(row).getCell(col);
        if (cell == null) {
            sheet.getRow(row).createCell(col).setCellValue(writeSomething);
        } else {
            cell.setCellValue(writeSomething);
        }
        wb.write(new FileOutputStream(sourceFile));
        System.out.println("已成功录入数据至EXCEL:" + writeSomething);
        wb.close();
        return writeSomething;
    }
}
