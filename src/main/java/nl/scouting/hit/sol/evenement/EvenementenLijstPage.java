package nl.scouting.hit.sol.evenement;

import nl.scouting.hit.sol.AbstractSolMetHoofdMenuPage;
import nl.scouting.hit.sol.evenement.tab.basis.TabBasisNieuwPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EvenementenLijstPage extends AbstractSolMetHoofdMenuPage<EvenementenLijstPage> {

    @FindBy(linkText = "Evenement toevoegen")
    private WebElement buttonEvenementToevoegen;

    public EvenementenLijstPage(final WebDriver driver) {
        super(driver);
    }

    public boolean heeftEvenement(final String naamEvenement) {
        return !driver.findElements(By.ById.partialLinkText(naamEvenement)).isEmpty();
    }

    public <T extends AbstractEvenementPage> T openEvenement(final String naamEvenement) {
        final By text = By.ById.partialLinkText(naamEvenement);

        driver.findElement(text).click();
        return AbstractEvenementPage.build(driver);
    }

    public TabBasisNieuwPage evenementToevoegen() {
        scrollIntoViewAndClick(buttonEvenementToevoegen);
        return new TabBasisNieuwPage(driver);
    }
}
