package hrp.test.tools.api.service.element.desktop;

import org.openqa.selenium.WebDriver;

public interface DesktopFormElementService {
    /**
     * 在弹窗内的单元表格下的指定行数的单元框内填写相应的数据 （一般使用在报账系统内）
     *
     * @param driver          固定参数
     * @param rowNumber       行数
     * @param targetFieldName 填写内容所在列名
     * @param writeSometing   输入的值
     * @throws Exception 使用Tread
     */
    public void fieldWrite(WebDriver driver, String rowNumber, String targetFieldName, String writeSometing)
            throws Exception;

    /**
     * 在弹窗内的单元表格下的指定行数的单元框的下拉列表内选择对应的值 （一般使用在报账系统内）
     *
     * @param driver          固定参数
     * @param rowNumber       行数
     * @param targetFieldName 填写内容所在列名
     * @param writeSometing   输入的值
     * @throws Exception 使用Tread
     */
    public void fieldDownListSelect(WebDriver driver, String rowNumber, String targetFieldName, String writeSometing)
            throws Exception;

    /**
     * 在弹窗内的单元表格下的指定行数的单元框内填写相应的数据并查找对应的值 （一般使用在报账系统内）
     *
     * @param driver          固定参数
     * @param rowNumber       行数
     * @param targetFieldName 填写内容所在列名
     * @param writeSometing   输入的值
     * @throws Exception 使用Tread
     */
    public void fieldDownListWriteSearch(WebDriver driver, String rowNumber, String targetFieldName,
                                         String writeSometing) throws Exception;

    /**
     * 弹窗内分配课题预算金额
     *
     * @param driver          固定参数
     * @param targetFieldName 分配预算表头名称
     * @param listCode        子预算名称
     * @param budgetAmount    分配金额
     * @throws Exception 使用Thread
     */
    public void listFieldCheckButtonWrite(WebDriver driver, String targetFieldName, String listCode, String budgetAmount)
            throws Exception;

    /**
     * 在一个二位表单中，使用多个查询值，来找到指定的填写位置进行填写
     *
     * @param driver                 固定参数
     * @param targetFieldName        目标填写名称
     * @param writeSometing          目标填写值
     * @param originTargetFieldCodes 一组或多组查询的参数值
     * @throws Exception
     */
    public void guideFieldWrite(WebDriver driver, String targetFieldName, String writeSometing,
                                 String... originTargetFieldCodes) throws Exception;
}
