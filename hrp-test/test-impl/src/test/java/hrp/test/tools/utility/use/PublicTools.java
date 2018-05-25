package hrp.test.tools.utility.use;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.jws.soap.SOAPBinding;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class PublicTools {
	/**
	 * 查询系统当前时间
	 *
	 * @return 按格式输出的系统当前时间
	 */
	public static String getSystemTime() {
		Date date = new Date();
		SimpleDateFormat getSystemDateValue = new SimpleDateFormat("yyyyMMddhhmmss");
		String getSystemTime = getSystemDateValue.format(date);
		return getSystemTime;
	}

	/**
	 * 使用TryCatch后传出快照
	 *
	 * @param driver
	 *            固定参数
	 * @param e
	 *            Exception
	 * @throws IOException
	 *             使用file
	 */
	public static void screenshot(WebDriver driver, Exception e, String failPathName) throws IOException {
		// 获取本地工程目录
		String cyrPatn = System.getProperty("user.dir") + "/../..";
		// String cyrPatn = "hrp-test";

		// 保存时报错控制台抛异常
		System.out.println("======Exception Reason======\n" + e);
		String getSystemTime = PublicTools.getSystemTime();
		// 并保存页面图片
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		System.out.println(getSystemTime);
		FileUtils.copyFile(srcFile, new File(
				cyrPatn + "\\test-result\\screenshot\\" + failPathName + PublicTools.getSystemTime() + ".png"));
	}

	/**
	 * 处理StringBuffer增加","
	 *
	 * @param strings
	 *            需要处理的对象
	 * @return 增加","
	 */

	public static String bufferPlus(String... strings) {
		StringBuffer comma = new StringBuffer(",");
		StringBuffer result = new StringBuffer();
		for (String string : strings) {
			result.append(comma).append(string);
		}
		String resultString = result.substring(1, result.length());
		return resultString;
	}

	/**
	 * 自动生成多位随机数(唯一编码)
	 *
	 * @param specialCode
	 *            需要在随机数前增加的特殊标识（可为空）
	 * @param randomNumber
	 *            需要生成的位数
	 * @return 返回生成的多位数（String）
	 */
	public static String getRandomValue(String specialCode, int randomNumber) {
		StringBuffer math = new StringBuffer(specialCode);
		String randomMath;
		for (int i = 0; i < randomNumber - 1; i++) {
			randomMath = String.valueOf((int) (Math.random() * 10));
			math.append(randomMath);
		}
		System.out.println(math);
		return new String(math);
	}

	/**
	 * 自动生成多位随机金额
	 *
	 * @param randomNumber
	 *            需要生成的位数(不含小数位)
	 * @return 返回生成的多位数（String）
	 */
	public static String getRandomMoney(int randomNumber) {
		Random rd = new Random();
		StringBuffer math = new StringBuffer("");
		String randomMath;
		for (int i = 1; i < randomNumber + 1; i++) {
			randomMath = String.valueOf(rd.nextInt(10));
			math.insert(0, randomMath);
			// if (i % 3 == 0) {
			// math.insert(0, ",");
			// }
		}
		String selectString = String.valueOf(math);
		// 开头不能为","
		if (selectString.startsWith(",")) {
			math.delete(0, 1);
		}
		// 开头不能为0
		selectString = String.valueOf(math);
		if (selectString.startsWith("0")) {
			String replaceMath = String.valueOf(rd.nextInt(9) + 1);
			math.replace(0, 1, replaceMath);
		}
		// 处理角、分
		math.append(".");
		for (int n = 0; n < 2; n++) {
			randomMath = String.valueOf(rd.nextInt(10));
			math.append(randomMath);
		}
		System.out.println(math);
		return new String(math);
	}

}
