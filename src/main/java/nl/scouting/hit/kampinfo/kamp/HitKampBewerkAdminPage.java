package nl.scouting.hit.kampinfo.kamp;

import nl.scouting.hit.joomla.JoomlaPublished;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Manipuleert de velden in KampInfo » HIT Kamp » Admin.
 */
public class HitKampBewerkAdminPage extends AbstractKampInfoBewerkPage<HitKampBewerkAdminPage> {

    @FindBy(id = "jform_published")
    private WebElement fieldsetGepubliceerd;

    @FindBy(id = "jform_shantiFormuliernummer")
    private WebElement fieldShantiFormuliernummer;

    @FindBy(id = "jform_aantalDeelnemers")
    private WebElement fieldAantalDeelnemers;

    @FindBy(id = "jform_gereserveerd")
    private WebElement fieldGereserveerd;

    @FindBy(id = "jform_aantalSubgroepen")
    private WebElement fieldAantalSubgroepen;

    public HitKampBewerkAdminPage(final WebDriver driver) {
        super(driver);
    }

    public HitKampBewerkAdminPage withGepubliceerd(final JoomlaPublished jaNee) {
        return setRadioButtonByLabelClick(this.fieldsetGepubliceerd, jaNee);
    }

    public HitKampBewerkAdminPage withShantiFormuliernummer(final String formuliernummer) {
        return withShantiFormuliernummer(Integer.parseInt(formuliernummer));
    }

    public HitKampBewerkAdminPage withShantiFormuliernummer(final int formuliernummer) {
        clearAndSendKeys(fieldShantiFormuliernummer, formuliernummer);
        return this;
    }

    public HitKampBewerkAdminPage withAantalDeelnemers(final int aantalDeelnemers) {
        clearAndSendKeys(fieldAantalDeelnemers, aantalDeelnemers);
        return this;
    }

    public HitKampBewerkAdminPage withGereserveerd(final int aantalGereserveerd) {
        clearAndSendKeys(fieldGereserveerd, aantalGereserveerd);
        return this;
    }

    public HitKampBewerkAdminPage withAantalSubgroepen(final int aantalSubgroepen) {
        clearAndSendKeys(fieldAantalSubgroepen, aantalSubgroepen);
        return this;
    }
}