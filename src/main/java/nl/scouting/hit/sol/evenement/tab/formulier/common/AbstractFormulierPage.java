package nl.scouting.hit.sol.evenement.tab.formulier.common;

import nl.scouting.hit.common.AbstractPage;
import nl.scouting.hit.sol.AbstractSolMetHoofdMenuPage;
import nl.scouting.hit.sol.evenement.tab.formulier.TabFormulierenOverzichtPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.FormulierSubMenuPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class AbstractFormulierPage<T extends AbstractFormulierPage> extends AbstractSolMetHoofdMenuPage<T> {

    public static class MeerMenuPage extends AbstractPage<MeerMenuPage> {
        @FindBy(linkText = "Naar alle formulieren")
        private WebElement menuNaarAlleFormulieren;

        public MeerMenuPage(final WebDriver driver) {
            super(driver);
        }

        public TabFormulierenOverzichtPage naarAlleFormulieren() {
            menuNaarAlleFormulieren.click();
            return new TabFormulierenOverzichtPage(driver);
        }
    }

    protected FormulierSubMenuPage formulierSubmenu;

    @FindBy(css = "div#buttonbar div#extra_menu input#expand")
    private WebElement buttonMeer;

    protected AbstractFormulierPage(final WebDriver driver) {
        super(driver);
        formulierSubmenu = new FormulierSubMenuPage(driver);
    }

    public FormulierSubMenuPage submenu() {
        return formulierSubmenu;
    }


    public MeerMenuPage meer() {
        scrollToBottomRight();
        activeerElement(buttonMeer);
        return new MeerMenuPage(driver);
    }

}
