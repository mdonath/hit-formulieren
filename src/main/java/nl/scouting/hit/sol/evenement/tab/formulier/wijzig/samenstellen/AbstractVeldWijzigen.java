package nl.scouting.hit.sol.evenement.tab.formulier.wijzig.samenstellen;

import nl.scouting.hit.sol.AbstractSolMetHoofdMenuPage;
import nl.scouting.hit.sol.JaNee;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public abstract class AbstractVeldWijzigen<T extends AbstractVeldWijzigen> extends AbstractSolMetHoofdMenuPage<T> {

    @FindBy(name = "move_fld_after_options")
    private WebElement fieldLocatieVanVeld;
    @FindBy(name = "fld_req_yn")
    private List<WebElement> fieldVerplichtVeld;
    @FindBy(name = "fld_req_yn")
    private List<WebElement> fieldZichtbaarVoorGebruiker;
    @FindBy(name = "fld_text_before")
    private WebElement fieldKoptekst;
    @FindBy(name = "fld_help_txt")
    private WebElement fieldHelptekst;

    protected AbstractVeldWijzigen(WebDriver driver) {
        super(driver);
    }

    public T withKoptekst(String s) {
        clearAndSendKeys(fieldKoptekst, s);
        return (T) this;
    }

    public T withHelptekst(String s) {
        clearAndSendKeys(fieldHelptekst, s);
        return (T) this;
    }

    public T withLocatieVanVeld(String s) {
        selectByVisibleText(fieldLocatieVanVeld, s);
        return (T) this;
    }

    public T withVerplichtVeld(final JaNee jaNee) {
        selectRadio(fieldVerplichtVeld, jaNee);
        return (T) this;
    }

    public T withZichtbaarVoorGebruiker(final JaNee jaNee) {
        selectRadio(fieldZichtbaarVoorGebruiker, jaNee);
        return (T) this;
    }
}
