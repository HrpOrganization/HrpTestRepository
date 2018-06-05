package hrp.test.tools.utility.use;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ElementTools {
	/**
	 * 处理二维度表单的CLASS并从CLASS内取ITEM的名称
	 *
	 * @param driver
	 *            固定参数
	 * @param selector
	 *            查找CLASS的方法及路径
	 * @return 返回ITEM名称
	 */
	public static String getFormListClassItemName(WebDriver driver, By selector) {
		String getClassName = driver.findElement(selector).getAttribute("class").replaceAll("x-grid3-header", "")
				.replaceAll("x-grid3-hd", "").replaceAll("x-grid3-cell", "").replaceAll("x-grid3-td-", "")
				.replaceAll("\\s", "");
		return getClassName;
	}
}
