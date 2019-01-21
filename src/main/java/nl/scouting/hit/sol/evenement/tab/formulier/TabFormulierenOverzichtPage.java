package nl.scouting.hit.sol.evenement.tab.formulier;

import nl.scouting.hit.kampinfo.export.KampInfoFormulierExportRegel;
import nl.scouting.hit.sol.evenement.AbstractEvenementPage;
import nl.scouting.hit.sol.evenement.tab.formulier.nieuw.FormulierSoortAanmeldingNieuwPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.FormulierWijzigBasisPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Toont de lijst met formulieren van een evenement.
 */
public class TabFormulierenOverzichtPage extends AbstractEvenementPage {

    @FindBy(linkText = "Formulier toevoegen")
    private WebElement buttonFormulierToevoegen;

    public TabFormulierenOverzichtPage(final WebDriver driver) {
        super(driver);
    }

    public FormulierSoortAanmeldingNieuwPage toevoegenFormulier() {
        scrollIntoViewAndClick(buttonFormulierToevoegen);
        return new FormulierSoortAanmeldingNieuwPage(driver);
    }

    public boolean hasFormulier(final KampInfoFormulierExportRegel regel) {
        return hasFormulier(regel.getFormulierNaam()) ||
                hasFormulierMetKampInfoID(regel);
    }

    public boolean hasFormulier(final String formulierNaam) {
        return !driver.findElements(getLocatorFormulierNaam(formulierNaam)).isEmpty();
    }

    private boolean hasFormulierMetKampInfoID(final KampInfoFormulierExportRegel regel) {
        return !driver.findElements(getLocatorKampID(regel)).isEmpty();
    }

    private By getLocatorKampID(final KampInfoFormulierExportRegel regel) {
        return By.partialLinkText(String.format(" (%d)", regel.getKampID()));
    }

    private By getLocatorFormulierNaam(final String formulierNaam) {
        return By.linkText(formulierNaam);
    }

    public FormulierWijzigBasisPage openFormulier(final KampInfoFormulierExportRegel regel) {
        driver.findElement(getLocatorKampID(regel)).click();
        return new FormulierWijzigBasisPage(driver);
    }

    public FormulierWijzigBasisPage openFormulier(final String formulierNaam) {
        driver.findElement(getLocatorFormulierNaam(formulierNaam)).click();
        return new FormulierWijzigBasisPage(driver);
    }

    public List<Formulier> getFormulieren() {
        final List<Formulier> result = new ArrayList<>();
        driver.findElements(By.xpath("//table[@class='filter']/tbody/tr"))
                .forEach(row -> result.add(new Formulier(row)));
        return result;
    }
}
