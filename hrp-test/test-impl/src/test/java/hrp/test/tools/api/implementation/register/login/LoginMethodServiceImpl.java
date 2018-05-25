package hrp.test.tools.api.implementation.register.login;

import hrp.test.tools.api.implementation.element.desktop.DesktopButtonElementServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopInputBoxElementServiceImpl;
import hrp.test.tools.api.service.register.login.LoginMethodService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class LoginMethodServiceImpl implements LoginMethodService {
	/**
	 * 用户登录控制台
	 *
	 * @param driver
	 *            固定参数
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @action 使用用户名和密码登录
	 */
	@Override
	public void login(WebDriver driver, String username, String password) throws Exception {
		DesktopInputBoxElementServiceImpl desktopInputBoxElementService = new DesktopInputBoxElementServiceImpl();
		// 输入账号
		desktopInputBoxElementService.fieldWrite(driver, "用户名", username);
		// 输入密码
		desktopInputBoxElementService.fieldWrite(driver, "密　码", password);
		// 点击登录
		DesktopButtonElementServiceImpl desktopButtunElement = new DesktopButtonElementServiceImpl();
		desktopButtunElement.clickButton(driver, "登录");
	}

	/**
	 * 选择登录台选择科室
	 *
	 * @param driver
	 *            固定参数
	 * @param loginname
	 *            登录科室名
	 */
	@Override
	public void loginConsole(WebDriver driver, String loginname) {
		String xpathname = "//*[contains(text(),'" + loginname + "')]";
		// 选择科室（下拉框选择）
		driver.findElement(By.xpath("//img[@class = 'x-form-trigger x-form-trigger-arrow']")).click();
		driver.findElement(By.xpath(xpathname)).click();
		// 登录工作台
		System.out.println("已选择工作台");
		driver.findElement(By.xpath("//button[contains(text(),'确定')]")).click();
	}

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
	@Override
	public void loginPage(WebDriver driver, String loginFirstPageName, String loginSecendPageName) throws Exception {
		// 进入一级菜单
		String firstXpathname = "//div[contains(@class,'x-menubar-item x-component') and (text()='" + loginFirstPageName
				+ "')]";
		driver.findElement(By.xpath(firstXpathname)).click();
		Thread.sleep(500);
		// 进入二级菜单
		String secendXpathname = "//a[contains(@class,'x-menu-item x-component') and (text()='" + loginSecendPageName
				+ "')]";
		driver.findElement(By.xpath(secendXpathname)).click();
		System.out.println("登录页面:" + loginFirstPageName + " → " + loginSecendPageName);
		Thread.sleep(500);
//		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
}
