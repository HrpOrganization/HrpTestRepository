package hrp.test.tools.utility.use;

import org.openqa.selenium.WebDriver;

import java.util.Set;

public class WindowSwitch {
	/**
	 * 切换至当前窗口（暂无需使用）
	 *
	 * @param driver
	 *            固定参数
	 * @param sreachHandles
	 *            当前页面句柄
	 * @throws Exception
	 *             使用Tread
	 */
	public static void screenAlert(WebDriver driver, String sreachHandles) throws Exception {
		// 获取当前页面句柄
		// String sreachHandles = driver.getWindowHandle();
		// 获取所有页面句柄
		Set<String> handles = driver.getWindowHandles();
		for (String handle : handles) {
			if (handle.equals(sreachHandles)) {
				// 切换到弹窗
				driver.switchTo().window(handle);
				Thread.sleep(1000);
			}
		}
	}
}
