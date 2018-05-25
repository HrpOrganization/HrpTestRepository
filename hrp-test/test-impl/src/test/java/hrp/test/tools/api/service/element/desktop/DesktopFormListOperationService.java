package hrp.test.tools.api.service.element.desktop;

import org.openqa.selenium.WebDriver;

public interface DesktopFormListOperationService {

	/**
	 * 在弹窗内的单元表格下的指定行数的单元框内填写相应的数据并查找对应的值 （一般使用在报账系统内）
	 *
	 * @param driver
	 *            固定参数
	 * @param targetFieldName
	 *            填写内容所在列名
	 * @param targetCode
	 *            目标值（主键）
	 * @param contrastTargetFieldName
	 *            对比目标的表头名
	 * @return 对比目标表头名的相应值
	 */
	public String formListContrastTarget(WebDriver driver, String targetFieldName, String targetCode,
			String contrastTargetFieldName);

	/**
	 * 在弹窗内的单元表格下的指定行数的单元框内填写相应的数据并查找对应的值 （一般使用在报账系统内）
	 *
	 * @param driver
	 *            固定参数
	 * @param targetFieldName
	 *            填写内容所在列名
	 * @param targetCode
	 *            目标值（主键）
	 * @return 对比目标表头名的相应值
	 */
	public void formListClickTarget(WebDriver driver, String targetFieldName, String targetCode);
}
