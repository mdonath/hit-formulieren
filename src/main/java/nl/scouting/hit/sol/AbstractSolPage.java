package nl.scouting.hit.sol;

import nl.scouting.hit.common.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class AbstractSolPage<T extends AbstractSolPage> extends AbstractPage<T> {

    protected AbstractSolPage(final WebDriver driver) {
        super(driver);
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
        return driver.findElement(By.xpath("//div[@class=\"notice_msg\"]")).getText();
    }

}
