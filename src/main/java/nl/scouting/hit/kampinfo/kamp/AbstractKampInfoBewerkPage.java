package nl.scouting.hit.kampinfo.kamp;

import nl.scouting.hit.joomla.AbstractJoomlaEditPage;
import org.openqa.selenium.WebDriver;

public abstract class AbstractKampInfoBewerkPage<C extends AbstractKampInfoBewerkPage>
        extends AbstractJoomlaEditPage<C, HitKampenPage> {

    private HitKampBewerkTabs tabs;

    public AbstractKampInfoBewerkPage(final WebDriver driver) {
        super(driver, new HitKampenPage(driver));
        tabs = new HitKampBewerkTabs(driver);
    }

    public HitKampBewerkTabs tabs() {
        return tabs;
    }

}
