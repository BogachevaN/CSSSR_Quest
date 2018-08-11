package csssr.vacancies.pageobjects;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.open;

/**
 * Created by Пользователь on 09.08.2018.
 */
public class BasePage {

    public Properties getUrlPages () throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("./src/test/resources/urlPages.properties");
        prop.load(fis);
        return prop;
    }

    public void openPage(String url) {
        open(url);
    }

    protected void setSystemProp(String browser) {
        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
            Configuration.browser = browser;
        }
        //установка размера браузера в максимально возможный для экрана
        WebDriverRunner.getWebDriver().manage().window().maximize();
        Configuration.timeout = 6000;
    }
}
