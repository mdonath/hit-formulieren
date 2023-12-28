package nl.scouting.hit.sol.evenement.tab.formulier;

import nl.scouting.hit.kampinfo.export.KampInfoFormulierExportRegel;
import nl.scouting.hit.kampinfo.export.KampInfoKindFormulierExportRegel;
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
    @FindBy(css = ".filter_toggle_text")
    private WebElement filterToggle;

    @FindBy(css = "div#buttonbar div.filter_pagination input.filterFirst")
    private WebElement eerstePagina;
    @FindBy(css = "div#buttonbar div.filter_pagination input.filterPrevious")
    private WebElement vorigePagina;
    @FindBy(css = "div#buttonbar div.filter_pagination input.filterNext")
    private WebElement volgendePagina;
    @FindBy(css = "div#buttonbar div.filter_pagination input.filterLast")
    private WebElement laatstePagina;

    public TabFormulierenOverzichtPage(final WebDriver driver) {
        super(driver);
    }

    public boolean hasPaginering() {
        return !this.driver.findElements(By.className("filter_pagination_text")).isEmpty();
    }

    public FormulierSoortAanmeldingNieuwPage toevoegenFormulier() {
        scrollIntoViewAndClick(buttonFormulierToevoegen);
        return new FormulierSoortAanmeldingNieuwPage(driver);
    }

    public TabFormulierenOverzichtPage eerstePagina() {
        scrollIntoViewAndClick(eerstePagina);
        return this;
    }

    public boolean heeftVolgendePagina() {
        return volgendePagina.isEnabled();
    }

    public TabFormulierenOverzichtPage volgendePagina() {
        scrollIntoViewAndClick(volgendePagina);
        return this;
    }

    public TabFormulierenOverzichtPage vorigePagina() {
        scrollIntoViewAndClick(vorigePagina);
        return this;
    }

    public TabFormulierenOverzichtPage laatstePagina() {
        scrollIntoViewAndClick(laatstePagina);
        return this;
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
        return By.partialLinkText(
                String.format("%s(%d)",
                        regel.getSpecifier(),
                        regel.getKampID())
        );
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
        if (hasPaginering()) {
            while (heeftVolgendePagina()) {
                volgendePagina();
                driver.findElements(By.xpath("//table[@class='filter']/tbody/tr"))
                        .forEach(row -> result.add(new Formulier(row)));
            }
            eerstePagina();
        }
        return result;
    }

    public TabFormulierenOverzichtZoekModusPage zetZoekFilterAan() {
        final String huidigeStaat = filterToggle.getText();
        if ("Toon filter".equals(huidigeStaat)) {
            // zoekbalk is nu verborgen => zet 'm aan
            scrollIntoViewAndClick(filterToggle);
        }
        return new TabFormulierenOverzichtZoekModusPage(driver);
    }

    public TabFormulierenOverzichtPage zetZoekFilterUit() {
        final String huidigeStaat = filterToggle.getText();
        if ("Verberg filter".equals(huidigeStaat)) {
            // zoekbalk is nu zichtbaar => zet 'm uit
            scrollIntoViewAndClick(filterToggle);
        }
        // Was al uit
        return this;
    }

}
