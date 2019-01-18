package nl.scouting.hit.kampinfo.kamp;

import nl.scouting.hit.joomla.AbstractJoomlaEditPage;
import nl.scouting.hit.kampinfo.AbstractKampInfoPage;
import org.openqa.selenium.WebDriver;

public abstract class AbstractKampInfoBewerkPage<C extends AbstractKampInfoBewerkPage, P extends AbstractKampInfoPage> extends AbstractJoomlaEditPage {

    private HitKampBewerkTabs tabs;

    public AbstractKampInfoBewerkPage(final WebDriver driver) {
        super(driver, new HitKampenPage(driver));
        tabs = new HitKampBewerkTabs(driver);
    }

    public HitKampBewerkTabs tabs() {
        return tabs;
    }

}
