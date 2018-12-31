package nl.scouting.hit.sol.evenement;

import nl.scouting.hit.sol.AbstractSolPage;
import nl.scouting.hit.sol.evenement.tab.basis.TabBasisNieuwPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EvenementenLijstPage extends AbstractSolPage {

    @FindBy(linkText = "Evenement toevoegen")
    private WebElement buttonEvenementToevoegen;

    public EvenementenLijstPage(WebDriver driver) {
        super(driver);
    }

    public boolean heeftEvenement(String naamEvenement) {
        return !driver.findElements(By.ById.partialLinkText(naamEvenement)).isEmpty();
    }

    public <T extends AbstractEvenementPage> T openEvenement(String naamEvenement) {
        By text = By.ById.partialLinkText(naamEvenement);

        driver.findElement(text).click();
        return AbstractEvenementPage.build(driver);
    }

    public TabBasisNieuwPage evenementToevoegen() {
        scrollIntoViewAndClick(buttonEvenementToevoegen);
        return new TabBasisNieuwPage(driver);
    }
}
