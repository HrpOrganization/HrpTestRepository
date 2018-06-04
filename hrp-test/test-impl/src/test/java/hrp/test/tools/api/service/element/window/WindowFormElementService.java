package hrp.test.tools.api.service.element.window;

import org.openqa.selenium.WebDriver;

public interface WindowFormElementService {
	/**
	 * 在弹窗内的单元表格下的指定行数的单元框内填写相应的数据 （一般使用在报账系统内）
	 *
	 * @param driver
	 *            固定参数
	 * @param windowName
	 *            弹窗名（表头名）
	 * @param rowNumber
	 *            行数
	 * @param targetFieldName
	 *            填写内容所在列名
	 * @param writeSometing
	 *            输入的值
	 * @throws Exception
	 *             使用Tread
	 */
	public void fieldWrite(WebDriver driver, int windowLevel, String windowName, String rowNumber,
						   String targetFieldName, String writeSometing) throws Exception;

	/**
	 * 在弹窗内的单元表格下的指定行数的单元框的下拉列表内选择对应的值 （一般使用在报账系统内）
	 *
	 * @param driver
	 *            固定参数
	 * @param windowName
	 *            弹窗名（表头名）
	 * @param rowNumber
	 *            行数
	 * @param targetFieldName
	 *            填写内容所在列名
	 * @param writeSometing
	 *            输入的值
	 * @throws Exception
	 *             使用Tread
	 */
	public void fieldDownListSelect(WebDriver driver, int windowLevel, String windowName, String rowNumber,
									String targetFieldName, String writeSometing) throws Exception;

	/**
	 * 在弹窗内的单元表格下的指定行数的单元框内填写相应的数据并查找对应的值 （一般使用在报账系统内）
	 *
	 * @param driver
	 *            固定参数
	 * @param windowName
	 *            弹窗名（表头名）
	 * @param rowNumber
	 *            行数
	 * @param targetFieldName
	 *            填写内容所在列名
	 * @param writeSometing
	 *            输入的值
	 * @throws Exception
	 *             使用Tread
	 */
	public void fieldDownListWriteSearch(WebDriver driver, int windowLevel, String windowName, String rowNumber,
										 String targetFieldName, String writeSometing) throws Exception;

	/**
	 * 指定窗口下含有父目录的子项目下填写值
	 *
	 * @param driver
	 *            固定参数
	 * @param windowLevel
	 *            窗口级次
	 * @param windowName
	 *            弹窗名称
	 * @param SearchFatherName
	 *            下拉框内需要选择的名称（父名称，若传空，则直接选择子名称）
	 * @param SearchName
	 *            下拉框内需要选择的名称
	 * @param budgetAmount
	 *            预算金额
	 * @throws Exception
	 *             使用thread
	 */
	public void listFieldCheckButtonWrite(WebDriver driver, int windowLevel, String windowName, String SearchFatherName,
			String SearchName, String budgetAmount) throws Exception;

	/**
	 * 在一个二位表单中，使用多个查询值，来找到指定的填写位置进行填写
	 *
	 * @param driver                 固定参数
	 * @param windowLevel            窗口级次
	 * @param windowName             弹窗名称
	 * @param targetFieldName        目标填写名称
	 * @param writeSometing          目标填写值
	 * @param originTargetFieldCodes 一组或多组查询的参数值
	 * @throws Exception
	 */
	public void guideFieldWrite(WebDriver driver, int windowLevel, String windowName,
								String targetFieldName, String writeSometing,
								String... originTargetFieldCodes) throws Exception;
}
