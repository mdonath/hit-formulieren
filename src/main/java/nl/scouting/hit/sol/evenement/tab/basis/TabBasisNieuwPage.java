package nl.scouting.hit.sol.evenement.tab.basis;

import nl.scouting.hit.sol.evenement.EvenementenLijstPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class TabBasisNieuwPage extends AbstractTabBasisPage {

    @FindBy(xpath = "//*[@id=\"buttonbar\"]/div[1]/input")
    private WebElement buttonGegevensOpslaan;

    @FindBy(linkText = "Annuleren")
    private WebElement buttonAnnuleren;

    @FindBy(name = "evt_bank_id")
    private WebElement fieldBankrekening;

    public TabBasisNieuwPage(WebDriver driver) {
        super(driver);
    }

    public TabBasisNieuwPage setFieldBankrekening(final int index) {
        Select select = new Select(fieldBankrekening);
        List<WebElement> options = select.getOptions();
        if (!options.isEmpty()) {
            int selectedIndex = Math.min(index, options.size() - 1);
            WebElement element = options.get(selectedIndex);
            select.selectByVisibleText(element.getText());
        }
        return this;
    }

    public TabBasisWijzigenPage opslaan() {
        scrollIntoViewAndClick(buttonGegevensOpslaan);
        return new TabBasisWijzigenPage(driver);
    }

    public EvenementenLijstPage annuleren() {
        scrollIntoViewAndClick(buttonAnnuleren);
        return new EvenementenLijstPage(driver);
    }
}
