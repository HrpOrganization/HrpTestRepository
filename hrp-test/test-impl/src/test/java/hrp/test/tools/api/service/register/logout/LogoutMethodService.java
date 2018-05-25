package hrp.test.tools.api.service.register.logout;

import org.openqa.selenium.WebDriver;

public interface LogoutMethodService {
	/**
	 * 退出当前页面（用于页面元素重复搜索不到当前页面元素）
	 *
	 * @param driver
	 *            固定元素
	 * @param pageName
	 *            二级页面名称
	 */
	public void endPage(WebDriver driver, String pageName);

	/**
	 * 用户退出
	 *
	 * @param driver
	 *            固定参数
	 */
	public void userLogout(WebDriver driver);
}
