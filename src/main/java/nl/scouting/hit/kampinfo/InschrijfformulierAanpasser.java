package nl.scouting.hit.kampinfo;

import nl.scouting.hit.sol.HitContext;
import nl.scouting.hit.sol.JaNee;
import nl.scouting.hit.sol.SolHomePage;
import nl.scouting.hit.sol.evenement.tab.formulier.TabFormulierenOverzichtPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.FormulierWijzigBasisPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.FormulierWijzigSubgroepenPage;

import java.util.Locale;

/**
 * Vult ScoutsOnline met de gegevens uit KampInfo.
 */
public final class InschrijfformulierAanpasser {

    private final SolHomePage solHomePage;
    private final String naamEvenement;
    private final String idEvenement;
    private final String naamSpeleenheid;
    private final String jaar;
    private String datumDeelnemersinformatie;

    /*
     * Aanpassingen van 2020:
     * - in de mailtekst bij Ouder Kind Kampen is al de totaal prijs beschikbaar in %frm_price% en dan moet er niet bij gezegd worden dat het twee keer dat bedrag is.
     */

    /*
     * Aanpassingen van 2022:
     * - starttijd inschrijving is 12:00 uur
     * - kosteloos annuleren == einde inschrijving
     */

    /*
     * Aanpassingen van 2023:
     * - subgroepjes maken de wachtlijst vol waardoor deelnemers zich niet meer kunnen aanmelden in fase 1
     */

    /*
     * Aanpassingen van 2024:
     * - starttijdstip is 03:00 uur.
     * - subgroepjesgrootte van ouder-kind-kampen moeten altijd op 1 staan, ongeacht wat ingevuld is in KampInfo.
     * - Bij de ouder-is-lid formulieren is het handig om in fase 1 de minmax van aantal deelnemers op 0/0 te zetten zodat de statistieken niet vertroebeld worden.
     */

    /**
     * Maakt een InschrijfformulierAanpasser aan.
     *
     * @param sol             de connectie naar ScoutsOnline
     * @param idEvenement     het shantiID van het evenement
     * @param naamEvenement   de naam van het evenement zoals dat te zien is in het overzicht, bijvoorbeeld "HIT 2019"
     * @param naamSpeleenheid de naam van de organiserende speleenheid
     */
    public InschrijfformulierAanpasser(final SolHomePage sol, final String idEvenement, final String naamEvenement, final String naamSpeleenheid) {
        this.solHomePage = sol;
        this.idEvenement = idEvenement;
        this.naamEvenement = naamEvenement;
        this.jaar = naamEvenement.split(" ")[1];
        this.naamSpeleenheid = naamSpeleenheid;
    }

    public InschrijfformulierAanpasser(final SolHomePage sol, final HitContext context) {
        this(sol, context.idEvenement, context.naamEvenement, context.naamSpeleenheid);
        setDatumDeelnemersinformatie(context.datumDeelnemersinformatie);
    }

    public void setDatumDeelnemersinformatie(final String datumDeelnemersinformatie) {
        this.datumDeelnemersinformatie = datumDeelnemersinformatie;
    }

    private String getEvenementLink() {
        return naamEvenement + " (Nr. " + idEvenement + ")";
    }

    /**
     * - FIX 1: Zet begintijdstip inschrijving op 03:00 uur -> beter dan 's middags 12:00 uur
     * - FIX 2: Zet de koppelgroepgrootte op 1 bij alle ouder-kind kampen -> elk subgroepje mag maar uit één formulier (met daarop 2 personen) bestaan
     */
    public void foutjesInAanmakenFormulieren2024() {

        final TabFormulierenOverzichtPage tabFormulieren = openTabFormulieren();
        tabFormulieren.getFormulieren().stream()
                .map(HitFormulier::new)
                .filter(formulier -> formulier.isInschrijfFormulier() || formulier.isGekoppeldFormulier())
                .filter(formulier -> formulier.naam.contains("HIT Heerenveen")
                        || formulier.naam.contains("HIT Mook")
                        || formulier.naam.contains("HIT Ommen")
                        || formulier.naam.contains("HIT Zeeland")
                )
                .forEach(formulier -> {
                    System.out.printf("Reparatie formulier %s na aanmaken 2024", formulier.naam);

                    final FormulierWijzigBasisPage formulierWijzigBasisPage = tabFormulieren
                            .openFormulier(formulier.naam);

                    // FIX 1: begintijdstip inschrijving op 03:00
                    formulierWijzigBasisPage
                            .withInschrijvingStarttijd("03:00")
                            .opslaanWijzigingen()
                            .controleerMelding(BevestigingsTekst.FORMULIER_GEWIJZIGD);

                    // FIX 2: koppelgroepgrootte op 1 bij alle ouder-kind kampen
                    if (formulier.naam.toLowerCase(Locale.ROOT).contains("ouder")) {
                        formulierWijzigBasisPage.submenu().openTabSubgroepen()
                                .openSubgroepCategorie(ScoutsOnlineVuller.KOPPELGROEPJE)
                                .withMinimumAantalDeelnemers(1)
                                .withMaximumAantalDeelnemers(1)
                                .opslaanGegevens()
                                .controleerMelding(BevestigingsTekst.SUBGROEPCATEGORIE_GEWIJZIGD);
                    }

                    System.out.println(" [OK]");

                    // Terug naar overzicht met alle formulieren
                    formulierWijzigBasisPage.meer().naarAlleFormulieren();
                });
    }

    /**
     * Past de inschrijfformulieren aan voor fase 2 waarbij er geen wachtlijsten meer zijn.
     */
    public void fase2() {
        final TabFormulierenOverzichtPage tabFormulieren = openTabFormulieren();

        tabFormulieren.getFormulieren().stream()
                .map(HitFormulier::new)
                .filter(formulier -> formulier.isInschrijfFormulier() || formulier.isGekoppeldFormulier())
                .forEach(formulier -> {
                    System.out.printf("Aanpassen formulier %s voor fase 2", formulier.naam);
                    final FormulierWijzigBasisPage formulierWijzigBasisPage = tabFormulieren
                            .openFormulier(formulier.naam);

                    System.out.printf(" (%d %d %d) ",
                            formulier.aantalDeelnemers,
                            formulier.gereserveerd,
                            formulier.maximumAantalDeelnemers
                    );
                    // STAP EEN: inschrijfperiode FIXME: Dit zijn nog de datums van 2023
                    formulierWijzigBasisPage
                            .withInschrijvingStart(4, 2, 2024)
                            .withInschrijvingStarttijd("03:00")
                            .withInschrijvingEind(11, 2, 2024)
                            .opslaanWijzigingen()
                            .controleerMelding(BevestigingsTekst.FORMULIER_GEWIJZIGD);

                    // STAP TWEE: verwijder wachtlijst
                    formulierWijzigBasisPage.submenu().openTabDeelnamecondities()
                            .withWachtlijst(JaNee.NEE)
                            .withStandaardWachtlijst(JaNee.NEE)
                            .opslaanWijzigingen()
                            .controleerMelding(BevestigingsTekst.FORMULIER_GEWIJZIGD);

                    // STAP DRIE: herstel limiet op aantal koppelgroepjes
                    formulierWijzigBasisPage.submenu().openTabSubgroepen()
                            .openSubgroepCategorie(ScoutsOnlineVuller.KOPPELGROEPJE)
                            .withTeltHetMaxAantalMee(JaNee.JA)
                            .opslaanGegevens()
                            .controleerMelding(BevestigingsTekst.SUBGROEPCATEGORIE_GEWIJZIGD)
                    ;
                    System.out.print(" [GEEN WACHTLIJST MEER]");
                    tabFormulieren.clickLink(getEvenementLink());
                    System.out.println(" [OK]");
                });
    }

    private TabFormulierenOverzichtPage openTabFormulieren() {
        return solHomePage
                .hoofdmenu().openSpelVanMijnSpeleenheid(naamSpeleenheid)
                .openEvenement(naamEvenement)
                .submenu().openTabFormulieren();
    }

}
