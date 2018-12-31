package nl.scouting.hit.sol.evenement.tab.formulier.wijzig.subgroepen;

import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.FormulierWijzigSubgroepenPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FormulierSubgroepenCategorieNieuwPage extends AbstractFormulierSubgroepenCategoriePage<FormulierSubgroepenCategorieNieuwPage> {

    @FindBy(xpath = "//*[@id=\"buttonbar\"]/div[1]/input")
    private WebElement buttonGegevensOpslaan;

    /**
     * Constructor.
     *
     * @param driver
     */
    public FormulierSubgroepenCategorieNieuwPage(WebDriver driver) {
        super(driver);
    }

    public FormulierWijzigSubgroepenPage gegevensOpslaan() {
        scrollIntoViewAndClick(buttonGegevensOpslaan);
        return new FormulierWijzigSubgroepenPage(driver);
    }

}
