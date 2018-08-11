package csssr.vacancies.runners;

import com.codeborne.selenide.WebDriverRunner;
import csssr.vacancies.pageobjects.AssistantPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.open;

/**
 * Created by Пользователь on 09.08.2018.
 */
public class AssistantTests {

    Logger logger = LoggerFactory.getLogger(AssistantTests.class);

    public AssistantPage assistantPage = new AssistantPage();
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void beforRun(){
        //установка размера браузера в максимально возможный для экрана
        WebDriverRunner.getWebDriver().manage().window().maximize();
        System.getProperty("browser");
        //logger.info(System.getProperty("browser"));
        assistantPage = open(assistantPage.getUrlAssistPage(),AssistantPage.class);
    }

    @BeforeMethod
    public void logTestStart (Method m){
        if (!WebDriverRunner.url().equals("http://blog.csssr.ru/qa-engineer/")){
            open(assistantPage.getUrlAssistPage(),AssistantPage.class);
        }
        logger.info("Start " + m.getName());
    }

    //тест проверяет, что основной контент не может быть меньше 1000 и равен 1000
    @Test
    public void testContentWidth(){
        setSizeWindow(999, 1000);
        softAssert.assertEquals(assistantPage.getWidthWrap(),"1000px");
        setSizeWindow(1000, 1000);
        softAssert.assertEquals(assistantPage.getWidthWrap(),"1000px");
        softAssert.assertAll();
    }

    //тест проверяет, что основной контент растягивается до 1024 включительно
    @Test
    public void testFond(){
        setSizeWindow(1024, 1000);
        softAssert.assertEquals(assistantPage.getWidthWrap(),"1024px");

        setSizeWindow(1025,1000);
        softAssert.assertEquals(assistantPage.getWidthWrap(),"1024px");
        softAssert.assertAll();
    }

    //тест проверяет количество обязанностей в блоке, каждая в своей группе
    @Test
    public void testAmountTasks(){
        Assert.assertEquals(assistantPage.getGraphs().size(),4);
    }

    //тест роверяет, что текст внутри рамки исчезает
    @Test
    public void testSwitchTasks() throws InterruptedException {
        for (int i=0;i<assistantPage.getInfo().size();i++){
            if (i!=(assistantPage.getInfo().size()-1)) {
                assistantPage.selectTabByNum(i+1);
                Thread.sleep(1000);
                Assert.assertEquals(assistantPage.getInfo().get(i+1).getCssValue("display"),"block");
                Assert.assertEquals(assistantPage.getInfo().get(i).getCssValue("display"),"none");
            } else {
                assistantPage.selectTabByNum(0);
                Thread.sleep(1000);
                Assert.assertEquals( assistantPage.getInfo().get(0).getCssValue("display"),"block");
                Assert.assertEquals(assistantPage.getInfo().get(i).getCssValue("display"),"none");
            }
        }
    }

    //тест проверяет, что при повторном нажатии на выделенную вкладку, ее вид не меняется
    @Test
    public void testDoubleClickTask() throws InterruptedException {
        assistantPage.getGraphs().get(0).find(By.xpath("a")).doubleClick();
        Thread.sleep(1000);
        Assert.assertEquals(assistantPage.getInfo().get(0).getCssValue("display"),"block");
    }

    //проверяет, что вкладки с процентами не исчезают
    @Test
    public void testVisibleTab() throws InterruptedException {
        for (int i=0;i<assistantPage.getGraphs().size();i++){
            if (i!=(assistantPage.getGraphs().size()-1)) {
                assistantPage.selectTabByNum(i+1);
                Thread.sleep(1000);
                Assert.assertEquals(assistantPage.getGraphs().get(i+1).getCssValue("display"),"block");
                Assert.assertEquals(assistantPage.getGraphs().get(i).getCssValue("display"),"block");
            } else {
                assistantPage.selectTabByNum(0);
                Thread.sleep(1000);
                Assert.assertEquals( assistantPage.getGraphs().get(0).getCssValue("display"),"block");
                Assert.assertEquals(assistantPage.getGraphs().get(i).getCssValue("display"),"block");
            }
        }
    }

    //проверяет, что сслыка на контакт работает
    @Test
    public void testVkLink(){
        assistantPage.clickVkLink();
        Assert.assertEquals(WebDriverRunner.url(),"https://vk.com/csssr");
        open(assistantPage.getUrlAssistPage(),AssistantPage.class);
    }

    //проверяет, что ссылка на почту работает
    //что конкретно произойдет при нажатии на ссылку зависит от настроек конкретного компьютера
    @Test
    public void testEmailLink(){
        assistantPage.clickEmailLink();
    }


    //проверяет ссылку на прогамму для скриншотов
    @Test
    public void testScrnLink(){
        assistantPage.selectTabByNum(1);
        assistantPage.getScrnLink().click();
        WebDriver driver = WebDriverRunner.getWebDriver();
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        Assert.assertEquals(driver.getCurrentUrl(),"http://monosnap.com/");
        driver.switchTo().window(tabs.get(0));
    }

    private void setSizeWindow(int w, int h) {
        Dimension d = new Dimension(1024,1000);
        WebDriverRunner.getWebDriver().manage().window().setSize(d);
    }


    @AfterMethod (alwaysRun = true)
    public void logTestStop(Method m){
        logger.info("Stop " + m.getName());
    }

    @AfterClass (alwaysRun = true)
    public void afterClass(){
        WebDriverRunner.closeWebDriver();
    }
}
