package nl.scouting.hit.joomla;

import nl.scouting.hit.common.AbstractPage;
import nl.scouting.hit.kampinfo.AbstractKampInfoPage;
import org.openqa.selenium.WebDriver;

public abstract class AbstractJoomlaEditPage<C extends AbstractJoomlaEditPage, P extends AbstractKampInfoPage> extends AbstractPage {

    private JoomlaEditButtons<C, P> editButtons;

    public AbstractJoomlaEditPage(final WebDriver driver, final P previousPage) {
        super(driver);
        this.editButtons = new JoomlaEditButtons<>(driver, previousPage);
    }

    public JoomlaEditButtons<C, P> editButtons() {
        return editButtons;
    }

}
