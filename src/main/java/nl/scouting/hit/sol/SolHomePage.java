package nl.scouting.hit.sol;

import org.openqa.selenium.WebDriver;

public class SolHomePage extends AbstractSolMetHoofdMenuPage<SolHomePage> {

    public SolHomePage(WebDriver driver) {
        super(driver);
    }

    public SolHomePage wijzigRol(String rolnaam) {
        hoofdmenu().wijzigRol(rolnaam);
        return this;
    }

}
