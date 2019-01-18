package nl.scouting.hit.sol.evenement.tab.basis;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TabBasisWijzigenPage extends AbstractTabBasisPage {

    @FindBy(linkText = "Wijzigingen opslaan")
    private WebElement buttonWijzigingenOpslaan;

    public TabBasisWijzigenPage(WebDriver driver) {
        super(driver);
    }

}
