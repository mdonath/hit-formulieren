package nl.scouting.hit.kampinfo;

import nl.scouting.hit.common.AbstractPage;
import org.openqa.selenium.WebDriver;

public abstract class AbstractKampInfoPage<T extends AbstractPage> extends AbstractPage {

    private final KampInfoLinkerMenu submenu;

    public AbstractKampInfoPage(final WebDriver driver) {
        super(driver);
        submenu = new KampInfoLinkerMenu(driver);
    }

    public KampInfoLinkerMenu submenu() {
        return submenu;
    }

}
