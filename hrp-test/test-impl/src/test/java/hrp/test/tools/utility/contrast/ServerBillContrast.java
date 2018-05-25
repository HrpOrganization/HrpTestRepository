package hrp.test.tools.utility.contrast;

import hrp.test.tools.api.implementation.element.desktop.DesktopFormListOperationServiceImpl;
import hrp.test.tools.api.implementation.element.desktop.DesktopInputBoxElementServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginMethodServiceImpl;
import hrp.test.tools.api.implementation.register.login.LoginModuleServiceImpl;
import hrp.test.tools.api.service.register.login.LoginMethodService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ServerBillContrast {

	/**
	 * 获取服务计价对应值 经费卡自身使用的时候可以用（经费卡系统自身系统业务没有单据号） 建议区别录入时的个别值来建立Key的唯一性
	 *
	 * @param driver
	 *            固定参数
	 * @param targetFieldName
	 *            服务计价取获取该列的主键表头
	 * @param targetCode
	 *            服务计价取获取该列的主键名
	 * @param contrastTargetFieldName
	 *            需要取值所在列主键的值
	 * @return 需要对比的目标值
	 */

	public static String fcsSeverBillContrast(WebDriver driver, String targetFieldName, String targetCode,
			String contrastTargetFieldName) throws Exception {
		Thread.sleep(500);
		// 登录经费卡系统
		LoginModuleServiceImpl loginModuleService = new LoginModuleServiceImpl();
		loginModuleService.loginModule(driver, "http://192.168.222.227:8080/fcs-portal/", "USER001", "123456", "计算机中心");
		// 统计查询→服务计价查询
		LoginMethodService loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.loginPage(driver, "统计查询", "服务计价查询");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		DesktopFormListOperationServiceImpl desktopFormListOperationService = new DesktopFormListOperationServiceImpl();
		return desktopFormListOperationService.formListContrastTarget(driver, targetFieldName, targetCode,
				contrastTargetFieldName);
	}

	/**
	 * 获取新下预算业务单据号内的金额
	 *
	 * @param driver
	 *            固定参数
	 * @param budgetCode
	 *            所下预算编码
	 * @param targetFieldName
	 *            服务计价取获取该列的主键表头
	 * @param targetCode
	 *            服务计价取获取该列的主键名
	 * @param contrastTargetFieldName
	 *            需要取值所在列主键的值
	 * @return 所下预算金额
	 * @throws Exception
	 *             使用Thread
	 */
	public static String bcsSeverBillContrast(WebDriver driver, String budgetCode, String targetFieldName,
			String targetCode, String contrastTargetFieldName) throws Exception {
		Thread.sleep(500);
		// 登录预算系统
		LoginModuleServiceImpl loginModuleService = new LoginModuleServiceImpl();
		loginModuleService.loginModule(driver, "http://192.168.222.228:8080/bcs-portal/", "USER001", "123456",
				"主院区: 医院预算");
		// 对比预算列表是否正确
		// 登录预算页面
		LoginMethodServiceImpl loginMethodService = new LoginMethodServiceImpl();
		loginMethodService.loginPage(driver, "预算项目管理", "年度预算管理");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		DesktopInputBoxElementServiceImpl desktopInputBoxElement = new DesktopInputBoxElementServiceImpl();
		// 查询相应预算
		desktopInputBoxElement.fieldWrite(driver, "预算编码", budgetCode);
		driver.findElement(By.xpath("//button[@class = 'x-btn-text' and (text() = '查询')]")).click();
		Thread.sleep(1000);
		// 选择相应预算并查看变动记录
		driver.findElement(By.xpath(
				"//div[@class='x-grid3-cell-inner x-grid3-col-budgetCode' " + "and (text()='" + budgetCode + "')]"))
				.click();
		driver.findElement(By.xpath("//button[@class='x-btn-text ' and (text()='查看变动记录')]")).click();
		Thread.sleep(1000);
		// 选择预算执行类别
		driver.findElement(By.xpath("//label[@class='x-form-item-label' and (text()='执行类型:')]"
				+ "/..//img[@class = 'x-form-trigger x-form-trigger-arrow']")).click();
		driver.findElement(By.xpath("//div[@class='x-combo-list-item' and (text() = '预算执行')]")).click();
		// 取到最大业务单据号
		DesktopFormListOperationServiceImpl desktopFormListOperationService = new DesktopFormListOperationServiceImpl();
		return desktopFormListOperationService.formListContrastTarget(driver, targetFieldName, targetCode,
				contrastTargetFieldName);

		//
		// List<Integer> bcsBillList = new ArrayList<>();
		// Thread.sleep(1000);
		// List<WebElement> inputs = driver.findElements
		// (By.xpath("//div[@class='x-grid3-cell-inner x-grid3-col-transcateNo']"));
		// int inputNumber;
		// for (int i = 0; i < inputs.size(); i++) {
		// inputNumber = Integer.valueOf(inputs.get(i).getText());
		// bcsBillList.add(inputNumber);
		// }
		// String transcateNo = String.valueOf(Collections.max(bcsBillList));
		// //取得最大业务单据号的金额
		// String transcateNoCost = driver.findElement
		// (By.xpath("//div[@class='x-grid3-cell-inner x-grid3-col-transcateNo' " +
		// "and (text() = '"transcateNo + "')]/../.." +
		// "//div[@class='x-grid3-cell-inner x-grid3-col-costs']")).getText();
		// driver.findElement(By.xpath("//div[contains(@class,'x-nodrag x-tool-close
		// x-tool x-component')]")).click();
		// return transcateNoCost;
	}
}
