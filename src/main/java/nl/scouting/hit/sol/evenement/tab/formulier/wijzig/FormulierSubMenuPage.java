package nl.scouting.hit.sol.evenement.tab.formulier.wijzig;

import nl.scouting.hit.common.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Kan alle tabs openen uit het submenu van een Formulier.
 */
public class FormulierSubMenuPage extends AbstractPage {

    @FindBy(linkText = "basis")
    private WebElement tabBasis;
    @FindBy(linkText = "deelnamecondities")
    private WebElement tabDeelnamecondities;
    @FindBy(linkText = "financiën")
    private WebElement tabFinancien;
    @FindBy(linkText = "samenstellen")
    private WebElement tabSamenstellen;
    @FindBy(linkText = "voorbeeld formulier")
    private WebElement tabVoorbeeldFormulier;
    @FindBy(linkText = "doelgroep")
    private WebElement tabDoelgroep;
    @FindBy(linkText = "deelnemers")
    private WebElement tabDeelnemers;
    @FindBy(linkText = "subgroepen")
    private WebElement tabSubgroepen;
    @FindBy(linkText = "mail deelnemers")
    private WebElement tabMailDeelnemers;
    @FindBy(linkText = "aanpassen mails")
    private WebElement tabAanpassenMails;
    @FindBy(linkText = "rapporteren")
    private WebElement tabRapporteren;
    @FindBy(linkText = "totalen")
    private WebElement tabTotalen;

    public FormulierSubMenuPage(WebDriver driver) {
        super(driver);
    }

    public FormulierWijzigBasisPage openTabBasis() {
        tabBasis.click();
        return new FormulierWijzigBasisPage(driver);
    }

    public FormulierWijzigDeelnameconditiesPage openTabDeelnamecondities() {
        tabDeelnamecondities.click();
        return new FormulierWijzigDeelnameconditiesPage(driver);
    }

    public FormulierWijzigFinancienPage openTabFinancien() {
        tabFinancien.click();
        return new FormulierWijzigFinancienPage(driver);
    }

    public FormulierWijzigSamenstellenPage openTabSamenstellen() {
        tabSamenstellen.click();
        return new FormulierWijzigSamenstellenPage(driver);
    }

    public FormulierWijzigBasisPage openTabVoorbeeldFormulier() {
        tabVoorbeeldFormulier.click();
        return new FormulierWijzigBasisPage(driver);
    }

    public FormulierWijzigDoelgroepPage openTabDoelgroep() {
        tabDoelgroep.click();
        return new FormulierWijzigDoelgroepPage(driver);
    }

    public FormulierWijzigBasisPage openTabDeelnemers() {
        tabDeelnemers.click();
        return new FormulierWijzigBasisPage(driver);
    }

    public FormulierWijzigSubgroepenPage openTabSubgroepen() {
        tabSubgroepen.click();
        return new FormulierWijzigSubgroepenPage(driver);
    }

    public FormulierWijzigBasisPage openTabMailDeelnemers() {
        tabMailDeelnemers.click();
        return new FormulierWijzigBasisPage(driver);
    }

    public FormulierWijzigAanpassenMailsPage openTabAanpassenMails() {
        tabAanpassenMails.click();
        return new FormulierWijzigAanpassenMailsPage(driver);
    }

    public FormulierWijzigBasisPage openTabRapporteren() {
        tabRapporteren.click();
        return new FormulierWijzigBasisPage(driver);
    }

    public FormulierWijzigBasisPage openTabTotalen() {
        tabTotalen.click();
        return new FormulierWijzigBasisPage(driver);
    }

}
