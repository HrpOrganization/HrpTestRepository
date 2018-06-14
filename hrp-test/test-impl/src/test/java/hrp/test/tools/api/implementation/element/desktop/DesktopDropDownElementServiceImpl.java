package hrp.test.tools.api.implementation.element.desktop;

import hrp.test.tools.api.service.element.desktop.DesktopDropDownElementService;
import org.openqa.selenium.*;

public class DesktopDropDownElementServiceImpl implements DesktopDropDownElementService {

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
	@Override
	public void listFieldSelect(WebDriver driver, String fieldName, String listCode) throws Exception {
		// 打开下拉框
		String inputFieldNamePath = "//label[contains(@class,'x-form-item-label') and (text()='" + fieldName + ":')]"
				+ "/..//img[contains(@class,'x-form-trigger x-form-trigger-arrow')]";
		DesktopTargetElementServiceImpl desktopTargetElementService = new DesktopTargetElementServiceImpl();
		if (desktopTargetElementService.doesWebElementExist(driver, By.xpath(inputFieldNamePath))) {
		} else {
			inputFieldNamePath = "//div[contains(@class,'x-panel-tbar x-panel-tbar-noheader')]"
					+ "//div[contains(@class,'x-form-label x-component') and contains(text(),'" + fieldName + "')]"
					+ "/.." + "/following-sibling::td[1]"
					+ "//img[contains(@class,'x-form-trigger x-form-trigger-arrow')]";
		}
		driver.findElement(By.xpath(inputFieldNamePath)).click();
		Thread.sleep(500);
		// 选择对应选项
		System.out.println(fieldName + ":" + listCode + "（下拉框选择）");
		String budget = "/html/body/div[contains(@class,'x-combo-list x-ignore x-component x-border')]"
				+ "//div[contains(@class,'x-combo-list-item') and (text()='" + listCode + "')]";
		driver.findElement(By.xpath(budget)).click();
		Thread.sleep(500);
	}

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
	@Override
	public void listFieldWriteSearch(WebDriver driver, String fieldName, String writeSomething, String listCode)
			throws Exception {
		// 查询结果
		String inputFieldNamePath = "//label[contains(@class ,'x-form-item-label') and contains(text(),'" + fieldName
				+ ":')]" + "/.." + "//*[contains(@class,'x-form-field x-form-text')]";
		String listButtunPath = "//label[contains(@class ,'x-form-item-label') and contains(text(),'" + fieldName
				+ ":')]" + "/.." + "//img[contains(@class,'x-form-trigger')]";
		driver.findElement(By.xpath(inputFieldNamePath)).clear();
		try {
			driver.findElement(By.xpath(listButtunPath)).click();
			Thread.sleep(1000);
		} catch (ElementNotVisibleException e) {
		} finally {
			System.out.println(fieldName + ":" + writeSomething + "（输入）");
			driver.findElement(By.xpath(inputFieldNamePath)).sendKeys(writeSomething);
			System.out.println(fieldName + ":" + listCode + "（下拉框选择）");
			// 选择对应查询结果
			String targetPath = "/html/body/div[contains(@class,'x-combo-list x-ignore x-component x-border')]"
					+ "//div[contains(@class,'x-grid3-cell-inner') and (text()='" + listCode + "')]";
			DesktopTargetElementServiceImpl desktopTargetElementService = new DesktopTargetElementServiceImpl();
			if (!desktopTargetElementService.doesWebElementExist(driver, By.xpath(targetPath))) {
				targetPath = "/html/body/div[contains(@class,'x-combo-list x-ignore x-component x-border')]"
						+ "//span[contains(@class,'x-tree3-node-text') and (text()='" + listCode + "')]";
			}
			Thread.sleep(1000);
			driver.findElement(By.xpath(targetPath)).click();
		}
		Thread.sleep(500);
	}

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
	@Override
	public void listFieldWriteEnter(WebDriver driver, String fieldName, String writeSomething) throws Exception {
		// 查询结果
		String inputFieldNamePath = "label[contains(@class ,'x-form-item-label') and contains(text(),'" + fieldName
				+ ":')]" + "/..//*[contains(@class,'x-form-field x-form-text')]";
		WebElement element = driver.findElement(By.xpath(inputFieldNamePath));
		element.clear();
		System.out.println(fieldName + ":" + writeSomething + "（输入）");
		element.sendKeys(writeSomething);
		Thread.sleep(1000);
		element.sendKeys(Keys.ENTER);
		Thread.sleep(500);

	}

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
	@Override
	public void listFieldCheckBox(WebDriver driver, String windowName, String fieldName, String... listCode)
			throws Exception {
		// 打开下拉框
		String inputFieldNamePath = "//label[contains(@class,'x-form-item-label') and contains(text(),'" + fieldName
				+ ":')]" + "/.." + "//img[contains(@class,'x-form-trigger x-form-trigger-arrow')]";
		driver.findElement(By.xpath(inputFieldNamePath)).click();
		String targetPath;
		// 选择对应选项
		for (String listCodes : listCode) {
			System.out.println(fieldName + ":" + listCodes);
			targetPath = "/html/body/div[contains(@class,'x-combo-list x-ignore x-component x-border')]"
					+ "//td[text()='" + listCodes + "']" + "/..//input[@type='checkbox']";
			System.out.println(fieldName + ":" + listCodes + "（下拉框选择）");
			driver.findElement(By.xpath(targetPath)).click();
			Thread.sleep(500);
		}
	}

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
	 *             使用thread
	 */
	@Override
	public void listFieldCheckButtonSelect(WebDriver driver, String fieldName, String SearchFatherName,
			String SearchName) throws Exception {
		String inputFieldNamePath = "//label[contains(@class,'x-form-item-label') and contains(text(),'" + fieldName
				+ ":')]" + "/.." + "//img[contains(@class,'x-form-trigger x-form-trigger-arrow')]";
		driver.findElement(By.xpath(inputFieldNamePath)).click();
		if (!SearchFatherName.equals("")) {
			System.out.println(fieldName + "（父目录）:" + SearchFatherName);
			String fatherButtonPath = "/html/body/div[contains(@class,'x-combo-list x-ignore x-component x-border')]"
					+ "//span[contains(@class ,'x-tree3-node-text') and (text() = '" + SearchFatherName + "')]" + "/.."
					+ "/img[contains(@class,'x-tree3-node-joint')]";
			driver.findElement(By.xpath(fatherButtonPath)).getAttribute("onload");
			driver.findElement(By.xpath(fatherButtonPath)).click();
			Thread.sleep(500);
		}
		System.out.println(fieldName + "（子项）:" + SearchName);
		String targetPath = "/html/body/div[contains(@class,'x-combo-list x-ignore x-component x-border')]"
				+ "//span[contains(@class ,'x-tree3-node-text') and (text() = '" + SearchName + "')]";
		driver.findElement(By.xpath(targetPath)).click();
		Thread.sleep(500);
	}

}
