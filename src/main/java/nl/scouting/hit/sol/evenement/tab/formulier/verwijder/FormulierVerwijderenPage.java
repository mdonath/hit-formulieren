package nl.scouting.hit.sol.evenement.tab.formulier.verwijder;

import nl.scouting.hit.sol.AbstractSolMetHoofdMenuPage;
import nl.scouting.hit.sol.evenement.tab.formulier.TabFormulierenOverzichtPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.FormulierWijzigBasisPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Vraagt om bevestiging voor het verwijderen van een formulier.
 */
public class FormulierVerwijderenPage extends AbstractSolMetHoofdMenuPage<FormulierVerwijderenPage> {

    @FindBy(xpath = "//*[@id=\"buttonbar\"]/div/input[@name=\"yes_btn\"]")
    private WebElement buttonJa;

    @FindBy(xpath = "//*[@id=\"buttonbar\"]/div/input[@name=\"no_btn\"]")
    private WebElement buttonNee;

    public FormulierVerwijderenPage(WebDriver driver) {
        super(driver);
    }

    public TabFormulierenOverzichtPage ja() {
        scrollIntoViewAndClick(buttonJa);
        return new TabFormulierenOverzichtPage(driver);
    }

    public FormulierWijzigBasisPage nee() {
        scrollIntoViewAndClick(buttonNee);
        return new FormulierWijzigBasisPage(driver);
    }

}
