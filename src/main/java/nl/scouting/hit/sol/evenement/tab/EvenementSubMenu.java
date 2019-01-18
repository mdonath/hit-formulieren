package nl.scouting.hit.sol.evenement.tab;

import nl.scouting.hit.common.AbstractPage;
import nl.scouting.hit.sol.evenement.tab.basis.TabBasisWijzigenPage;
import nl.scouting.hit.sol.evenement.tab.extra.TabExtraPage;
import nl.scouting.hit.sol.evenement.tab.formulier.TabFormulierenOverzichtPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Kan alle tabs openen uit het submenu van een Evenement.
 */
public class EvenementSubMenu extends AbstractPage {

    @FindBy(linkText = "basis")
    private WebElement tabBasis;
    @FindBy(linkText = "extra")
    private WebElement tabExtra;
    @FindBy(linkText = "formulieren")
    private WebElement tabFormulieren;

    public EvenementSubMenu(WebDriver driver) {
        super(driver);
    }

    public TabBasisWijzigenPage openTabBasis() {
        tabBasis.click();
        return new TabBasisWijzigenPage(driver);
    }

    public TabExtraPage openTabExtra() {
        tabExtra.click();
        return new TabExtraPage(driver);
    }

    public TabFormulierenOverzichtPage openTabFormulieren() {
        tabFormulieren.click();
        return new TabFormulierenOverzichtPage(driver);
    }

}
