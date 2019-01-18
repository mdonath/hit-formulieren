package nl.scouting.hit.sol.evenement.tab.formulier.nieuw;

import nl.scouting.hit.sol.AbstractSolMetHoofdMenuPage;
import nl.scouting.hit.sol.AbstractSolPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.FormulierWijzigBasisPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FormulierOverzichtNieuwPage extends AbstractSolMetHoofdMenuPage<FormulierOverzichtNieuwPage> {

    @FindBy(xpath = "//*[@id=\"buttonbar\"]/div[1]/input")
    private WebElement buttonVoltooien;

    public FormulierOverzichtNieuwPage(WebDriver driver) {
        super(driver);
    }

    public FormulierWijzigBasisPage voltooien() {
        scrollIntoViewAndClick(buttonVoltooien);
        return new FormulierWijzigBasisPage(driver);
    }
}
