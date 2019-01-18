package nl.scouting.hit.kampinfo.kamp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HitKampBewerkGegevensPage extends AbstractKampInfoBewerkPage<HitKampBewerkGegevensPage, HitKampenPage> {

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

    public HitKampBewerkGegevensPage(final WebDriver driver) {
        super(driver);
    }

    public HitKampBewerkGegevensPage setFieldHitPlaats(final String hitPlaats) {
        selectByValue(fieldHitPlaats, hitPlaats);
        return this;
    }

    public HitKampBewerkGegevensPage setFieldNaam(final String naam) {
        clearAndSendKeys(fieldNaam, naam);
        return this;
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
}
