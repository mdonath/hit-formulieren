package nl.scouting.hit.sol.evenement.tab.formulier.wijzig;

import nl.scouting.hit.sol.AbstractPage;
import nl.scouting.hit.sol.JaNee;
import nl.scouting.hit.sol.Valuable;
import nl.scouting.hit.sol.evenement.tab.formulier.TabFormulierenOverzichtPage;
import nl.scouting.hit.sol.evenement.tab.formulier.common.AbstractFormulierBasisPage;
import nl.scouting.hit.sol.evenement.tab.formulier.verwijder.FormulierVerwijderenPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class FormulierWijzigBasisPage extends AbstractFormulierBasisPage<FormulierWijzigBasisPage> {
    /**
     * Type barcode voor tickets, tickets kunnen meegezonden worden met bevestiging inschrijving.
     */
    public enum TypeBarcode implements Valuable {
        QRCODE(1), BARCODE(0);

        public final int id;

        TypeBarcode(final int id) {
            this.id = id;
        }

        public String getId() {
            return String.valueOf(id);
        }
    }

    // FIELDS //
    @FindBy(name = "frm_on_off")
    private List<WebElement> fieldFormulierActief;

    @FindBy(name = "frm_location_nm")
    private WebElement fieldLocatie;

    @FindBy(name = "frm_url")
    private WebElement fieldWebsite;

    @FindBy(name = "frm_location_url")
    private WebElement fieldWebsiteLocatie;

    @FindBy(name = "frm_part_condition_url")
    private WebElement fieldLinkDeelnemersvoorwaarden;

    @FindBy(name = "frm_send_ticket_yn")
    private List<WebElement> fieldStuurTicket;

    @FindBy(name = "frm_ticket_barcode_1_2_d") // 0: 1D/bar | 1: 2D/QR
    private List<WebElement> fieldTypeBarcode;

    @FindBy(name = "frm_ticket_text")
    private WebElement fieldTicketTekst;

    @FindBy(name = "frm_change_till_dt_day")
    private WebElement fieldInschrijvingWijzigenDag;

    @FindBy(name = "frm_change_till_dt_month")
    private WebElement fieldInschrijvingWijzigenMaand;

    @FindBy(name = "frm_change_till_dt_year")
    private WebElement fieldInschrijvingWijzigenJaar;

    // BUTTONS //
    @FindBy(xpath = "//*[@id=\"buttonbar\"]/div[1]/input")
    private WebElement buttonWijzigingenOpslaan;

    @FindBy(linkText = "Formulier verwijderen")
    private WebElement buttonFormulierVerwijderen;

    /**
     * Constructor.
     * @param driver
     */
    public FormulierWijzigBasisPage(final WebDriver driver) {
        super(driver);
    }

    public FormulierWijzigBasisPage setFieldFormulierActief(final JaNee jaNee) {
        selectRadio(fieldFormulierActief, jaNee);
        return this;
    }

    public FormulierWijzigBasisPage setFieldLocatie(final String s) {
        clearAndSendKeys(fieldLocatie, s);
        return this;
    }

    public FormulierWijzigBasisPage setFieldWebsite(final String s) {
        clearAndSendKeys(fieldWebsite, s);
        return this;
    }

    public FormulierWijzigBasisPage setFieldWebsiteLocatie(final String s) {
        clearAndSendKeys(fieldWebsiteLocatie, s);
        return this;
    }

    public FormulierWijzigBasisPage setFieldLinkDeelnemersvoorwaarden(final String s) {
        clearAndSendKeys(fieldLinkDeelnemersvoorwaarden, s);
        return this;
    }

    public FormulierWijzigBasisPage setFieldStuurTicket(final JaNee jaNee) {
        selectRadio(fieldStuurTicket, jaNee);
        return this;
    }

    public FormulierWijzigBasisPage setFieldTypeBarcode(final TypeBarcode typeBarcode) {
        selectRadio(fieldTypeBarcode, typeBarcode);
        return this;
    }

    public FormulierWijzigBasisPage setFieldTicketTekst() {
        return this;
    }

    public FormulierWijzigBasisPage setFieldsInschrijvingWijzigen(final int dag, final int maand, final int jaar) {
        selectByValue(fieldInschrijvingWijzigenDag, String.format("%02d", dag));
        selectByValue(fieldInschrijvingWijzigenMaand, String.format("%02d", maand));
        selectByValue(fieldInschrijvingWijzigenJaar, String.format("%04d", jaar));
        return this;
    }

    public FormulierWijzigBasisPage wijzigingenOpslaan() {
        scrollIntoViewAndClick(buttonWijzigingenOpslaan);
        // Je komt weer terug op deze pagina
        return this;
    }

    public FormulierVerwijderenPage verwijderFormulier() {
        scrollIntoViewAndClick(buttonFormulierVerwijderen);
        return new FormulierVerwijderenPage(driver);
    }

}
