package hrp.test.tools.api.service.register.login;

import org.openqa.selenium.WebDriver;

public interface StartChromeSettingsService {
	/**
	 * 调用并打开本地浏览器设置
	 *
	 * @param driver
	 *            固定参数
	 * @return driver
	 */
	public WebDriver startChrome(WebDriver driver);

}
