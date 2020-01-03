package nl.scouting.hit.common;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public abstract class AbstractPage<T extends AbstractPage> {

    protected final WebDriver driver;

    protected AbstractPage(final WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected final void scrollToTop() {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, 0)");
    }

    protected final void scrollToBottom() {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    protected final void scrollToBottomRight() {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(document.body.scrollWidth, document.body.scrollHeight)");
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

    public final void selectByPartialVisibleText(final WebElement element, final String value) {
        final Select select = new Select(element);
        select.getOptions().forEach(option -> {
            if (option.getText().contains(value)) {
                select.selectByVisibleText(option.getText());
            }
        });
    }

    protected final void selectByValue(final WebElement element, final String value) {
        new Select(element).selectByValue(value);
    }

    protected final void selectByValue(final WebElement element, final Valuable value) {
        selectByValue(element, value.getId());
    }

    protected final void selectRadio(final List<WebElement> elements, final Valuable value) {
        scrollIntoView(elements.get(0), false);
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

    protected final WebElement scrollIntoView(final WebElement element, final boolean centered) {
        final String args = centered ? "{behavior:'auto', block:'center', inline:'center'}" : "true";
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(" + args + ");", element);
        return element;
    }

    protected void scrollIntoViewAndClick(final WebElement button) {
        scrollIntoView(button, false);
        button.click();
    }

    protected void scrollIntoViewCenteredAndClick(final WebElement button) {
        scrollIntoView(button, true);
        button.click();
    }

    public void clickLink(final String linkText) {
        driver.findElement(By.linkText(linkText)).click();
    }

    public final T sleep(final long millis) {

        try {
            Thread.sleep(millis);
        } catch (final InterruptedException ignore) {
            // ignore
        }
        return (T) this;
    }

}
