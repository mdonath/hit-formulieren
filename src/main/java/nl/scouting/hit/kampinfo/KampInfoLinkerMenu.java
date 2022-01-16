package nl.scouting.hit.kampinfo;

import nl.scouting.hit.common.AbstractPage;
import nl.scouting.hit.kampinfo.kamp.HitKampenPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class KampInfoLinkerMenu extends AbstractPage<KampInfoLinkerMenu> {

    @FindBy(linkText = "Info")
    private WebElement menuInfo;
    @FindBy(linkText = "HIT Projecten")
    private WebElement menuHitProjecten;
    @FindBy(linkText = "HIT Plaatsen")
    private WebElement menuHitPlaatsen;
    @FindBy(linkText = "HIT Kampen")
    private WebElement menuHitKampen;

    public KampInfoLinkerMenu(final WebDriver driver) {
        super(driver);
    }

    public KampInfoInfoPage openInfo() {
        menuInfo.click();
        return new KampInfoInfoPage(driver);
    }

    public HitKampenPage openHitKampen() {
        menuHitKampen.click();
        return new HitKampenPage(driver);
    }


}
