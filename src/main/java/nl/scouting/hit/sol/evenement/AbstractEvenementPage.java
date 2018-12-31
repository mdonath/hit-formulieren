package nl.scouting.hit.sol.evenement;

import nl.scouting.hit.sol.AbstractSolPage;
import nl.scouting.hit.sol.HoofdMenu;
import nl.scouting.hit.sol.evenement.tab.basis.TabBasisWijzigenPage;
import nl.scouting.hit.sol.evenement.tab.extra.TabExtraPage;
import nl.scouting.hit.sol.evenement.tab.formulier.TabFormulierenOverzichtPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class AbstractEvenementPage extends AbstractSolPage {

    private HoofdMenu hoofdMenu;
    private EvenementSubMenuPage evenementSubMenu;

    public AbstractEvenementPage(WebDriver driver) {
        super(driver);
        hoofdMenu = new HoofdMenu(driver);
        evenementSubMenu = new EvenementSubMenuPage(driver);
    }

    public EvenementSubMenuPage submenu() {
        return evenementSubMenu;
    }

    public static <T extends AbstractEvenementPage> T build(final WebDriver driver) {
        String activeTab = driver.findElement(By.xpath("//a[@class='active']")).getText();
        System.out.println(activeTab);
        switch (activeTab) {
            case "basis":
                return (T) new TabBasisWijzigenPage(driver);
            case "extra":
                return (T) new TabExtraPage(driver);
            case "formulieren":
                return (T) new TabFormulierenOverzichtPage(driver);
            default:
                throw new IllegalArgumentException("Huidige tab wordt nog niet ondersteund");
        }
    }

}
