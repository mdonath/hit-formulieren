package nl.scouting.hit.kampinfo.kamp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HitKampBewerkAdminPage extends AbstractKampInfoBewerkPage<HitKampBewerkGegevensPage, HitKampenPage> {

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

    public HitKampBewerkAdminPage setFieldShantiFormuliernummer(String formuliernummer) {
        return setFieldShantiFormuliernummer(Integer.parseInt(formuliernummer));
    }

    public HitKampBewerkAdminPage setFieldShantiFormuliernummer(int formuliernummer) {
        clearAndSendKeys(fieldShantiFormuliernummer, formuliernummer);
        return this;
    }

    public HitKampBewerkAdminPage setFieldAantalDeelnemers(int aantalDeelnemers) {
        clearAndSendKeys(fieldAantalDeelnemers, aantalDeelnemers);
        return this;
    }

    public HitKampBewerkAdminPage setFieldGereserveerd(int aantalGereserveerd) {
        clearAndSendKeys(fieldGereserveerd, aantalGereserveerd);
        return this;
    }

    public HitKampBewerkAdminPage setFieldAantalSubgroepen(int aantalSubgroepen) {
        clearAndSendKeys(fieldAantalSubgroepen, aantalSubgroepen);
        return this;
    }
}