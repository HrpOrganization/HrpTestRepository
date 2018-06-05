package hrp.test.tools.api.service.element.window;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public interface WindowTargetElementService {
	/**
	 * 选取目标
	 *
	 * @param driver
	 *            固定参数
	 * @param targetClassCode
	 *            选取列的所在class
	 * @param tagetCode
	 *            查询列的所在class下的code（必须为唯一值）
	 */
	public void clickTarget(WebDriver driver, int windowLevel, String windowName, String targetClassCode,
			String tagetCode);

	/**
	 * 确定元素是否存在于页面
	 *
	 * @param driver
	 *            固定参数
	 * @param selector
	 *            元素
	 * @return 是否存在
	 */
	public boolean doesWebElementExist(WebDriver driver, int windowLevel, String windowName, By selector);

	/**
	 * 获取一行列表中指定元素的其他元素
	 *
	 * @param driver
	 *            固定参数
	 * @param inTargetClassCode
	 *            查询列的所在class
	 * @param tagetCode
	 *            查询列的所在class下的code（必须为唯一值）
	 * @param outTargetClassCode
	 *            想获取的该列的其他值的所在class
	 * @return 回传目标参数值
	 */

}
