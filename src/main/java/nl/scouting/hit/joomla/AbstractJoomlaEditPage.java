package nl.scouting.hit.joomla;

import nl.scouting.hit.common.AbstractPage;
import nl.scouting.hit.common.Valuable;
import nl.scouting.hit.kampinfo.AbstractKampInfoPage;
import nl.scouting.hit.kampinfo.kamp.HitKampBewerkAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class AbstractJoomlaEditPage<C extends AbstractJoomlaEditPage, P extends AbstractKampInfoPage> extends AbstractPage<C> {

    private final JoomlaEditButtons<C, P> editButtons;

    public AbstractJoomlaEditPage(final WebDriver driver, final P previousPage) {
        super(driver);
        this.editButtons = new JoomlaEditButtons<>(driver, previousPage);
    }

    public JoomlaEditButtons<C, P> editButtons() {
        return editButtons;
    }

    protected final C setRadioButtonByLabelClick(final WebElement field, final Valuable jaNee) {
        field.findElements(By.tagName("input")).stream()
                .filter(input ->
                        // find inputfield within fieldset with correct value
                        jaNee.getId().equals(input.getAttribute("value")))
                .findFirst()
                .ifPresent(input ->
                        // find corresponding label and click on THAT
                        field
                                .findElement(By.xpath("label[@for='" + input.getAttribute("id") + "']"))
                                .click());
        return (C) this;
    }

}
