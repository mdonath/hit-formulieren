package nl.scouting.hit.sol.evenement.tab.formulier.wijzig.subgroepen;

import nl.scouting.hit.common.Range;
import nl.scouting.hit.sol.JaNee;
import nl.scouting.hit.sol.evenement.tab.formulier.common.AbstractFormulierPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.FormulierWijzigSubgroepenPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public abstract class AbstractFormulierSubgroepenCategoriePage<T extends AbstractFormulierSubgroepenCategoriePage> extends AbstractFormulierPage<T> {

    @FindBy(name = "fte_nm")
    private WebElement fieldAanduiding;
    @FindBy(name = "fte_txt")
    private WebElement fieldToelichting;
    @FindBy(name = "fte_teams_min_ct")
    private WebElement fieldMinimumAantalSubgroepen;
    @FindBy(name = "fte_teams_max_ct")
    private WebElement fieldMaximumAantalSubgroepen;

    @FindBy(name = "fte_parts_min_ct")
    private WebElement fieldMinimumAantalDeelnemers;
    @FindBy(name = "fte_parts_max_ct")
    private WebElement fieldMaximumAantalDeelnemers;

    @FindBy(name = "fte_parts_may_create_yn")
    private List<WebElement> fieldMagSubgroepAanmaken;
    @FindBy(name = "fte_required_yn")
    private List<WebElement> fieldSubgroepVerplicht;
    @FindBy(name = "fte_parts_max_count_yn")
    private List<WebElement> fieldTeltHetMaxAantalMee;
    @FindBy(name = "fte_contact_show_yn")
    private List<WebElement> fieldContactpersoonVermelden;

    @FindBy(name = "fte_modulo")
    private WebElement fieldDeelbaarDoor;

    // INCOMPLETE SUBGROEPEN
    @FindBy(name = "fte_days_incomplete_ct")
    private WebElement fieldAantalDagenIncompleet;
    @FindBy(name = "fte_incomplete_mail_yn")
    private List<WebElement> fieldAutomatischeIncompleetMailDeelnemers;
    @FindBy(name = "fte_incomplete_mail_cc_admin_yn")
    private List<WebElement> fieldAutomatischeIncompleetMailOrganisatie;

    /**
     * Constructor.
     *
     * @param driver
     */
    public AbstractFormulierSubgroepenCategoriePage(final WebDriver driver) {
        super(driver);
    }

    public T setFieldAanduiding(final String s) {
        clearAndSendKeys(fieldAanduiding, s);
        return (T) this;
    }

    public T setFieldToelichting(final String s) {
        clearAndSendKeys(fieldToelichting, s);
        return (T) this;
    }

    public T setFieldsAantalSubgroepen(final Range range) {
        return (T) setFieldMinimumAantalSubgroepen(range.getMinimum())
                .setFieldMaximumAantalSubgroepen(range.getMaximum());
    }

    public T setFieldMinimumAantalSubgroepen(final int i) {
        clearAndSendKeys(fieldMinimumAantalSubgroepen, i);
        return (T) this;
    }

    public T setFieldMaximumAantalSubgroepen(final int i) {
        clearAndSendKeys(fieldMaximumAantalSubgroepen, i);
        return (T) this;
    }

    public T setFieldsAantalDeelnemers(final Range range) {
        return (T) setFieldMinimumAantalDeelnemers(range.getMinimum())
                .setFieldMaximumAantalDeelnemers(range.getMaximum());
    }

    public T setFieldMinimumAantalDeelnemers(final int i) {
        clearAndSendKeys(fieldMinimumAantalDeelnemers, i);
        return (T) this;
    }

    public T setFieldMaximumAantalDeelnemers(final int i) {
        clearAndSendKeys(fieldMaximumAantalDeelnemers, i);
        return (T) this;
    }

    public T setFieldMagSubgroepAanmaken(final JaNee jaNee) {
        selectRadio(fieldMagSubgroepAanmaken, jaNee);
        return (T) this;
    }

    public T setFieldSubgroepVerplicht(final JaNee jaNee) {
        selectRadio(fieldSubgroepVerplicht, jaNee);
        return (T) this;
    }

    public T setFieldTeltHetMaxAantalMee(final JaNee jaNee) {
        selectRadio(fieldTeltHetMaxAantalMee, jaNee);
        return (T) this;
    }

    public T setFieldContactpersoonVermelden(final JaNee jaNee) {
        selectRadio(fieldContactpersoonVermelden, jaNee);
        return (T) this;
    }

    public T setFieldDeelbaarDoor(final int i) {
        clearAndSendKeys(fieldDeelbaarDoor, i == 0 ? 1 : i);
        return (T) this;
    }

    public T setFieldAantalDagenIncompleet(final int i) {
        clearAndSendKeys(fieldAantalDagenIncompleet, i);
        return (T) this;
    }

    public T setFieldAutomatischeIncompleetMailDeelnemers(final JaNee jaNee) {
        selectRadio(fieldAutomatischeIncompleetMailDeelnemers, jaNee);
        return (T) this;
    }

    public T setFieldAutomatischeIncompleetMailOrganisatie(final JaNee jaNee) {
        selectRadio(fieldAutomatischeIncompleetMailOrganisatie, jaNee);
        return (T) this;
    }

}
