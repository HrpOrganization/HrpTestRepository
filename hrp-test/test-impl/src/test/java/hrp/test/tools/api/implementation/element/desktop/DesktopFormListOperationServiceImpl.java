package hrp.test.tools.api.implementation.element.desktop;

import hrp.test.tools.api.service.element.desktop.DesktopFormListOperationService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class DesktopFormListOperationServiceImpl implements DesktopFormListOperationService {
	/**
	 * 在单元表格内获取二维表单的目标值所对应的元素值
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
	@Override
	public String formListContrastTarget(WebDriver driver, String targetFieldName, String targetCode,
			String contrastTargetFieldName) {
		// 查找元素
		// 获取表头名称
		String targetHeadClassPath = "//div[contains(@class,'x-panel x-component')]"
				+ "/div/div/div/div/div/div[contains(@class,'x-grid3-header')]" + "/div/div/div/table/tbody/tr/td/div"
				+ "/span[contains(@class,'x-component') and (text()='" + targetFieldName + "')]" + "/../..";
		// 获取名称所在class唯一值
		String targetClassCode = driver.findElement(By.xpath(targetHeadClassPath)).getAttribute("class")
				.replaceAll("x-grid3-header", "").replaceAll("x-grid3-hd", "").replaceAll("x-grid3-cell", "")
				.replaceAll("x-grid3-td-", "").replaceAll("\\s", "");
		// 获取目标元素
		String targetClassPath = "//div[contains(@class,'x-panel x-component')]"
				+ "/div/div/div/div/div/div[contains(@class,'x-grid3-scroller')]"
				+ "/div/div/table/tbody/tr/td[contains(@class,'" + targetClassCode + "')]" + "/div[contains(@class,'"
				+ targetClassCode + "') and (text()='" + targetCode + "')]";
		// 目标元素
		// 获取表头名称
		String contrastTargetHeadClassPath = "//div[contains(@class,'x-panel x-component')]"
				+ "/div/div/div/div/div/div[contains(@class,'x-grid3-header')]" + "/div/div/div/table/tbody/tr/td/div"
				+ "/span[contains(@class,'x-component') and (text()='" + contrastTargetFieldName + "')]" + "/../..";
		// 获取名称所在class唯一值
		String contrastTargetClassCode = driver.findElement(By.xpath(contrastTargetHeadClassPath)).getAttribute("class")
				.replaceAll("x-grid3-header", "").replaceAll("x-grid3-hd", "").replaceAll("x-grid3-cell", "")
				.replaceAll("x-grid3-td-", "").replaceAll("\\s", "");
		// 获取目标元素
		String contrastTargetClassPath = "//div[contains(@class,'x-panel x-component')]"
				+ "/div/div/div/div/div/div[contains(@class,'x-grid3-scroller')]"
				+ "/div/div/table/tbody/tr/td[contains(@class,'" + targetClassCode + "')]" + "/div[contains(@class,'"
				+ targetClassCode + "') and (text()='" + targetCode + "')]" + "/../.." + "/td[contains(@class,'"
				+ contrastTargetClassCode + "')]/div";
		String targetValue = driver.findElement(By.xpath(contrastTargetClassPath)).getText();
		String replaceValue = targetValue.replace(",", "");
		System.out.println(contrastTargetFieldName + ":" + targetValue + "(对比表值)");
		return replaceValue;
	}

	/**
	 * 在单元表格内点击二维表单的一个目标值
	 *
	 * @param driver
	 *            固定参数
	 * @param targetFieldName
	 *            填写内容所在列名
	 * @param targetCode
	 *            目标值（主键）
	 * @return 对比目标表头名的相应值
	 */
	@Override
	public void formListClickTarget(WebDriver driver, String targetFieldName, String targetCode) {
		// 查找元素
		// 获取表头名称
		String targetHeadClassPath = "//div[contains(@class,'x-panel x-component')]"
				+ "/div/div/div/div/div/div[contains(@class,'x-grid3-header')]" + "/div/div/div/table/tbody/tr/td/div"
				+ "/span[contains(@class,'x-component') and (text()='" + targetFieldName + "')]" + "/../..";
		// 获取名称所在class唯一值
		String targetClassCode = driver.findElement(By.xpath(targetHeadClassPath)).getAttribute("class")
				.replaceAll("x-grid3-header", "").replaceAll("x-grid3-hd", "").replaceAll("x-grid3-cell", "")
				.replaceAll("x-grid3-td-", "").replaceAll("\\s", "");
		// 获取目标元素
		String targetClassPath = "//div[contains(@class,'x-panel x-component')]"
				+ "/div/div/div/div/div/div[contains(@class,'x-grid3-scroller')]"
				+ "/div/div/table/tbody/tr/td[contains(@class,'" + targetClassCode + "')]" + "/div[contains(@class,'"
				+ targetClassCode + "') and (text()='" + targetCode + "')]";
		String targetValue = driver.findElement(By.xpath(targetClassPath)).getText();
		System.out.println("click:" + targetValue);
		driver.findElement(By.xpath(targetClassPath)).click();
	}

}
