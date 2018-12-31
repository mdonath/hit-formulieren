package nl.scouting.hit.sol.evenement.tab.formulier.wijzig.samenstellen;

import nl.scouting.hit.sol.AbstractSolPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class AbstractVeldWijzigen<T extends AbstractVeldWijzigen> extends AbstractSolPage {

    @FindBy(name = "fld_text_before")
    private WebElement fieldKoptekst;
    @FindBy(name = "fld_help_txt")
    private WebElement fieldHelptekst;

    protected AbstractVeldWijzigen(WebDriver driver) {
        super(driver);
    }

    public T setFieldKoptekst(String s) {
        clearAndSendKeys(fieldKoptekst, s);
        return (T) this;
    }

    public T setFieldHelptekst(String s) {
        clearAndSendKeys(fieldHelptekst, s);
        return (T) this;
    }

}
