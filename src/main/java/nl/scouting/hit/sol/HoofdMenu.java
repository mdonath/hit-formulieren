package nl.scouting.hit.sol;

import nl.scouting.hit.sol.evenement.EvenementenLijstPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HoofdMenu extends AbstractSolPage<HoofdMenu> {

    @FindBy(id = "login_role_select")
    private WebElement rollenMenu;

    public HoofdMenu(final WebDriver driver) {
        super(driver);
    }

    @Deprecated // Er is nu geen rollenmenu meer, maar een organisatiemenu
    public void openklappenRollenMenu() {
        activeerElement(rollenMenu);
    }

    @Deprecated // Er is nu geen rollenmenu meer, maar een organisatiemenu
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

    public EvenementenLijstPage openSpelVanMijnSpeleenheid(final String naamSpeleenheid) {
        openklappenMenuSpel();
        klikMenuSubItem("Van speleenheid " + naamSpeleenheid);
        return new EvenementenLijstPage(driver);
    }

    public void openklappenMenuSpel() {
        openklappenMenu("Spel");
    }

    private void klikMenuSubItem(final String naam) {
        driver.findElement(By.partialLinkText(naam)).click();
    }
}
