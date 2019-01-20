package nl.scouting.hit.joomla;

import nl.scouting.hit.common.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @param <C> Type of Current Page
 * @param <P> Type of Previous Page
 */
public class JoomlaEditButtons<C extends AbstractPage, P extends AbstractPage> extends AbstractPage {

    @FindBy(xpath = "//*[@id=\"toolbar-apply\"]/button")
    private WebElement buttonApply; // Opslaan
    @FindBy(xpath = "//*[@id=\"toolbar-save\"]/button")
    private WebElement buttonSave; // Opslaan & cancel
    @FindBy(xpath = "//*[@id=\"toolbar-save-new\"]/button")
    private WebElement buttonSaveNew; // Opslaan & nieuw
    @FindBy(xpath = "//*[@id=\"toolbar-popup-preview\"]/button")
    private WebElement buttonPreview; // Preview
    @FindBy(xpath = "//*[@id=\"toolbar-cancel\"]/button")
    private WebElement buttonCancel; // Sluiten

    private final P previousPage;


    public JoomlaEditButtons(final WebDriver driver, P previousPage) {
        super(driver);
        this.previousPage = previousPage;
    }

    public C apply() {
        scrollIntoViewCenteredAndClick(buttonApply);
        return (C) this;
    }

    public P save() {
        scrollIntoViewCenteredAndClick(buttonSave);
        return previousPage;
    }

    public C saveAndNew() {
        scrollIntoViewCenteredAndClick(buttonSaveNew);
        return (C) this;
    }

    public C preview() {
        scrollIntoViewCenteredAndClick(buttonPreview);
        return (C) this;
    }

    public P cancel() {
        scrollIntoViewCenteredAndClick(buttonCancel);
        return previousPage;
    }

}
