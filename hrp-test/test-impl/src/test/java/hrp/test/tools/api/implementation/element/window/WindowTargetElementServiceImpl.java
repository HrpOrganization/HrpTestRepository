package hrp.test.tools.api.implementation.element.window;

import hrp.test.tools.api.service.element.window.WindowTargetElementService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WindowTargetElementServiceImpl implements WindowTargetElementService {

	@Override
	public void clickTarget(WebDriver driver, int windowLevel, String windowName, String targetClassCode,
			String tagetCode) {

	}

	@Override
	public boolean doesWebElementExist(WebDriver driver, int windowLevel, String windowName, By selector) {
		return false;
	}

}
