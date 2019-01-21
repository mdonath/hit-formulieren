package nl.scouting.hit.sol.evenement.tab.basis;

import nl.scouting.hit.sol.evenement.AbstractEvenementPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class AbstractTabBasisPage<T extends AbstractTabBasisPage> extends AbstractEvenementPage<T> {

    @FindBy(name = "evt_nm")
    private WebElement fieldNaamEvenement;
    @FindBy(name = "evt_ds")
    private WebElement fieldBeschrijving;
    @FindBy(name = "evt_org_mail")
    private WebElement fieldMailadresOrganisatie;
    @FindBy(name = "evt_finance_mail")
    private WebElement fieldMailadresFinancieleVragen;
    @FindBy(name = "evt_url")
    private WebElement fieldWebsite;
    @FindBy(name = "evt_cevt_id")
    private WebElement fieldCategorie;

    /**
     * Kan alleen bij wijzigen, is niet aanwezig bij nieuw.
     */
    @FindBy(name = "evt_sec_id")
    private WebElement fieldHoortBijSpeleenheid;

    public AbstractTabBasisPage(WebDriver driver) {
        super(driver);
    }

    public T withNaamEvenement(String s) {
        clearAndSendKeys(fieldNaamEvenement, s);
        return (T) this;
    }

    public T withBeschrijving(String s) {
        clearAndSendKeys(fieldBeschrijving, s);
        return (T) this;
    }

    public T withMailadresOrganisatie(String s) {
        clearAndSendKeys(fieldMailadresOrganisatie, s);
        return (T) this;
    }

    public T withMailadresFinancieleVragen(String s) {
        clearAndSendKeys(fieldMailadresFinancieleVragen, s);
        return (T) this;
    }

    public T withWebsite(String s) {
        clearAndSendKeys(fieldWebsite, s);
        return (T) this;
    }

    public T withCategorie(String s) {
        selectByVisibleText(fieldCategorie, s);
        return (T) this;
    }

    public T withHoortBijSpeleenheid(String s) {
        selectByVisibleText(fieldHoortBijSpeleenheid, s);
        return (T) this;
    }
}
