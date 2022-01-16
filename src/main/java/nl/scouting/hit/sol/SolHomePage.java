package nl.scouting.hit.sol;

import org.openqa.selenium.WebDriver;

public class SolHomePage extends AbstractSolMetHoofdMenuPage<SolHomePage> {

    public SolHomePage(final WebDriver driver) {
        super(driver);
    }

    public SolHomePage wijzigRol(final String rolnaam) {
        hoofdmenu().wijzigRol(rolnaam);
        return this;
    }

}
