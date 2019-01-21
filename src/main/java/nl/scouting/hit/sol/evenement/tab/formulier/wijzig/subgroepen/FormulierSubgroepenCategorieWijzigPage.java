package nl.scouting.hit.sol.evenement.tab.formulier.wijzig.subgroepen;

import nl.scouting.hit.sol.JaNee;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.FormulierWijzigSubgroepenPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class FormulierSubgroepenCategorieWijzigPage extends AbstractFormulierSubgroepenCategoriePage<FormulierSubgroepenCategorieWijzigPage> {

    @FindBy(name = "fte_visible_yn")
    private List<WebElement> fieldZichtbaarVoorDeelnemer;

    @FindBy(name = "submitBtn")
    private WebElement buttonGegevensOpslaan;

    @FindBy(linkText = "Verwijderen")
    private WebElement buttonVerwijderen;

    @FindBy(linkText = "Terug naar subgroepoverzicht")
    private WebElement buttonTerugNaarSubgroepoverzicht;


    /**
     * Constructor.
     *
     * @param driver
     */
    public FormulierSubgroepenCategorieWijzigPage(WebDriver driver) {
        super(driver);
    }

    public FormulierSubgroepenCategorieWijzigPage withZichtbaarVoorDeelnemer(JaNee jaNee) {
        selectRadio(fieldZichtbaarVoorDeelnemer, jaNee);
        return this;
    }

    public FormulierSubgroepenCategorieWijzigPage opslaanGegevens() {
        scrollIntoViewAndClick(buttonGegevensOpslaan);
        return this;
    }

    public FormulierWijzigSubgroepenPage verwijderen() {
        scrollIntoViewAndClick(buttonVerwijderen);
        return new FormulierWijzigSubgroepenPage(driver);
    }

    public FormulierWijzigSubgroepenPage terugNaarSubgroepoverzicht() {
        scrollIntoViewAndClick(buttonTerugNaarSubgroepoverzicht);
        return new FormulierWijzigSubgroepenPage(driver);
    }

}
