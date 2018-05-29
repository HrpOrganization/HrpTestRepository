package hrp.test.tools.api.implementation.register.login;

import hrp.test.tools.api.service.register.login.StartChromeSettingsService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class StartChromeSettingsServiceImpl implements StartChromeSettingsService {
	/**
	 * 调用并打开本地浏览器设置
	 *
	 * @param driver
	 *            固定参数
	 * @return driver
	 */
	@Override
	public WebDriver startChrome(WebDriver driver) {
		// 设定 chrome启动文件的位置, 若未设定则取默认安装目录的 chrome
		String chromeBin = "C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe";
		System.setProperty("webdriver.chrome.bin", chromeBin);
		// 设定 chrome webdirver 的位置 ,若未设定则从path变量读取
		String chromeDriver = "D:\\java\\selenium\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", chromeDriver);
		// 打开浏览器默认用户配置信息
		ChromeOptions options = new ChromeOptions();
		// 添加谷歌浏览器参数
		options.addArguments("start-maximized");
		options.addArguments("always-authorize-plugins");
		options.addArguments("disable-popup-blocking");
		// 引用谷歌浏览器驱动(并调用用户信息)
		driver = new ChromeDriver(options);
		// 等待浏览器打开
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		// 浏览器最大化
		System.out.println("浏览器初始化配置成功");
		return driver;
	}
}
