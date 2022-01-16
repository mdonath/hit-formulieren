package nl.scouting.hit.kampinfo.kamp;

import nl.scouting.hit.common.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * De tabs van het bewerken van de gegevens van een HIT kamponderdeel.
 */
public class HitKampBewerkTabs extends AbstractPage<HitKampBewerkTabs> {

    @FindBy(linkText = "Gegevens van een HIT Kamp")
    private WebElement tabGegevens;

    @FindBy(linkText = "HIT Courant")
    private WebElement tabHitCourant;

    @FindBy(linkText = "Website")
    private WebElement tabWebsite;

    @FindBy(linkText = "Iconen")
    private WebElement tabIconen;

    @FindBy(linkText = "Deelnemer")
    private WebElement tabDeelnemer;

    @FindBy(linkText = "Doelstelling")
    private WebElement tabDoelstelling;

    @FindBy(linkText = "Admin")
    private WebElement tabAdmin;

    public HitKampBewerkTabs(final WebDriver driver) {
        super(driver);
    }

    public HitKampBewerkGegevensPage gegevensVanEenHitKamp() {
        scrollIntoViewCenteredAndClick(tabGegevens);
        return new HitKampBewerkGegevensPage(driver);
    }

    public HitKampBewerkHitCourantPage hitCourant() {
        scrollIntoViewCenteredAndClick(tabHitCourant);
        return new HitKampBewerkHitCourantPage(driver);
    }

    public HitKampBewerkWebsitePage website() {
        scrollIntoViewCenteredAndClick(tabWebsite);
        return new HitKampBewerkWebsitePage(driver);
    }

    public HitKampBewerkIconenPage iconen() {
        scrollIntoViewCenteredAndClick(tabIconen);
        return new HitKampBewerkIconenPage(driver);
    }

    public HitKampBewerkDeelnemerPage deelnemer() {
        scrollIntoViewCenteredAndClick(tabDeelnemer);
        return new HitKampBewerkDeelnemerPage(driver);
    }

    public HitKampBewerkDoelstellingPage doelstelling() {
        scrollIntoViewCenteredAndClick(tabDoelstelling);
        return new HitKampBewerkDoelstellingPage(driver);
    }

    public HitKampBewerkAdminPage admin() {
        scrollIntoViewCenteredAndClick(tabAdmin);
        return new HitKampBewerkAdminPage(driver);
    }

}
