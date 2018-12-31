package nl.scouting.hit.sol;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public abstract class AbstractPage<T extends AbstractPage> implements SolPage {

    protected final WebDriver driver;

    protected AbstractPage(final WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public final T controleerMelding(final String expected) {
        if (!verifyNoticeMessage(expected)) {
            throw new RuntimeException(String.format("Onjuiste melding: '%s', maar verwachtte: '%s'.", logNoticeMessage(), expected));
        }
        return (T) this;
    }

    public final boolean verifyNoticeMessage(final String expected) {
        final String text = driver.findElement(By.xpath("//div[@class=\"notice_msg\"]")).getText();
        return expected.equals(text);
    }

    protected final String logNoticeMessage() {
        final String text = driver.findElement(By.xpath("//div[@class=\"notice_msg\"]")).getText();
        System.out.println(text);
        return text;
    }

    protected final void scrollToBottom() {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    protected final void clearAndSendKeys(final WebElement element, final String value) {
        element.clear();
        element.sendKeys(value);
    }

    protected final void clearAndSendKeys(final WebElement element, final int value) {
        element.clear();
        element.sendKeys(String.valueOf(value));
    }

    protected final void selectByVisibleText(final WebElement element, final String value) {
        new Select(element).selectByVisibleText(value);
    }

    protected final void selectByValue(final WebElement element, final String value) {
        new Select(element).selectByValue(value);
    }

    protected final void selectRadio(final List<WebElement> elements, final Valuable value) {
        scrollIntoView(elements.get(0));
        elements.stream()
                .filter(element -> value.getId().equals(element.getAttribute("value")))
                .findFirst().ifPresent(WebElement::click);
    }

    protected final void setFieldsDatumTijd(final WebElement fieldDag, final int dag, final WebElement fieldMaand, final int maand, final WebElement fieldJaar, final int jaar, final WebElement fieldTijd, final String tijd) {
        selectByValue(fieldDag, String.format("%02d", dag));
        selectByValue(fieldMaand, String.format("%02d", maand));
        selectByValue(fieldJaar, String.format("%04d", jaar));
        clearAndSendKeys(fieldTijd, tijd);
    }

    protected final void setFieldsDatum(final WebElement fieldDag, final int dag, final WebElement fieldMaand, final int maand, final WebElement fieldJaar, final int jaar) {
        selectByValue(fieldDag, String.format("%02d", dag));
        selectByValue(fieldMaand, String.format("%02d", maand));
        selectByValue(fieldJaar, String.format("%04d", jaar));
    }

    protected final void openklappenMenu(final String naam) {
        activeerElement(driver.findElement(By.partialLinkText(naam)));
    }

    protected final void activeerElement(final WebElement element) {
        new Actions(driver).moveToElement(element).perform();
    }

    protected final void scrollIntoView(final WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void scrollIntoViewAndClick(final WebElement button) {
        scrollIntoView(button);
        button.click();
    }

}
