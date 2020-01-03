package nl.scouting.hit.sol.evenement.tab.formulier.wijzig.samenstellen;

import org.openqa.selenium.WebDriver;

public class ToelichtendeTekstWijzigen extends AbstractVeldWijzigen<ToelichtendeTekstWijzigen> {

    public ToelichtendeTekstWijzigen(WebDriver driver) {
        super(driver);
    }

    public ToelichtendeTekstWijzigen withInhoud(String inhoud) {
        this.withHelptekst(inhoud);
        return this;
    }
}
