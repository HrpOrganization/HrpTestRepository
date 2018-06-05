package hrp.test.tools.api.service.element.window;

import org.openqa.selenium.WebDriver;

public interface WindowInputBoxElementService {
	/**
	 * 弹窗内所在名称右侧输入框内输入值
	 *
	 * @param driver
	 *            固定参数
	 * @param windowName
	 *            弹窗名
	 * @param fieldName
	 *            左侧名称
	 * @param writeSomething
	 *            需要写入的值
	 * @throws Exception
	 *             使用Thread
	 */
	public void fieldWrite(WebDriver driver, int windowLevel, String windowName, String fieldName,
			String writeSomething) throws Exception;

	/**
	 * 弹窗内所在名称右侧输入框内输入值后回车
	 *
	 * @param driver
	 *            固定参数
	 * @param windowName
	 *            弹窗名
	 * @param fieldName
	 *            左侧名称
	 * @param writeSomething
	 *            需要写入的值
	 * @throws Exception
	 *             使用Thread
	 */
	public void fieldWriteEnter(WebDriver driver, int windowLevel, String windowName, String fieldName,
			String writeSomething) throws Exception;

	/**
	 * 在空白输入框内输入值(特殊输入框)
	 *
	 * @param driver
	 *            固定参数
	 * @param windowName
	 *            弹窗名
	 * @param writeSomething
	 *            需要写入的值
	 * @throws Exception
	 *             使用Thread
	 */
	public void nullFieldWrite(WebDriver driver, int windowLevel, String windowName, String styleCode,
			String writeSomething) throws Exception;
}
