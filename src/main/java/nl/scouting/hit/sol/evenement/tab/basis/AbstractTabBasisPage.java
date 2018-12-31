package nl.scouting.hit.sol.evenement.tab.basis;

import nl.scouting.hit.sol.evenement.AbstractEvenementPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class AbstractTabBasisPage extends AbstractEvenementPage {

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

    /** Kan alleen bij wijzigen, is niet aanwezig bij nieuw. */
    @FindBy(name = "evt_sec_id")
    private WebElement fieldHoortBijSpeleenheid;


    public AbstractTabBasisPage(WebDriver driver) {
        super(driver);
    }

    public void setFieldNaamEvenement(String s) {
        clearAndSendKeys(fieldNaamEvenement, s);
    }

    public void setFieldBeschrijving(String s) {
        clearAndSendKeys(fieldBeschrijving, s);
    }

    public void setFieldMailadresOrganisatie(String s) {
        clearAndSendKeys(fieldMailadresOrganisatie, s);
    }

    public void setFieldMailadresFinancieleVragen(String s) {
        clearAndSendKeys(fieldMailadresFinancieleVragen, s);
    }

    public void setFieldWebsite(String s) {
        clearAndSendKeys(fieldWebsite, s);
    }

    public void setFieldCategorie(String s) {
        selectByVisibleText(fieldCategorie, s);
    }

    public void setFieldHoortBijSpeleenheid(String s) {
        selectByVisibleText(fieldHoortBijSpeleenheid, s);
    }


}
