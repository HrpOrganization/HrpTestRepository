package hrp.test.tools.utility.contrast;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContrastList {
	/**
	 * 按行数获取框体下的所有信息，方便进行数据对比
	 *
	 * @param driver
	 *            固定参数
	 * @param firstXpathCode
	 *            框体父目录路径
	 * @param thirdXpathCode
	 *            相对父目录下需要取值的同级子目录
	 * @param numberOfBranches
	 *            取值条数
	 * @return 二位列表数据
	 */
	public static List<Map<String, String>> listText(WebDriver driver, String firstXpathCode, String thirdXpathCode,
			int numberOfBranches) {
		// 需要查找的文本值
		String secendXpathCode;
		// 建立一个存放计价类结果的表单
		List<Map<String, String>> gridList = new ArrayList<>();
		for (int i = 1; i <= numberOfBranches; i++) {
			// 参数化表单行数，并放入List中（目前服务计价主键为行数）
			secendXpathCode = "//*[@" + firstXpathCode + "and (text() = '" + i + "')]";
			String xpathCode = secendXpathCode + thirdXpathCode;
			List<WebElement> initialValue = driver.findElements(By.xpath(xpathCode));
			// 将List中的参数赋值到对应的Map中
			Map<String, String> valueMap = new HashMap<>();
			for (WebElement anInitialValue : initialValue) {
				// 选取相应的值，其中name统一去除字段
				String name = anInitialValue.getAttribute("class").replaceAll("x-grid3-cell-inner x-grid3-col-", "");
				String value = anInitialValue.getText();
				valueMap.put(name, value);
			}
			// 将多条Map转化为一条Map[由于循环取值后赋值，导致Map内存储条数为取值条数（一行条数不唯一）]
			gridList.add(valueMap);
		}
		return gridList;
		// 对比Map内的条数值与原值进行对比
		// gridList.get(1).get("accountNo").equals("8001");
	}

	/**
	 * 获取确定存在该集合中的一组值
	 *
	 * @param gridList
	 *            billText()方法中获取的List集合
	 * @param uniqueKey
	 *            获取List集合内的唯一组值的Key
	 * @param uniqueValue
	 *            获取List集合内的唯一组值
	 * @return targetValue 确定的一组Map值，可以通过getValue来对比
	 */
	public static Map contrastResults(List<Map<String, String>> gridList, String uniqueKey, String uniqueValue) {

		Map<String, String> targetMap = new HashMap<>();
		for (Map<String, String> target : gridList) {
			if (target.get(uniqueKey).equals(uniqueValue)) {
				targetMap = target;
			}
		}
		return targetMap;
	}
}
