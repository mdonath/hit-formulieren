package nl.scouting.hit.sol;

import nl.scouting.hit.sol.evenement.EvenementenLijstPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HoofdMenu extends AbstractPage {

    @FindBy(id = "login_role_select")
    private WebElement rollenMenu;

    public HoofdMenu(final WebDriver driver) {
        super(driver);
    }

    public void openklappenRollenMenu() {
        activeerElement(rollenMenu);
    }

    public void wijzigRol(final String rolnaam) {
        openklappenRollenMenu();
        driver.findElement(By.linkText(rolnaam)).click();
        logNoticeMessage();
    }

    public EvenementenLijstPage openSpelVanMijnOrganisatie() {
        openklappenMenuSpel();
        klikMenuSubItem("Van mijn organisatie");
        return new EvenementenLijstPage(driver);
    }

    public EvenementenLijstPage openSpelVanMijnSpeleenheid() {
        openklappenMenuSpel();
        klikMenuSubItem("Van mijn speleenheid");
        return new EvenementenLijstPage(driver);
    }

    public void openklappenMenuSpel() {
        openklappenMenu("Spel");
    }


    private void klikMenuSubItem(String naam) {
        driver.findElement(By.partialLinkText(naam)).click();
    }
}
