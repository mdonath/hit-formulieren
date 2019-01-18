package nl.scouting.hit.kampinfo.export;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * Een exportregel uit KampInfoHelper met informatie voor een ScoutsOnline inschrijfformulier.
 */
public class KampInfoFormulierExportRegel {

    //"kampinfo_project_jaar": 2019
    @JsonProperty("kampinfo_project_jaar")
    private int jaar;

    //, "kampinfo_camp_id": 555
    @JsonProperty("kampinfo_camp_id")
    private int kampID;

    //, "kampinfo_project_evenement_id": 0
    @JsonProperty("kampinfo_project_evenement_id")
    private int shantiID;

    //, "frm_location_nm": "Alphen"
    @JsonProperty("frm_location_nm")
    private String locatie;

    //, "projectcode": "HIT ALPHEN 2019"
    @JsonProperty("projectcode")
    private String projectcode;

    //, "frm_nm": "Alphen Hakt!"
    @JsonProperty("frm_nm")
    private String kampnaam;

    //, "kampinfo_camp_isouder": 0
    @JsonProperty("kampinfo_camp_isouder")
    private boolean isOuderKindKamp;

    //, "frm_price": 42
    @JsonProperty("frm_price")
    private int deelnemersprijs;

    //, "frm_min_age": 14
    //, "frm_max_age": 17
    @JsonUnwrapped(prefix = "frm_", suffix = "_age")
    private KampInfoRange leeftijd;

    //, "frm_min_age_margin_days": 30
    //, "frm_max_age_margin_days": 90
    @JsonUnwrapped(prefix = "frm_", suffix = "_age_margin_days")
    private KampInfoRange leeftijdMarge;

    //, "frm_part_min_ct": 10
    //, "frm_part_max_ct": 20
    @JsonUnwrapped(prefix = "frm_part_", suffix = "_ct")
    private KampInfoRange aantalDeelnemers;

    //, "frm_max_outof_group": 0
    @JsonProperty("frm_max_outof_group")
    private int maximumAantalUitEenGroep;

    //, "fte_teams_min_ct": 0
    //, "fte_teams_max_ct": 0
    @JsonUnwrapped(prefix = "fte_teams_", suffix = "_ct")
    private KampInfoRange aantalSubgroepen;

    //, "fte_parts_min_ct": 1
    //, "fte_parts_max_ct": 3
    @JsonUnwrapped(prefix = "fte_parts_", suffix = "_ct")
    private KampInfoRange aantalDeelnemersInSubgroep;

    //, "fte_modulo": 1
    @JsonProperty("fte_modulo")
    private int deelbaarDoor;

    @JsonUnwrapped(prefix = "frm_cancel_dt1_")
    private KampInfoDatum kosteloosAnnulerenTot;

    @JsonUnwrapped(prefix = "frm_cancel_dt2_")
    private KampInfoDatum volledigeKostenAnnulerenVanaf;

    @JsonUnwrapped(prefix = "kampinfo_project_inningsdatum_")
    private KampInfoDatum projectInningsdatum;


    @JsonUnwrapped(prefix = "frm_from_dt_")
    private KampInfoDatum evenementStart;
    //, "frm_from_time": "19:00"
    @JsonProperty("frm_from_time")
    private String evenementStartTijd;


    @JsonUnwrapped(prefix = "frm_till_dt_")
    private KampInfoDatum evenementEind;
    //, "frm_till_time": "15:00"
    @JsonProperty("frm_till_time")
    private String evenementEindTijd;


    @JsonUnwrapped(prefix = "frm_book_from_dt_")
    private KampInfoDatum inschrijvingStart;

    @JsonUnwrapped(prefix = "frm_book_till_dt_")
    private KampInfoDatum inschrijvingEind;

    public final String getFormulierNaam() {
        return String.format("HIT %s %s (%d)"
                , locatie
                , kampnaam
                , kampID);
    }

    public final String getBasisformulierNaam() {
        final String okk = "HIT %d Basisformulier Ouder-Kind (niet wijzigen)";
        final String normaal = "HIT %d Basisformulier (niet wijzigen)";
        return String.format(isOuderKindKamp ? okk : normaal, jaar);
    }

    public int getJaar() {
        return jaar;
    }

    public int getKampID() {
        return kampID;
    }

    public int getShantiID() {
        return shantiID;
    }

    public String getLocatie() {
        return locatie;
    }

    public String getProjectcode() {
        return projectcode;
    }

    public String getKampnaam() {
        return kampnaam;
    }

    public boolean isOuderKindKamp() {
        return isOuderKindKamp;
    }

    public int getDeelnemersprijs() {
        return deelnemersprijs;
    }

    public KampInfoRange getLeeftijd() {
        return leeftijd;
    }

    public KampInfoRange getLeeftijdMarge() {
        return leeftijdMarge;
    }

    public KampInfoRange getAantalDeelnemers() {
        return aantalDeelnemers;
    }

    public int getMaximumAantalUitEenGroep() {
        return maximumAantalUitEenGroep;
    }

    public KampInfoRange getAantalSubgroepen() {
        return aantalSubgroepen;
    }

    public KampInfoRange getAantalDeelnemersInSubgroep() {
        return aantalDeelnemersInSubgroep;
    }

    public int getDeelbaarDoor() {
        return deelbaarDoor;
    }

    public KampInfoDatum getKosteloosAnnulerenTot() {
        return kosteloosAnnulerenTot;
    }

    public KampInfoDatum getVolledigeKostenAnnulerenVanaf() {
        return volledigeKostenAnnulerenVanaf;
    }

    public KampInfoDatum getProjectInningsdatum() {
        return projectInningsdatum;
    }

    public KampInfoDatum getEvenementStart() {
        return evenementStart;
    }

    public String getEvenementStartTijd() {
        return evenementStartTijd;
    }

    public KampInfoDatum getEvenementEind() {
        return evenementEind;
    }

    public String getEvenementEindTijd() {
        return evenementEindTijd;
    }

    public KampInfoDatum getInschrijvingStart() {
        return inschrijvingStart;
    }

    public KampInfoDatum getInschrijvingEind() {
        return inschrijvingEind;
    }
}
