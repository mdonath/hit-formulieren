package nl.scouting.hit.sol.evenement.tab.formulier.wijzig;

import nl.scouting.hit.sol.JaNee;
import nl.scouting.hit.common.Valuable;
import nl.scouting.hit.sol.evenement.tab.formulier.common.AbstractFormulierBasisPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class FormulierWijzigDeelnameconditiesPage extends AbstractFormulierBasisPage<FormulierWijzigDeelnameconditiesPage> {

    public enum InschrijvingToegstaanDoor implements Valuable {
        ALLEEN_SCOUTINGLEDEN(0),
        LEDEN_EN_NIETLEDEN(1),
        ALLEEN_KADERLEDEN(2),
        ALLEEN_LEDEN_MET_VOLGENDE_FUNCTIE(3);

        private final int id;

        InschrijvingToegstaanDoor(int id) {
            this.id = id;
        }

        public String getId() {
            return String.valueOf(id);
        }
    }

    //
    // ALGEMEEN
    //
    @FindBy(name = "frm_subscribe_once")
    private List<WebElement> fieldEenmaalInschrijven;

    @FindBy(name = "frm_participant_must_have_email_yn")
    private List<WebElement> fieldDeelnemerMoetEmailadresHebben;

    @FindBy(name = "frm_targetgroup_restricted_yn")
    private List<WebElement> fieldDeelnemerMoetInDoelgroepZitten;

    //
    // Leeftijd
    //

    @FindBy(name = "frm_min_age")
    private WebElement fieldMinimumLeeftijd;
    @FindBy(name = "frm_max_age")
    private WebElement fieldMaximumLeeftijd;

    @FindBy(name = "frm_min_age_margin_days")
    private WebElement fieldMinimumLeeftijdMarge;
    @FindBy(name = "frm_max_age_margin_days")
    private WebElement fieldMaximumLeeftijdMarge;

    //
    // Functie
    //

    @FindBy(name = "frm_who_can_subscribe")
    private List<WebElement> fieldInschrijvingToegestaanDoor;
    @FindBy(name = "frm_who_cfun_id[]")
    private WebElement fieldInschrijvingToegestaanDoorFuncties;

    //
    // Aantallen
    //

    @FindBy(name = "frm_part_min_ct")
    private WebElement fieldMinimumDeelnemersaantal;
    @FindBy(name = "frm_part_max_ct")
    private WebElement fieldMaximumDeelnemersaantal;
    @FindBy(name = "frm_waiting_list_yn")
    private List<WebElement> fieldWachtlijst;
    @FindBy(name = "frm_standard_waiting_list_yn")
    private List<WebElement> fieldStandaardWachtlijst;
    @FindBy(name = "frm_max_outof_group")
    private WebElement fieldMaximumAantalUitEengroep;
    @FindBy(name = "frm_max_outside_cooperate_orgs_ct")
    private WebElement fieldMaximumAantalExterneDeelnemers;

    //
    // Uitzonderingen
    //
    @FindBy(name = "per_exception_id")
    private WebElement fieldUitzonderingLidnummer;
    @FindBy(xpath = "//input[@value=\"Toevoegen\"]")
    private WebElement buttonUitzonderingToevoegen;

    /**
     * Button Wijzigingen apply.
     */
    @FindBy(xpath = "//input[@value=\"Wijzigingen opslaan\"]")
    private WebElement buttonWijzigingenOpslaan;

    public FormulierWijzigDeelnameconditiesPage(WebDriver driver) {
        super(driver);
    }

    public FormulierWijzigDeelnameconditiesPage withUitzonderingLidnummer(String lidnummer) {
        clearAndSendKeys(fieldUitzonderingLidnummer, lidnummer);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage toevoegenUitzondering() {
        scrollIntoViewAndClick(buttonUitzonderingToevoegen);
        return this; // ?
    }

    public FormulierWijzigDeelnameconditiesPage withEenmaalInschrijven(JaNee jaNee) {
        selectRadio(fieldEenmaalInschrijven, jaNee);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage withDeelnemerMoetEmailadresHebben(JaNee jaNee) {
        selectRadio(fieldDeelnemerMoetEmailadresHebben, jaNee);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage withDeelnemerMoetInDoelgroepZitten(JaNee jaNee) {
        selectRadio(fieldDeelnemerMoetInDoelgroepZitten, jaNee);
        return this;
    }


    public FormulierWijzigDeelnameconditiesPage withMinimumLeeftijd(int i) {
        clearAndSendKeys(fieldMinimumLeeftijd, i);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage withMaximumLeeftijd(int i) {
        clearAndSendKeys(fieldMaximumLeeftijd, i);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage withMinimumLeeftijdMarge(int i) {
        clearAndSendKeys(fieldMinimumLeeftijdMarge, i);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage withMaximumLeeftijdMarge(int i) {
        clearAndSendKeys(fieldMaximumLeeftijdMarge, i);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage withInschrijvingToegestaanDoor(InschrijvingToegstaanDoor door) {
        selectRadio(fieldInschrijvingToegestaanDoor, door);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage withInschrijvingToegestaanDoorFuncties() {
        // TODO
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage withMinimumDeelnemersaantal(int i) {
        clearAndSendKeys(fieldMinimumDeelnemersaantal, i);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage withMaximumDeelnemersaantal(int i) {
        clearAndSendKeys(fieldMaximumDeelnemersaantal, i);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage withWachtlijst(JaNee jaNee) {
        selectRadio(fieldWachtlijst, jaNee);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage withStandaardWachtlijst(JaNee jaNee) {
        selectRadio(fieldStandaardWachtlijst, jaNee);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage withMaximumAantalUitEengroep(int i) {
        clearAndSendKeys(fieldMaximumAantalUitEengroep, i);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage withMaximumAantalExterneDeelnemers(int i) {
        clearAndSendKeys(fieldMaximumAantalExterneDeelnemers, i);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage opslaanWijzigingen() {
        scrollIntoViewAndClick(buttonWijzigingenOpslaan);
        // Je komt weer terug op deze pagina
        return this;
    }
}
