package nl.scouting.hit.sol.evenement;

import nl.scouting.hit.sol.AbstractSolMetHoofdMenuPage;
import nl.scouting.hit.sol.evenement.tab.EvenementSubMenu;
import nl.scouting.hit.sol.evenement.tab.basis.TabBasisWijzigenPage;
import nl.scouting.hit.sol.evenement.tab.extra.TabExtraPage;
import nl.scouting.hit.sol.evenement.tab.formulier.TabFormulierenOverzichtPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class AbstractEvenementPage<T extends AbstractEvenementPage> extends AbstractSolMetHoofdMenuPage<T> {

    private EvenementSubMenu evenementSubMenu;

    public AbstractEvenementPage(WebDriver driver) {
        super(driver);
        evenementSubMenu = new EvenementSubMenu(driver);
    }

    public EvenementSubMenu submenu() {
        return evenementSubMenu;
    }

    public static <T extends AbstractEvenementPage> T build(final WebDriver driver) {
        final String activeTab = driver.findElement(By.xpath("//a[@class='active']")).getText();
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
