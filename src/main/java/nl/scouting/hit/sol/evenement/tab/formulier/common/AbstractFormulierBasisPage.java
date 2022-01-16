package nl.scouting.hit.sol.evenement.tab.formulier.common;

import nl.scouting.hit.common.Datum;
import nl.scouting.hit.sol.JaNee;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public abstract class AbstractFormulierBasisPage<T extends AbstractFormulierBasisPage> extends AbstractFormulierPage<T> {

    /* FIELDS */
    @FindBy(name = "frm_nm")
    private WebElement fieldNaamFormulier;

    @FindBy(name = "frm_project_ds")
    private WebElement fieldProjectCode;
    @FindBy(name = "frm_org_mail")
    private WebElement fieldMailadresEvenement;
    @FindBy(name = "frm_mail_to_organisator_yn")
    private List<WebElement> fieldMailBijAanmelding;
    @FindBy(name = "frm_mail_to_organisator_on_part_change_yn")
    private List<WebElement> fieldMailBijWijziging;
    @FindBy(name = "frm_subscribe_mail")
    private WebElement fieldMailadresVoorInschrijfvragen;

    // Startdatum evenement
    @FindBy(name = "frm_from_dt_day")
    private WebElement fieldEvenementStartDag;
    @FindBy(name = "frm_from_dt_month")
    private WebElement fieldEvenementStartMaand;
    @FindBy(name = "frm_from_dt_year")
    private WebElement fieldEvenementStartJaar;
    @FindBy(name = "frm_from_time")
    private WebElement fieldEvenementStartTijd;

    // Einddatum evenement
    @FindBy(name = "frm_till_dt_day")
    private WebElement fieldEvenementEindDag;
    @FindBy(name = "frm_till_dt_month")
    private WebElement fieldEvenementEindMaand;
    @FindBy(name = "frm_till_dt_year")
    private WebElement fieldEvenementEindJaar;
    @FindBy(name = "frm_till_time")
    private WebElement fieldEvenementEindTijd;

    // Inschrijven vanaf
    @FindBy(name = "frm_book_from_dt_day")
    private WebElement fieldInschrijvingStartDag;
    @FindBy(name = "frm_book_from_dt_month")
    private WebElement fieldInschrijvingStartMaand;
    @FindBy(name = "frm_book_from_dt_year")
    private WebElement fieldInschrijvingStartJaar;
    @FindBy(name = "frm_book_from_ts")
    private WebElement fieldInschrijvingStartTijd;

    @FindBy(name = "frm_book_till_dt_day")
    private WebElement fieldInschrijvingEindDag;
    @FindBy(name = "frm_book_till_dt_month")
    private WebElement fieldInschrijvingEindMaand;
    @FindBy(name = "frm_book_till_dt_year")
    private WebElement fieldInschrijvingEindJaar;

    public AbstractFormulierBasisPage(WebDriver driver) {
        super(driver);
    }

    public T withNaamFormulier(final String s) {
        clearAndSendKeys(fieldNaamFormulier, s);
        return (T) this;
    }


    public T withProjectCode(final String s) {
        clearAndSendKeys(fieldProjectCode, s);
        return (T) this;
    }

    public T withMailadresEvenement(String s) {
        clearAndSendKeys(fieldMailadresEvenement, s);
        return (T) this;
    }

    public T withMailBijAanmelding(final JaNee jaNee) {
        selectRadio(fieldMailBijAanmelding, jaNee);
        return (T) this;
    }

    public T withMailBijWijziging(final JaNee jaNee) {
        selectRadio(fieldMailBijWijziging, jaNee);
        return (T) this;
    }

    public T withMailadresVoorInschrijfvragen(final String s) {
        clearAndSendKeys(fieldMailadresVoorInschrijfvragen, s);
        return (T) this;
    }

    public T withEvenementStart(Datum datum, String tijd) {
        return withEvenementStart(datum.getDag(), datum.getMaand(), datum.getJaar(), tijd);
    }

    public T withEvenementStart(int dag, int maand, int jaar, String tijd) {
        setFieldsDatumTijd(fieldEvenementStartDag, dag
                , fieldEvenementStartMaand, maand
                , fieldEvenementStartJaar, jaar
                , fieldEvenementStartTijd, tijd);
        return (T) this;
    }

    public T withEvenementEind(Datum datum, String tijd) {
        return withEvenementEind(datum.getDag(), datum.getMaand(), datum.getJaar(), tijd);
    }

    public T withEvenementEind(int dag, int maand, int jaar, String tijd) {
        setFieldsDatumTijd(fieldEvenementEindDag, dag
                , fieldEvenementEindMaand, maand
                , fieldEvenementEindJaar, jaar
                , fieldEvenementEindTijd, tijd);
        return (T) this;
    }

    public T withInschrijvingStart(Datum datum, String tijd) {
        return withInschrijvingStart(datum.getDag(), datum.getMaand(), datum.getJaar(), tijd);
    }

    public T withInschrijvingStart(int dag, int maand, int jaar) {
        setFieldsDatum(fieldInschrijvingStartDag, dag
                , fieldInschrijvingStartMaand, maand
                , fieldInschrijvingStartJaar, jaar);
        return (T) this;
    }

    public T withInschrijvingStart(int dag, int maand, int jaar, String tijd) {
        setFieldsDatumTijd(fieldInschrijvingStartDag, dag
                , fieldInschrijvingStartMaand, maand
                , fieldInschrijvingStartJaar, jaar
                , fieldInschrijvingStartTijd, tijd);
        return (T) this;
    }

    public T withInschrijvingEind(Datum datum) {
        return withInschrijvingEind(datum.getDag(), datum.getMaand(), datum.getJaar());
    }

    public T withInschrijvingEind(int dag, int maand, int jaar) {
        setFieldsDatum(fieldInschrijvingEindDag, dag
                , fieldInschrijvingEindMaand, maand
                , fieldInschrijvingEindJaar, jaar);
        return (T) this;
    }

}
