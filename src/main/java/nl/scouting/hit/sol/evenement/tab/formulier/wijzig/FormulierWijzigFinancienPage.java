package nl.scouting.hit.sol.evenement.tab.formulier.wijzig;

import nl.scouting.hit.common.Datum;
import nl.scouting.hit.sol.JaNee;
import nl.scouting.hit.sol.Valuable;
import nl.scouting.hit.sol.evenement.tab.formulier.common.AbstractFormulierPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class FormulierWijzigFinancienPage extends AbstractFormulierPage<FormulierWijzigFinancienPage> {


    public enum Annuleringstype implements Valuable {
        GEEN_ANNULERINGSKOSTEN(1),
        PERCENTAGE_VAN_DE_DEELNAMEKOSTEN(2),
        VAST_BEDRAG_VOOR_DE_HELE_INSCHRIJVING(3);

        private final int id;

        Annuleringstype(final int id) {
            this.id = id;
        }

        public String getId() {
            return String.valueOf(id);
        }
    }

    public enum GebruikGroepsrekening implements Valuable {
        IEDEREEN(10),
        ALLEEN_GEMACHTIGDEN(20),
        NIET_TOEGSTAAN(3);

        private final int id;

        GebruikGroepsrekening(final int id) {
            this.id = id;
        }

        public String getId() {
            return String.valueOf(id);
        }
    }

    public enum Betalingswijze implements Valuable {
        IDEAL(5);

        private final int id;

        Betalingswijze(final int id) {
            this.id = id;
        }

        public String getId() {
            return String.valueOf(id);
        }
    }


    //
    // Annuleren
    //
    @FindBy(name = "frm_cancel_dt1_day")
    private WebElement fieldKosteloosAnnulerenTotDag;
    @FindBy(name = "frm_cancel_dt1_month")
    private WebElement fieldKosteloosAnnulerenTotMaand;
    @FindBy(name = "frm_cancel_dt1_year")
    private WebElement fieldKosteloosAnnulerenTotJaar;

    @FindBy(name = "frm_cancel_type")
    private List<WebElement> fieldAnnuleringstype;

    @FindBy(name = "frm_on_part_cancel_note_required_yn")
    private List<WebElement> fieldAnnuleringsredenVerplicht;

    @FindBy(name = "frm_cancel_perc")
    private WebElement fieldAnnuleringsPercentage;
    @FindBy(name = "frm_cancel_am")
    private WebElement fieldAnnuleringsBedrag;

    @FindBy(name = "frm_cancel_dt2_day")
    private WebElement fieldVolledigeKostenAnnulerenVanafDag;
    @FindBy(name = "frm_cancel_dt2_month")
    private WebElement fieldVolledigeKostenAnnulerenVanafaand;
    @FindBy(name = "frm_cancel_dt2_year")
    private WebElement fieldVolledigeKostenAnnulerenVanafJaar;

    //
    // Rekeningen
    //
    @FindBy(name = "frm_group_account_type")
    private List<WebElement> fieldGebruikGroepsrekening;

    @FindBy(name = "frm_person_account_yn")
    private List<WebElement> fieldGebruikPersoonlijkeRekening;

    //
    // Betalingswijzes
    //
    @FindBy(id = "fcty_cty_id_1")
    private WebElement fieldBetalingswijzeIdeal;

    //
    // Button
    //
    /**
     * Constructor.
     *
     * @param driver de webdriver
     */
    public FormulierWijzigFinancienPage(final WebDriver driver) {
        super(driver);
    }

    public FormulierWijzigFinancienPage setFieldsKosteloosAnnulerenTot(final Datum datum) {
        return setFieldsKosteloosAnnulerenTot(datum.getDag(), datum.getMaand(), datum.getJaar());
    }

    public FormulierWijzigFinancienPage setFieldsKosteloosAnnulerenTot(final int dag, final int maand, final int jaar) {
        setFieldsDatum(fieldKosteloosAnnulerenTotDag, dag
                , fieldKosteloosAnnulerenTotMaand, maand
                , fieldKosteloosAnnulerenTotJaar, jaar);
        return this;
    }

    public FormulierWijzigFinancienPage setFieldAnnuleringstype(final Annuleringstype type) {
        selectRadio(fieldAnnuleringstype, type);
        return this;
    }

    @FindBy(xpath = "//div[@id!=\"bottom_menu\"]//input[@name=\"submitBtn\" and @value=\"Wijzigingen opslaan\"]")
    private WebElement buttonWijzigingenOpslaan;

    public FormulierWijzigFinancienPage setFieldAnnuleringsPercentage(final String s) {
        clearAndSendKeys(fieldAnnuleringsPercentage, s);
        return this;
    }

    public FormulierWijzigFinancienPage setFieldAnnuleringsBedrag(final String s) {
        clearAndSendKeys(fieldAnnuleringsBedrag, s);
        return this;
    }

    public FormulierWijzigFinancienPage setFieldsVolledigeKostenAnnulerenVanaf(final Datum datum) {
        return setFieldsVolledigeKostenAnnulerenVanaf(datum.getDag(), datum.getMaand(), datum.getJaar());
    }

    public FormulierWijzigFinancienPage setFieldsVolledigeKostenAnnulerenVanaf(final int dag, final int maand, final int jaar) {
        setFieldsDatum(fieldVolledigeKostenAnnulerenVanafDag, dag
                , fieldVolledigeKostenAnnulerenVanafaand, maand
                , fieldVolledigeKostenAnnulerenVanafJaar, jaar);
        return this;
    }

    public FormulierWijzigFinancienPage setFieldAnnuleringsredenVerplicht(final JaNee jaNee) {
        selectRadio(fieldAnnuleringsredenVerplicht, jaNee);
        return this;
    }

    public FormulierWijzigFinancienPage setFieldGebruikGroepsrekening(final GebruikGroepsrekening type) {
        selectRadio(fieldGebruikGroepsrekening, type);
        return this;
    }

    public FormulierWijzigFinancienPage setFieldGebruikPersoonlijkeRekening(final JaNee jaNee) {
        selectRadio(fieldGebruikPersoonlijkeRekening, jaNee);
        return this;
    }

    public FormulierWijzigFinancienPage setFieldBetalingswijze(final Betalingswijze type) {
        if (Betalingswijze.IDEAL.equals(type)) {
            if (!fieldBetalingswijzeIdeal.isSelected()) {
                fieldBetalingswijzeIdeal.click();
            }
        }
        return this;
    }

    public FormulierWijzigFinancienPage wijzigingenOpslaan() {
        scrollToBottom();
        scrollIntoViewAndClick(buttonWijzigingenOpslaan);
        return this;
    }

}
