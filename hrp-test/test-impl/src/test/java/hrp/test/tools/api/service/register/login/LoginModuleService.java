package hrp.test.tools.api.service.register.login;

import org.openqa.selenium.WebDriver;

public interface LoginModuleService {

	/**
	 * 系统登录参数
	 *
	 * @param driver
	 *            固定参数
	 * @param baseUrl
	 *            模块登录地址
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param loginDepartment
	 *            登录科室
	 * @return driver
	 * @throws Exception
	 *             使用Tread
	 */
	public WebDriver loginModule(WebDriver driver, String baseUrl, String username, String password,
			String loginDepartment) throws Exception;

}
