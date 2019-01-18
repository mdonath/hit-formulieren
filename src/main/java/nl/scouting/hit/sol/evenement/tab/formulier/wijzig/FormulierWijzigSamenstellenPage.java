package nl.scouting.hit.sol.evenement.tab.formulier.wijzig;

import nl.scouting.hit.common.Valuable;
import nl.scouting.hit.sol.evenement.tab.formulier.common.AbstractFormulierPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.samenstellen.AbstractVeldWijzigen;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.samenstellen.DeelnamekostenWijzigen;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FormulierWijzigSamenstellenPage extends AbstractFormulierPage<FormulierWijzigSamenstellenPage> {


    public enum NieuwVeld implements Valuable {
        TEKSTREGEL(1),
        NUMERIEK(2),
        TEKSTREGELS(3),
        DROPDOWN(4),
        RADIOBUTTON(5),
        CHECKBOX(6),
        DATUMVELD(9),
        TOELICHTING(10),
        GROEPSNAAM_INGELOGDE_ROL(11),
        MAILADRES(12),
        GEBRUIKERSROL(13),
        KOPTEKST1(14),
        KOPTEKST2(15),
        KOPTEKST3(16),
        LIJN(19),
        DEELNAMEKOSTEN(20),
        DEELNEMERGEGEVENS_TE_ZIEN_DOOR_ANDERE_DEELNEMERS(23),
        BIJLAGEN_TOESTAAN(25);

        private final int id;

        NieuwVeld(int id) {
            this.id = id;
        }

        @Override
        public String getId() {
            return String.valueOf(id);
        }
    }

    @FindBy(name = "linked_frm_id")
    private WebElement fieldSelecteerFormulier;
    @FindBy(xpath = "//input[@value=\"Gebruik velden van geselecteerd formulier\"]")
    private WebElement buttonGebruikVeldenVanGeselecteerdFormulier;

    @FindBy(name = "fld_type_cd")
    private WebElement fieldSelecteerNieuwVeld;

    @FindBy(xpath = "//*[@id=\"buttonbar\"]/div[1]/input")
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

    public boolean heeftVeld(String veldnaam) {
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

    public FormulierWijzigSamenstellenPage setFieldSelecteerNieuwVeld(NieuwVeld nieuwVeld) {
        selectByValue(fieldSelecteerNieuwVeld, nieuwVeld.getId());
        return this;
    }

    public AbstractVeldWijzigen<?> toevoegen() {
        scrollIntoViewAndClick(buttonToevoegen);
        return bepaalVeldPage();
    }

    public AbstractVeldWijzigen<?> selecteerVeld(String veldnaam) {
        driver.findElement(By.linkText(veldnaam)).click();
        return bepaalVeldPage();
    }

    private AbstractVeldWijzigen<?> bepaalVeldPage() {
        switch (bepaalVeldType()) {
            case "deelnamekosten":
                return new DeelnamekostenWijzigen(driver);
            default:
                throw new IllegalArgumentException("Onbekend of (nog) niet ondersteund veldtype");
        }
    }

    private String bepaalVeldType() {
        final WebElement veldtypeElement = driver.findElement(By.xpath("//label[contains(text(), 'Veldtype')]/../following-sibling::div/div/span"));
        return veldtypeElement.getText();
    }
}
