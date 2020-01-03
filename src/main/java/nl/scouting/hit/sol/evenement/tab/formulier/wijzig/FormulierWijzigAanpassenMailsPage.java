package nl.scouting.hit.sol.evenement.tab.formulier.wijzig;

import nl.scouting.hit.common.Valuable;
import nl.scouting.hit.sol.evenement.tab.formulier.common.AbstractFormulierPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class FormulierWijzigAanpassenMailsPage extends AbstractFormulierPage<FormulierWijzigAanpassenMailsPage> {


    public enum Bericht implements Valuable {

        // Annuleringsmail van deelnemer aan organisatie
        BEVESTIGING_ANNULERING_VAN_DEELNEMER_AAN_ORGANISATIE("frm_txt_cancelmail_by_participant"),

        @Deprecated
        BEVESTIGING_ANNULERING_AAN_DEELNEMER_DOOR_ORGANISATIE("frm_txt_cancelconfirmmail_by_staff"),

        // Bevestiging van inschrijving aan deelnemer
        BEVESTIGING_INSCHRIJVING_AAN_DEELNEMER("frm_txt_confirmationmail_member"),

        // Bevestiging van inschrijving aan organisator
        BEVESTIGING_INSCHRIJVING_AAN_ORGANISATIE("frm_txt_registration_mail_for_organisator"),

        // Bevestiging van inschrijving aan ouders/verzorgers
        BEVESTIGING_INSCHRIJVING_AAN_OUDERS("frm_txt_confirmationmail_parent"),

        // Bevestiging van wachtlijst aan deelnemer
        BEVESTIGING_WACHTLIJST_AAN_DEELNEMER("frm_txt_confirmationmail_member_waitinglist"),

        // Bij groepsinschrijving als nog niet alle individuele deelnemers geregistreerd zijn
        GROEP_NOG_INCOMPLEET("frm_txt_group_incomplete_for_contact"),

        // Mail bij statuswijziging inschrijving aan deelnemer en organisator
        STATUSWIJZIGING_AAN_DEELNEMER_EN_ORGANISATOR("frm_txt_state_part_changed"),

        // Mail bij statuswijziging naar 'kosteloos geannuleerd'
        STATUSWIJZIGING_NAAR_KOSTELOOS_GEANNULEERD("frm_txt_as_mail_state_part_refused"),

        // Mail naar organisator bij wijziging van inschrijving
        WIJZIGING_AAN_ORGANISATOR("frm_txt_registration_edit_mail_for_organisator"),

        // mail naar deelnemer als de inschrijving niet door de penningmeester is goedgekeurd
        AFKEURING_DOOR_PENNINGMEESTER_AAN_DEELNEMER("frm_txt_as_mail_group_account_refused"),

        // mail naar penningmeester bij wijziging bedrag groepsrekening
        BEDRAGWIJZIGING_AAN_PENNINGMEESTER("frm_txt_form_change_treasurer");

        private final String id;

        Bericht(String id) {
            this.id = id;
        }

        @Override
        public String getId() {
            return id;
        }
    }

    public enum SoortBericht implements Valuable {
        STANDAARD_BERICHT(1),
        GEWIJZIGD_BERICHT(0);


        private final String id;

        SoortBericht(int id) {
            this.id = String.valueOf(id);
        }

        @Override
        public String getId() {
            return id;
        }
    }

    @FindBy(id = "switch_mail_type")
    private WebElement fieldSelecteerBericht;

    @FindBy(xpath = "//input[@value='Laad bericht']")
    private WebElement buttonLaadBericht;

    @FindBy(name = "default_yn")
    private List<WebElement> fieldSoortBericht;

    @FindBy(name = "custom_txt")
    private WebElement fieldGewijzigdBericht;

    @FindBy(xpath = "//*[@id=\"buttonbar\"]/div[1]/input[2]")
    private WebElement buttonWijzigingenOpslaan;

    public FormulierWijzigAanpassenMailsPage(WebDriver driver) {
        super(driver);
    }

    public FormulierWijzigAanpassenMailsPage withSelecteerBericht(Bericht bericht) {
        selectByValue(fieldSelecteerBericht, bericht.id);
        return this;
    }

    public FormulierWijzigAanpassenMailsPage laadBericht() {
        scrollIntoViewAndClick(buttonLaadBericht);
        return this;
    }

    public FormulierWijzigAanpassenMailsPage withSoortBericht(SoortBericht bericht) {
        selectRadio(fieldSoortBericht, bericht);
        return this;
    }

    public FormulierWijzigAanpassenMailsPage withGewijzigdBericht(String s) {
        clearAndSendKeys(fieldGewijzigdBericht, s);
        return this;
    }

    public FormulierWijzigAanpassenMailsPage wijzigingenOpslaan() {
        scrollIntoViewAndClick(buttonWijzigingenOpslaan);
        return this;
    }

}
