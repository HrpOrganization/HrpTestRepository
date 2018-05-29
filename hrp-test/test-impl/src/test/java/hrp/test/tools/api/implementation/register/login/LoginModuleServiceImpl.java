package hrp.test.tools.api.implementation.register.login;

import hrp.test.tools.api.implementation.element.desktop.DesktopTargetElementServiceImpl;
import hrp.test.tools.api.service.register.login.LoginModuleService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginModuleServiceImpl implements LoginModuleService {
	/**
	 * 登录相应模块
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
	@Override
	public WebDriver loginModule(WebDriver driver, String baseUrl, String username, String password,
			String loginDepartment) throws Exception {
		// 登录系统
		driver.get(baseUrl);
		Thread.sleep(1000);
		DesktopTargetElementServiceImpl desktopTargetElementService = new DesktopTargetElementServiceImpl();
		LoginMethodServiceImpl loginMethod = new LoginMethodServiceImpl();
		if (desktopTargetElementService.doesWebElementExist(driver,
				By.xpath("//button[contains(@class,'x-btn-text') and (text()='登录')]"))) {
			// 调用登录模块
			loginMethod.login(driver, username, password);
			Thread.sleep(1000);
			// 选择登录科室
			if (desktopTargetElementService.doesWebElementExist(driver,
					By.xpath("/html/body/div[contains(@class,'x-window x-component')]"
							+ "/div[contains(@class,'x-window-tl')]/div[contains(@class,'x-window-tr')]"
							+ "/div[contains(@class,'x-window-tc')]"
							+ "//span[contains(@class,'x-window-header-text') and (text()='选择工作台')]"))) {
				loginMethod.loginConsole(driver, loginDepartment);
			} else {
				System.out.println("用户已登录科室");
			}
			// 判断是否登录成功
			String text = driver
					.findElement(By.xpath("//div[@class=' xtb-text framework_titlebar_toolbar_item x-component'"
							+ " and contains(text(),'" + username + "')]"))
					.getText();
			String assertname = username + "(" + username + ")";
			Assert.assertEquals(text, assertname);
			System.out.println("登录成功");
		} else {
			System.out.println("用户已登录");
		}
		return driver;
	}
}
