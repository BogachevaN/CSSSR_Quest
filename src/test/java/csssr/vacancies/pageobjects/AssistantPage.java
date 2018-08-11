package csssr.vacancies.pageobjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;

/**
 * Created by Пользователь on 09.08.2018.
 */
public class AssistantPage extends BasePage {

    @FindBy(css = "div.wrap") private SelenideElement wrap;
    @FindBy (xpath = "//section[@class='graphs']/div") private ElementsCollection graphs;
    @FindBy (xpath = "//section[@class='info']/div") private ElementsCollection info;
    @FindBy (linkText = "vk.com/csssr") private SelenideElement vkLink;
    @FindBy (linkText = "hr@csssr.com") private SelenideElement emailLink;

    public SelenideElement getScrnLink() {
        return scrnLink;
    }

    @FindBy (linkText = "Софт для быстрого создания скриншотов") private SelenideElement scrnLink;

    public ElementsCollection getInfo() {
        return info;
    }

    public AssistantPage selectTabByNum(int i){
        graphs.get(i).find(By.xpath("a")).click();
        return this;
    }


    public ElementsCollection getGraphs() {
        return graphs;
    }

    private String urlAssistPage;

    public String getUrlAssistPage() {
        return urlAssistPage;
    }

    {
        try {

            urlAssistPage =getUrlPages().getProperty("assistantPage.url");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getWidthWrap() {
        String width = wrap.getCssValue("width");
        return width;
    }

    public void clickVkLink() {
        vkLink.click();
    }

    public SelenideElement getWrap() {
        return wrap;
    }

    public SelenideElement getVkLink() {
        return vkLink;
    }

    public SelenideElement getEmailLink() {
        return emailLink;
    }

    public void clickEmailLink() {
        emailLink.click();
    }
}
