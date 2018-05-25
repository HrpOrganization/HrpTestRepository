package hrp.test.tools.api.service.element.desktop;

import org.openqa.selenium.WebDriver;

public interface DesktopButtonElementService {
	/**
	 * 点击目标按钮
	 *
	 * @param driver
	 *            固定参数
	 * @param buttonName
	 *            按钮名称
	 */
	public void clickButton(WebDriver driver, String buttonName) throws Exception;

	/**
	 *
	 * 点击目标目标
	 *
	 * @param driver
	 *            固定参数
	 * @param buttonName
	 *            按钮名称
	 */
	public void clickDotTarget(WebDriver driver, String buttonName) throws Exception;

	/**
	 * 点击目标按钮（存在重复且不从属于窗口的按钮）
	 *
	 * @param driver
	 *            固定参数
	 * @param buttonName
	 *            按钮名称
	 */
	public void clickNullFieldButton(WebDriver driver, String buttonName) throws Exception;
}
