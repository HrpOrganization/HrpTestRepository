package hrp.test.tools.api.service.element.desktop;

import org.openqa.selenium.WebDriver;

public interface DesktopInputBoxElementService {
    /**
     * 所在名称右侧输入框内输入值
     *
     * @param driver         固定参数
     * @param fieldName      左侧名称
     * @param writeSomething 需要写入的值
     * @throws Exception 使用Thread
     */
    public void fieldWrite(WebDriver driver, String fieldName, String writeSomething) throws Exception;

    /**
     * 所在名称右侧输入框内输入值（并回车）
     *
     * @param driver         固定参数
     * @param fieldName      左侧名称
     * @param writeSomething 需要写入的值
     * @throws Exception 使用Thread
     */
    public void fieldWriteEnter(WebDriver driver, String fieldName, String writeSomething) throws Exception;

    /**
     * 在空白输入框内输入值
     *
     * @param driver         固定参数
     * @param writeSomething 需要写入的值
     * @throws Exception 使用Thread
     */
    public void nullFieldWrite(WebDriver driver, String writeSomething, String styleCode) throws Exception;
}
