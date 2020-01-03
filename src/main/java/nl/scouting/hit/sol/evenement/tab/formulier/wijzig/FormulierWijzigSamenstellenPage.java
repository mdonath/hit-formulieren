package nl.scouting.hit.sol.evenement.tab.formulier.wijzig;

import nl.scouting.hit.common.Valuable;
import nl.scouting.hit.sol.evenement.tab.formulier.common.AbstractFormulierPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.samenstellen.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FormulierWijzigSamenstellenPage extends AbstractFormulierPage<FormulierWijzigSamenstellenPage> {

    public enum NieuwVeld implements Valuable {
        TEKSTREGEL(1, "tekstveld (één regel)"),
        NUMERIEK(2, "numeriek veld"),
        TEKSTREGELS(3, "tekstveld (meerdere regels)"),
        DROPDOWN(4, "treklijst (dropdownbox)"),
        RADIOBUTTON(5, "keuzevelden (radio-button)"),
        CHECKBOX(6, "selectieveld(en) (checkbox)"),
        DATUMVELD(9, "datumveld"),
        TOELICHTING(10, "toelichtende tekst"),
        GROEPSNAAM_INGELOGDE_ROL(11, "groepsnaam van de rol waarmee de deelnemer ingelogd is"),
        MAILADRES(12, "mailadres"),
        GEBRUIKERSROL(13, "rol die deelnemer kan kiezen"),
        KOPTEKST1(14, "koptekst nr 1; vetgedrukt, groot formaat"),
        KOPTEKST2(15, "koptekst nr 2; vetgedrukt, middel formaat"),
        KOPTEKST3(16, "koptekst nr 3; vetgedrukt, normaal formaat"),
        LIJN(19, "lijn"),
        DEELNAMEKOSTEN(20, "deelnamekosten"),
        BETALINGSVELD_MET_AANTAL_OBJECTEN(22, "betalingsveld met op te geven aantal objecten"),
        DEELNEMERGEGEVENS_TE_ZIEN_DOOR_ANDERE_DEELNEMERS(23, "deelnemergegevens te zien door andere deelnemers"),
        BIJLAGEN_TOESTAAN(25, "bijlagen toestaan"),
        TREKLIJST_MET_GEKOPPELDE_TOESLAGEN(34, "treklijst (dropdownbox) met gekoppelde toeslagen"),
        KEUZEVELDEN_MET_GEKOPPELDE_TOESLAGEN(35, "keuzevelden (radio-button) met gekoppelde toeslagen"),
        SELECTIEVELDEN_MET_GEKOPPELDE_TOESLAGEN(36, "selectieveld(en) (checkbox) met gekoppelde toeslagen");

        private final int id;
        private final String type;

        NieuwVeld(int id, String type) {
            this.id = id;
            this.type = type;
        }

        @Override
        public String getId() {
            return String.valueOf(id);
        }

        public static final NieuwVeld byType(String type) {
            for (NieuwVeld veld : NieuwVeld.values()) {
                if (veld.type.equals(type)) {
                    return veld;
                }
            }
            return NieuwVeld.TEKSTREGEL;
        }
    }

    @FindBy(name = "linked_frm_id")
    private WebElement fieldSelecteerFormulier;
    @FindBy(xpath = "//input[@value=\"Gebruik velden van geselecteerd formulier\"]")
    private WebElement buttonGebruikVeldenVanGeselecteerdFormulier;

    @FindBy(name = "fld_type_cd")
    private WebElement fieldSelecteerNieuwVeld;

    @FindBy(xpath = "//*[@id=\"main_content\"]//input[@value ='Toevoegen']")
    private WebElement buttonToevoegen;

    /**
     * Constructor.
     *
     * @param driver
     */
    public FormulierWijzigSamenstellenPage(WebDriver driver) {
        super(driver);
    }

    public boolean isGekoppeldAanFormulier(String gekoppeldFormulierNaam) {
        return !driver.findElements(By.linkText(gekoppeldFormulierNaam)).isEmpty();
    }

    public boolean hasVeld(String veldnaam) {
        return !driver.findElements(By.linkText(veldnaam)).isEmpty();
    }

    public FormulierWijzigSamenstellenPage setFieldSelecteerFormulier(String basisformulier) {
        selectByVisibleText(fieldSelecteerFormulier, basisformulier);
        return this;
    }

    public FormulierWijzigSamenstellenPage gebruikVeldenVanGeselecteerdFormulier() {
        scrollIntoViewAndClick(buttonGebruikVeldenVanGeselecteerdFormulier);
        return this;
    }

    public FormulierWijzigSamenstellenPage withSelecteerNieuwVeld(final NieuwVeld nieuwVeld) {
        selectByValue(fieldSelecteerNieuwVeld, nieuwVeld.getId());
        return this;
    }

    public AbstractVeldWijzigen<?> toevoegen() {
        scrollIntoViewAndClick(buttonToevoegen);
        return bepaalVeldPage();
    }

    public AbstractVeldWijzigen<?> selecteerVeld(final String veldnaam) {
        driver.findElement(By.linkText(veldnaam)).click();
        return bepaalVeldPage();
    }

    private AbstractVeldWijzigen<?> bepaalVeldPage() {
        switch (bepaalVeldType()) {
            case DEELNAMEKOSTEN:
                return new DeelnamekostenWijzigen(driver);
            case CHECKBOX:
                return new CheckboxWijzigen(driver);
            case TEKSTREGELS:
                return new MeerdereTekstRegelsWijzigen(driver);
            case KOPTEKST1:
            case KOPTEKST2:
            case KOPTEKST3:
                return new KoptekstWijzigen(driver);
            case TOELICHTING:
                return new ToelichtendeTekstWijzigen(driver);
            case RADIOBUTTON:
                return new RadioButtonWijzigen(driver);
            default:
                throw new IllegalArgumentException("Onbekend of (nog) niet ondersteund veldtype");
        }
    }

    private NieuwVeld bepaalVeldType() {
        final WebElement veldtypeElement = driver.findElement(By.xpath("//label[contains(text(), 'Veldtype')]/../following-sibling::div/div/span"));
        return NieuwVeld.byType(veldtypeElement.getText());
    }
}
