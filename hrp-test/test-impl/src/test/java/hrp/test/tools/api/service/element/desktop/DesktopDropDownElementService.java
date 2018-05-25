package hrp.test.tools.api.service.element.desktop;

import org.openqa.selenium.WebDriver;

public interface DesktopDropDownElementService {
	/**
	 * 打开右侧下拉框并选择对应选项
	 *
	 * @param driver
	 *            固定参数
	 * @param fieldName
	 *            下拉框左侧名称
	 * @param listCode
	 *            元素唯一值
	 */
	public void listFieldSelect(WebDriver driver, String fieldName, String listCode) throws Exception;

	/**
	 * 在右侧下拉框内优先填写查询值，然后选择需要的值
	 *
	 * @param driver
	 *            固定参数
	 * @param fieldName
	 *            下拉框左侧名称
	 * @param writeSomething
	 *            需要查询的值
	 * @param listCode
	 *            查询结果内搜索的值
	 * @throws Exception
	 *             使用Tread
	 */

	public void listFieldWriteSearch(WebDriver driver, String fieldName, String writeSomething, String listCode)
			throws Exception;

	/**
	 * 在右侧下拉框内优先填写查询值，直接回车带出相关值(用于TABLE→tr类的下拉框)
	 *
	 * @param driver
	 *            固定参数
	 * @param fieldName
	 *            下拉框左侧名称
	 * @param writeSomething
	 *            需要查询的值
	 * @throws Exception
	 *             使用Tread
	 */
	public void listFieldWriteEnter(WebDriver driver, String fieldName, String writeSomething) throws Exception;

	/**
	 * 多项选择下拉框内的选项
	 *
	 * @param driver
	 *            固定参数
	 * @param windowName
	 *            弹窗名称
	 * @param fieldName
	 *            下拉框左侧名称
	 * @param listCode
	 *            下拉框内需要选择的值（可多选）
	 * @throws Exception
	 *             使用Thread
	 */
	public void listFieldCheckBox(WebDriver driver, String windowName, String fieldName, String... listCode)
			throws Exception;

	/**
	 * 指定窗口下含有父目录的的下拉框内选择
	 *
	 * @param driver
	 *            固定参数
	 * @param fieldName
	 *            下拉框左侧名称
	 * @param SearchFatherName
	 *            下拉框内需要选择的名称（父名称，若传空，则直接选择子名称）
	 * @param SearchName
	 *            下拉框内需要选择的名称
	 * @throws Exception
	 *             使用Thread
	 */
	public void listFieldCheckButtonSelect(WebDriver driver, String fieldName, String SearchFatherName,
			String SearchName) throws Exception;
}
