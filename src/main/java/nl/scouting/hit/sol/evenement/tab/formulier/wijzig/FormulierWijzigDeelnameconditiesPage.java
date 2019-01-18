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
    @FindBy(name = "frm_max_outof_group")
    private WebElement fieldMaximumAantalUitEengroep;
    @FindBy(name = "frm_max_outside_cooperate_orgs_ct")
    private WebElement fieldMaximumAantalExterneDeelnemers;

    /**
     * Button Wijzigingen opslaan.
     */
    @FindBy(xpath = "//input[@value=\"Wijzigingen opslaan\"]")
    private WebElement buttonWijzigingenOpslaan;

    public FormulierWijzigDeelnameconditiesPage(WebDriver driver) {
        super(driver);
    }

    public FormulierWijzigDeelnameconditiesPage setFieldEenmaalInschrijven(JaNee jaNee) {
        selectRadio(fieldEenmaalInschrijven, jaNee);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage setFieldDeelnemerMoetEmailadresHebben(JaNee jaNee) {
        selectRadio(fieldDeelnemerMoetEmailadresHebben, jaNee);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage setFieldDeelnemerMoetInDoelgroepZitten(JaNee jaNee) {
        selectRadio(fieldDeelnemerMoetInDoelgroepZitten, jaNee);
        return this;
    }


    public FormulierWijzigDeelnameconditiesPage setFieldMinimumLeeftijd(int i) {
        clearAndSendKeys(fieldMinimumLeeftijd, i);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage setFieldMaximumLeeftijd(int i) {
        clearAndSendKeys(fieldMaximumLeeftijd, i);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage setFieldMinimumLeeftijdMarge(int i) {
        clearAndSendKeys(fieldMinimumLeeftijdMarge, i);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage setFieldMaximumLeeftijdMarge(int i) {
        clearAndSendKeys(fieldMaximumLeeftijdMarge, i);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage setFieldInschrijvingToegestaanDoor(InschrijvingToegstaanDoor door) {
        selectRadio(fieldInschrijvingToegestaanDoor, door);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage setFieldInschrijvingToegestaanDoorFuncties() {
        // TODO
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage setFieldMinimumDeelnemersaantal(int i) {
        clearAndSendKeys(fieldMinimumDeelnemersaantal, i);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage setFieldMaximumDeelnemersaantal(int i) {
        clearAndSendKeys(fieldMaximumDeelnemersaantal, i);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage setFieldWachtlijst(JaNee jaNee) {
        selectRadio(fieldWachtlijst, jaNee);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage setFieldMaximumAantalUitEengroep(int i) {
        clearAndSendKeys(fieldMaximumAantalUitEengroep, i);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage setFieldMaximumAantalExterneDeelnemers(int i) {
        clearAndSendKeys(fieldMaximumAantalExterneDeelnemers, i);
        return this;
    }

    public FormulierWijzigDeelnameconditiesPage wijzigingenOpslaan() {
        scrollIntoViewAndClick(buttonWijzigingenOpslaan);
        // Je komt weer terug op deze pagina
        return this;
    }
}
