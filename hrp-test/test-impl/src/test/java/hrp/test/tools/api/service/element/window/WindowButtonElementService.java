package hrp.test.tools.api.service.element.window;

import org.openqa.selenium.WebDriver;

public interface WindowButtonElementService {
	/**
	 * 点击目标按钮
	 *
	 * @param driver
	 *            固定参数
	 * @param windowName
	 *            窗口名称
	 * @param buttonName
	 *            按钮名称
	 */
	public void clickButton(WebDriver driver, int windowLevel, String windowName, String buttonName) throws Exception;

	public void clickDotTarget(WebDriver driver, int windowLevel, String windowName, String targetName)
			throws Exception;
}
