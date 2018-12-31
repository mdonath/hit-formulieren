package nl.scouting.hit.sol;

import org.openqa.selenium.WebDriver;

public abstract class AbstractSolPage<T extends AbstractSolPage> extends AbstractPage<T> {

    private HoofdMenu hoofdMenu;

    protected AbstractSolPage(final WebDriver driver) {
        this(driver, null);
    }

    protected AbstractSolPage(final WebDriver driver, final String baseUrl) {
        super(driver);
        hoofdMenu = new HoofdMenu(driver);
    }

    public final HoofdMenu hoofdmenu() {
        return hoofdMenu;
    }
}
