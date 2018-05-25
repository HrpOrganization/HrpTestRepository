package hrp.test.tools.api.service.register.login;

import org.openqa.selenium.WebDriver;

public interface LoginMethodService {
	/**
	 * 用户登录
	 *
	 * @param driver
	 *            固定参数
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @action 使用用户名和密码登录
	 */
	public void login(WebDriver driver, String username, String password) throws Exception;

	/**
	 * 登录台选择科室
	 *
	 * @param driver
	 *            固定参数
	 * @param loginname
	 *            登录科室名
	 */
	public void loginConsole(WebDriver driver, String loginname);

	/**
	 * 登录指定页面
	 *
	 * @param driver
	 *            固定参数
	 * @param loginFirstPageName
	 *            一级菜单名
	 * @param loginSecendPageName
	 *            二级菜单名
	 */
	public void loginPage(WebDriver driver, String loginFirstPageName, String loginSecendPageName) throws Exception;
}
