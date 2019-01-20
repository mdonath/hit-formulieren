package nl.scouting.hit.kampinfo.kamp;

import nl.scouting.hit.joomla.JoomlaJaNee;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HitKampBewerkGegevensPage extends AbstractKampInfoBewerkPage<HitKampBewerkGegevensPage> {

    @FindBy(id = "jform_hitsite_id") // select
    private WebElement fieldHitPlaats;

    @FindBy(id = "jform_naam") // text
    private WebElement fieldNaam;

    @FindBy(id = "jform_startDatumTijd") // text
    private WebElement fieldStartDatumTijd;

    @FindBy(id = "jform_eindDatumTijd") // text
    private WebElement fieldEindDatumTijd;

    @FindBy(id = "jform_deelnamekosten") // text
    private WebElement fieldDeelnamekosten;

    @FindBy(id = "jform_isouderkind") // Ja/Nee
    private WebElement fieldsetIsOuderKind;
    @FindBy(id = "jform_startElders") // Ja/Nee
    private WebElement fieldsetAfwijkendeStartlokatie;
    @FindBy(id = "jform_sublocatie") // Ja/Nee
    private WebElement fieldSublocatie;

    @FindBy(id = "jform_akkoordHitKamp") // Ja/Nee
    private WebElement fieldsetAkkoordKamp;
    @FindBy(id = "jform_akkoordHitPlaats") // Ja/Nee
    private WebElement fieldsetAkkoordPlaats;

    public HitKampBewerkGegevensPage(final WebDriver driver) {
        super(driver);
    }

    public HitKampBewerkGegevensPage setFieldHitPlaats(final String hitPlaats, final int jaar) {
        selectByValue(fieldHitPlaats, String.format("%s (%d)", hitPlaats, jaar));
        return this;
    }

    public HitKampBewerkGegevensPage setFieldNaam(final String naam) {
        clearAndSendKeys(fieldNaam, naam);
        return this;
    }

    public HitKampBewerkGegevensPage setFieldStartDatumTijd(final LocalDateTime startDatumTijd) {
        return setFieldStartDatumTijd(startDatumTijd.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
    }

    public HitKampBewerkGegevensPage setFieldStartDatumTijd(final String startDatumTijd) {
        clearAndSendKeys(fieldStartDatumTijd, startDatumTijd);
        return this;
    }

    public HitKampBewerkGegevensPage setFieldEindDatumTijd(final String eindDatumTijd) {
        clearAndSendKeys(fieldEindDatumTijd, eindDatumTijd);
        return this;
    }

    public HitKampBewerkGegevensPage setFieldDeelnamekosten(final int deelnamekosten) {
        // FIXME
        return this;
    }

    public HitKampBewerkGegevensPage setFieldIsOuderKind(final JoomlaJaNee jaNee) {
        return setRadioButtonByLabelClick(this.fieldsetIsOuderKind, jaNee);
    }

    public HitKampBewerkGegevensPage setFieldAfwijkendeStartlokatie(final JoomlaJaNee jaNee) {
        return setRadioButtonByLabelClick(this.fieldsetAfwijkendeStartlokatie, jaNee);
    }

    public HitKampBewerkGegevensPage setFieldSublocatie(final String sublocatie) {
        clearAndSendKeys(fieldSublocatie, sublocatie);
        return this;
    }

    public HitKampBewerkGegevensPage setAkkoordKamp(final JoomlaJaNee jaNee) {
        return setRadioButtonByLabelClick(this.fieldsetAkkoordKamp, jaNee);
    }

    public HitKampBewerkGegevensPage setAkkoordPlaats(final JoomlaJaNee jaNee) {
        return setRadioButtonByLabelClick(this.fieldsetAkkoordPlaats, jaNee);
    }
}
