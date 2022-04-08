package nl.scouting.hit.kampinfo.export;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * Een exportregel uit KampInfoHelper met informatie voor een ScoutsOnline inschrijfformulier.
 */
public class KampInfoFormulierExportRegel {

    //"kampinfo_project_jaar": 2019
    @JsonProperty("kampinfo_project_jaar")
    protected int jaar;

    //, "kampinfo_camp_id": 555
    @JsonProperty("kampinfo_camp_id")
    protected int kampID;

    //, "kampinfo_project_evenement_id": 0
    @JsonProperty("kampinfo_project_evenement_id")
    protected int shantiID;

    //, "frm_location_nm": "Alphen"
    @JsonProperty("frm_location_nm")
    protected String locatie;

    //, "projectcode": "HIT ALPHEN 2019"
    @JsonProperty("projectcode")
    protected String projectcode;

    //, "frm_nm": "Alphen Hakt!"
    @JsonProperty("frm_nm")
    protected String kampnaam;

    //, "kampinfo_camp_isouder": 0
    @JsonProperty("kampinfo_camp_isouder")
    protected boolean isOuderKindKamp;

    //, "kampinfo_camp_ouderlid": 0
    @JsonProperty("kampinfo_camp_ouderlid")
    protected boolean isOuderLid;

    @JsonProperty("kampinfo_camp_shanti_id")
    protected int kampShantiID;
    @JsonProperty("kampinfo_camp_shanti_ouder_id")
    protected int kampShantiOuderID;
    @JsonProperty("kampinfo_camp_shanti_extra_id")
    protected int kampShantiExtraID;

    //, "frm_price": 42
    @JsonProperty("frm_price")
    protected int deelnemersprijs;

    //, "frm_min_age": 14
    //, "frm_max_age": 17
    @JsonUnwrapped(prefix = "frm_", suffix = "_age")
    protected KampInfoRange leeftijd;

    //, "frm_min_age_margin_days": 30
    //, "frm_max_age_margin_days": 90
    @JsonUnwrapped(prefix = "frm_", suffix = "_age_margin_days")
    protected KampInfoRange leeftijdMarge;

    //, "frm_part_min_ct": 10
    //, "frm_part_max_ct": 20
    @JsonUnwrapped(prefix = "frm_part_", suffix = "_ct")
    protected KampInfoRange aantalDeelnemers;

    //, "frm_max_outof_group": 0
    @JsonProperty("frm_max_outof_group")
    protected int maximumAantalUitEenGroep;

    //, "fte_teams_min_ct": 0
    //, "fte_teams_max_ct": 0
    @JsonUnwrapped(prefix = "fte_teams_", suffix = "_ct")
    protected KampInfoRange aantalSubgroepen;

    //, "fte_parts_min_ct": 1
    //, "fte_parts_max_ct": 3
    @JsonUnwrapped(prefix = "fte_parts_", suffix = "_ct")
    protected KampInfoRange aantalDeelnemersInSubgroep;

    //, "fte_modulo": 1
    @JsonProperty("fte_modulo")
    protected int deelbaarDoor;

    @JsonUnwrapped(prefix = "frm_cancel_dt1_")
    protected KampInfoDatum kosteloosAnnulerenTot;

    @JsonUnwrapped(prefix = "frm_cancel_dt2_")
    protected KampInfoDatum volledigeKostenAnnulerenVanaf;

    @JsonUnwrapped(prefix = "kampinfo_project_inningsdatum_")
    protected KampInfoDatum projectInningsdatum;


    @JsonUnwrapped(prefix = "frm_from_dt_")
    protected KampInfoDatum evenementStart;
    //, "frm_from_time": "19:00"
    @JsonProperty("frm_from_time")
    protected String evenementStartTijd;


    @JsonUnwrapped(prefix = "frm_till_dt_")
    protected KampInfoDatum evenementEind;
    //, "frm_till_time": "15:00"
    @JsonProperty("frm_till_time")
    protected String evenementEindTijd;


    @JsonUnwrapped(prefix = "frm_book_from_dt_")
    protected KampInfoDatum inschrijvingStart;

    @JsonUnwrapped(prefix = "frm_book_till_dt_")
    protected KampInfoDatum inschrijvingEind;

    protected final void copy(final KampInfoFormulierExportRegel regel) {
        this.jaar = regel.jaar;
        this.kampID = regel.kampID;
        this.shantiID = regel.shantiID;
        this.locatie = regel.locatie;
        this.projectcode = regel.projectcode;
        this.kampnaam = regel.kampnaam;
        this.isOuderKindKamp = regel.isOuderKindKamp;
        this.isOuderLid = regel.isOuderLid;
        this.kampShantiID = regel.kampShantiID;
        this.kampShantiOuderID = regel.kampShantiOuderID;
        this.kampShantiExtraID = regel.kampShantiExtraID;
        this.deelnemersprijs = regel.deelnemersprijs;
        this.leeftijd = regel.leeftijd;
        this.leeftijdMarge = regel.leeftijdMarge;
        this.aantalDeelnemers = regel.aantalDeelnemers;
        this.maximumAantalUitEenGroep = regel.maximumAantalUitEenGroep;
        this.aantalSubgroepen = regel.aantalSubgroepen;
        this.aantalDeelnemersInSubgroep = regel.aantalDeelnemersInSubgroep;
        this.deelbaarDoor = regel.deelbaarDoor;
        this.kosteloosAnnulerenTot = regel.kosteloosAnnulerenTot;
        this.volledigeKostenAnnulerenVanaf = regel.volledigeKostenAnnulerenVanaf;
        this.projectInningsdatum = regel.projectInningsdatum;
        this.evenementStart = regel.evenementStart;
        this.evenementStartTijd = regel.evenementStartTijd;
        this.evenementEind = regel.evenementEind;
        this.evenementEindTijd = regel.evenementEindTijd;
        this.inschrijvingStart = regel.inschrijvingStart;
        this.inschrijvingEind = regel.inschrijvingEind;
    }

    public String getFormulierNaam() {
        return buildKampNaam(false);
    }

    protected final String buildKampNaam(final boolean isOuderFormulier) {
        final String firstPart = String.format("HIT %s %s"
                , locatie
                , cleanKampnaam(kampnaam));
        final String specifier = getSpecifier(isOuderFormulier);
        final String ref = getReference(isOuderFormulier);
        final String idPart = String.format(" %s(%d)%s", specifier, kampID, ref);

        if (firstPart.length() + idPart.length() > 70) {
            final String truncatedFirstPart = firstPart.substring(0, 70 - idPart.length());
            return (truncatedFirstPart + idPart).replace("  ", " ");
        }
        return firstPart + idPart;
    }

    private String getReference(final boolean isOuderFormulier) {
        if (isOuderKindKamp && isOuderLid && isOuderFormulier && kampShantiID > 0) {
            return String.format(" ((%s))", kampShantiID);
        }
        return "";
    }

    private String getSpecifier(final boolean isOuderFormulier) {
        if (isOuderKindKamp && isOuderLid) {
            if (isOuderFormulier) {
                return "(OUDER) ";
            } else {
                return "(KIND) ";
            }
        }
        return "";
    }

    static String cleanKampnaam(final String naam) {
        return naam.replaceAll("[^a-zA-Z0-9\\-\\(\\)\\?,\\. \\pL]", "")
                .replace("â", "a")
                .replace("ë", "e")
                .replace("  ", " ");
    }

    public String getBasisformulierNaam() {
        if (isOuderKindKamp) {
            if (isOuderLid) {
                return String.format("HIT %d Basisformulier Ouder-Kind (optie OUDER) (niet wijzigen)", jaar);
            }
            return String.format("HIT %d Basisformulier Ouder-Kind (niet wijzigen)", jaar);
        }
        return String.format("HIT %d Basisformulier (niet wijzigen)", jaar);
    }

    /**
     * Omdat voor koppelgroepjes bij Ouder Kind kampen het aantal mensen dat zich via één formulier inschrijft in het koppelgroepjes moet staan.
     */
    public String getKoppelgroepjeNaam() {
        return "Koppelgroepje";
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

    public boolean isOuderLid() {
        return isOuderLid;
    }

    public int getKampShantiID() {
        return kampShantiID;
    }

    public int getKampShantiOuderID() {
        return kampShantiOuderID;
    }

    public int getKampShantiExtraID() {
        return kampShantiExtraID;
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
