package nl.scouting.hit.sol;

import org.openqa.selenium.WebDriver;

public abstract class AbstractSolMetHoofdMenuPage<T extends AbstractSolMetHoofdMenuPage> extends AbstractSolPage<T> {

    private final HoofdMenu hoofdMenu;

    public AbstractSolMetHoofdMenuPage(final WebDriver driver) {
        super(driver);
        hoofdMenu = new HoofdMenu(driver);
    }

    public final HoofdMenu hoofdmenu() {
        return hoofdMenu;
    }

}
