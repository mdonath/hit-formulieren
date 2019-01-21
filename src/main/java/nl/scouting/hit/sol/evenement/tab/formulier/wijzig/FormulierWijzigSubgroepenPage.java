package nl.scouting.hit.sol.evenement.tab.formulier.wijzig;

import nl.scouting.hit.sol.evenement.tab.formulier.common.AbstractFormulierPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.subgroepen.FormulierSubgroepenCategorieNieuwPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.subgroepen.FormulierSubgroepenCategorieWijzigPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FormulierWijzigSubgroepenPage extends AbstractFormulierPage<FormulierWijzigSubgroepenPage> {

    //
    // Button
    //
    @FindBy(linkText = "Voeg subgroepcategorie toe")
    private WebElement buttonToevoegenSubgroepCategorie;

    /**
     * Constructor.
     *
     * @param driver
     */
    public FormulierWijzigSubgroepenPage(final WebDriver driver) {
        super(driver);
    }

    public boolean hasSubgroepCategorie(final String s) {
        return !driver.findElements(By.linkText(s)).isEmpty();
    }

    public FormulierSubgroepenCategorieWijzigPage openSubgroepCategorie(final String s) {
        driver.findElement(By.linkText(s)).click();
        return new FormulierSubgroepenCategorieWijzigPage(driver);
    }

    public FormulierSubgroepenCategorieNieuwPage toevoegenSubgroepCategorie() {
        scrollIntoViewAndClick(buttonToevoegenSubgroepCategorie);
        return new FormulierSubgroepenCategorieNieuwPage(driver);
    }

}
