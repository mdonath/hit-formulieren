package nl.scouting.hit.kampinfo;

import nl.scouting.hit.kampinfo.basis.BasisFormulierRegel;
import nl.scouting.hit.kampinfo.export.KampInfoFormulierExportRegel;
import nl.scouting.hit.kampinfo.export.KampInfoHelper;
import nl.scouting.hit.kampinfo.export.KampInfoKindFormulierExportRegel;
import nl.scouting.hit.kampinfo.export.KampInfoOuderFormulierExportRegel;
import nl.scouting.hit.sol.JaNee;
import nl.scouting.hit.sol.SolHomePage;
import nl.scouting.hit.sol.evenement.tab.formulier.TabFormulierenOverzichtPage;
import nl.scouting.hit.sol.evenement.tab.formulier.common.AbstractFormulierPage;
import nl.scouting.hit.sol.evenement.tab.formulier.nieuw.FormulierBasisNieuwPage;
import nl.scouting.hit.sol.evenement.tab.formulier.nieuw.FormulierSoortAanmeldingNieuwPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.*;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.samenstellen.AbstractVeldWijzigen;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.samenstellen.DeelnamekostenWijzigen;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.samenstellen.RadioButtonWijzigen;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.samenstellen.ToelichtendeTekstWijzigen;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.subgroepen.AbstractFormulierSubgroepenCategoriePage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.subgroepen.FormulierSubgroepenCategorieNieuwPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.subgroepen.FormulierSubgroepenCategorieWijzigPage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static nl.scouting.hit.sol.evenement.tab.formulier.wijzig.samenstellen.AbstractVeldWijzigen.VELD_GEWIJZIGD;

/**
 * Vult ScoutsOnline met de gegevens uit KampInfo.
 */
public final class ScoutsOnlineVuller {

    public static final String KOPPELGROEPJE = "Koppelgroepje";

    private final SolHomePage solHomePage;
    private final String naamEvenement;
    private final String naamSpeleenheid;
    private final Integer hitJaar;
    private String datumDeelnemersinformatie;

    private final List<KampInfoFormulierExportRegel> data;

    private static List<KampInfoFormulierExportRegel> readExportFromKampInfo(String fileName) throws IOException {
        return KampInfoHelper.downloadReadAndExpand(fileName);
    }

    /**
     * Maakt een ScoutsOnlineVuller aan.
     *
     * @param sol             de connectie naar ScoutsOnline
     * @param naamEvenement   de naam van het evenement zoals dat te zien is in het overzicht, bijvoorbeeld "HIT 2019"
     * @param naamSpeleenheid de naam van de organiserende speleenheid
     * @throws IOException Als er iets mis gaat
     */
    public ScoutsOnlineVuller(final SolHomePage sol, final String naamEvenement, final String naamSpeleenheid, final String dataFile) throws IOException {
        this.solHomePage = sol;
        this.naamEvenement = naamEvenement;
        this.hitJaar = Integer.valueOf(naamEvenement.substring(4));
        this.naamSpeleenheid = naamSpeleenheid;
        this.data = readExportFromKampInfo(Objects.requireNonNullElse(dataFile, "data") + "-" + hitJaar + ".json");
    }

    public void setDatumDeelnemersinformatie(final String datumDeelnemersinformatie) {
        this.datumDeelnemersinformatie = datumDeelnemersinformatie;
    }

    /**
     * Maakt alle formulieren actief of inactief.
     * <p>
     * Als alles actief moet worden gemaakt, maak dan eerst de basisformulieren actief. Bij deactiveren moeten eerst de
     * inschrijfformulieren worden gedeactiveerd en pas daarna mogen de basisformulieren worden gedeactiveerd. Daarom
     * wordt een predicate als parameter meegegeven.
     */
    public void maakFormulierenActief(final JaNee actief) {
        final TabFormulierenOverzichtPage tabFormulieren = solHomePage
                .hoofdmenu().openSpelVanMijnSpeleenheid(naamSpeleenheid)
                .openEvenement(naamEvenement)
                .submenu().openTabFormulieren();

        final List<HitFormulier> alleFormulieren = tabFormulieren.getFormulieren().stream()
                .map(HitFormulier::new)
                .toList();

        // Activeer eerst de basisformulieren
        maakActief(tabFormulieren, alleFormulieren, actief
                , formulier -> actief.asBoolean() == formulier.isBasisFormulier());
        // Activeer daarna de inschrijfformulieren
        maakActief(tabFormulieren, alleFormulieren, actief
                , formulier -> actief.asBoolean() != formulier.isBasisFormulier());
    }

    private void maakActief(final TabFormulierenOverzichtPage tabFormulieren, final List<HitFormulier> alleFormulieren, final JaNee actief, final Predicate<HitFormulier> formulierPredicate) {
        alleFormulieren.stream()
                // .limit(4)
                .filter(formulierPredicate)
                .forEach(formulier -> {
                    System.out.printf("Actief maken: %s ", formulier.naam);
                    tabFormulieren.openFormulier(formulier.naam)
                            .withFormulierActief(actief)
                            .opslaanWijzigingen()
                            .controleerMelding(BevestigingsTekst.FORMULIER_GEWIJZIGD)
                            .meer().naarAlleFormulieren();
                    System.out.println("[OK]");
                });
    }

    /**
     * Bij het kopiëren van het evenement van vorig jaar komen ook alle formulieren mee. Maar deze moeten allemaal
     * verwijderd worden omdat er elk jaar volledig nieuwe formulieren aangemaakt moeten worden.
     */
    public void verwijderAlleFormulieren() {
        if (true) return; // GUARD!
        final TabFormulierenOverzichtPage tabFormulieren = solHomePage
                .hoofdmenu().openSpelVanMijnSpeleenheid(naamSpeleenheid)
                .openEvenement(naamEvenement)
                .submenu().openTabFormulieren();

        tabFormulieren.getFormulieren().stream().map(HitFormulier::new)
                .filter(formulier -> formulier.kampinfoID != null)
                .forEach(formulier -> {
                    System.out.printf("deleting %s... ", formulier.naam);
                    tabFormulieren.openFormulier(formulier.naam)
                            .verwijderFormulier()
                            .ja();
                    System.out.println("[OK]");
                });
    }

    public void maakBasisFormulieren() {
        final TabFormulierenOverzichtPage formulierenOverzicht = solHomePage.hoofdmenu()
                .openSpelVanMijnSpeleenheid(naamSpeleenheid)
                .openEvenement(naamEvenement);

        final List<String> bestaandeBasisFormulierNamen = retrieveBestaandeFormulierNamen(
                formulierenOverzicht, HitFormulier::isBasisFormulier);

        System.out.println("Gevonden basisformulieren:");
        bestaandeBasisFormulierNamen.forEach(formulierNaam -> System.out.println("- " + formulierNaam));

        // Basisformulier enkelvoudige inschrijving
        final BasisFormulierRegel basisFormulierEnkelvoudig = new BasisFormulierRegel.BasisFormulierRegelBuilder()
                .withJaar(this.hitJaar)
                .withKampnaam("Basisformulier")
                .withProjectcode(this.naamEvenement)
                .withEvenementStart(10, "april", 2020, "12:00")
                .withEvenementEind(13, "april", 2020, "16:00")
                .withInschrijvingStart(19, "januari", 2020)
                .withInschrijvingEind(29, "februari", 2020)
                .build();

        final List<BasisFormulierRegel> nieuweBasisFormulieren = Collections.singletonList(
                basisFormulierEnkelvoudig
        );

        nieuweBasisFormulieren.forEach(regel -> {
            maakInitieelFormulier(regel.getFormulierNaam(), formulierenOverzicht, bestaandeBasisFormulierNamen, regel);
            final String formulierNaam = regel.getFormulierNaam();
            if (!formulierenOverzicht.hasFormulier(regel)) {
                throw new RuntimeException(String.format("Formulier %s bestaat niet!", formulierNaam));
            } else {
                System.out.println("Verwerking van formulier " + formulierNaam);
            }
            final AbstractFormulierPage<?> geopendFormulier = formulierenOverzicht.openFormulier(regel);
            vulTabBasis(geopendFormulier, regel);
            vulTabDeelnamecondities(geopendFormulier, regel);
            vulTabFinancien(geopendFormulier, regel);
            vulTabSamenstellen(geopendFormulier, regel);
            //vulTabSubgroepen(geopendFormulier, regel);
            //vulTabAanpassenMails(geopendFormulier, regel, this.datumDeelnemersinformatie);

            // Volgende!
            geopendFormulier.meer().naarAlleFormulieren();
        });
    }


    private List<String> retrieveBestaandeFormulierNamen(
            final TabFormulierenOverzichtPage formulierenOverzicht,
            final Predicate<? super HitFormulier> filter
    ) {
        return formulierenOverzicht.getFormulieren().stream()
                .map(HitFormulier::new)
                .filter(filter)
                .map(formulier -> formulier.naam)
                .toList();
    }

    /**
     * Maakt alleen de basis van elk formulier aan. In een volgende stap hoeven dan altijd alleen maar wijzigingen plaats te vinden.
     * Dat is handig als er tijdens het verwerken iets fout gaat, of er slechts een klein aspect aangepast hoeft te worden.
     */
    public void maakInitieleFormulieren() {
        final TabFormulierenOverzichtPage formulierenOverzicht = solHomePage.hoofdmenu()
                .openSpelVanMijnSpeleenheid(naamSpeleenheid)
                .openEvenement(naamEvenement);
        final List<String> bestaandeFormulierNamen = retrieveBestaandeFormulierNamen(
                formulierenOverzicht,
                HitFormulier::isInschrijfFormulier
        );

        data.stream()
                .filter(regel -> !(regel instanceof KampInfoOuderFormulierExportRegel))
                .forEach(regel ->
                        maakInitieelFormulier(regel.getFormulierNaam(), formulierenOverzicht, bestaandeFormulierNamen, regel)
                );
    }

    public void maakInitieleFormulierenVoorOuderLid() {
        final TabFormulierenOverzichtPage formulierenOverzicht = solHomePage.hoofdmenu()
                .openSpelVanMijnSpeleenheid(naamSpeleenheid)
                .openEvenement(naamEvenement);
        final List<String> bestaandeFormulierNamen = retrieveBestaandeFormulierNamen(
                formulierenOverzicht,
                HitFormulier::isInschrijfFormulier
        );

        data.stream()
                .filter(KampInfoOuderFormulierExportRegel.class::isInstance)
                .forEach(regel ->
                        maakInitieelFormulier(regel.getFormulierNaam(), formulierenOverzicht, bestaandeFormulierNamen, regel)
                );
    }

    private void maakInitieelFormulier(final String formulierNaam, final TabFormulierenOverzichtPage formulierenOverzicht, final List<String> bestaandeFormulierNamen, final KampInfoFormulierExportRegel regel) {
        if (bestaandeFormulierNamen.contains(formulierNaam)) {
            System.out.printf("Formulier %s bestaat al!%n", formulierNaam);
        } else {
            System.out.printf("creating %s", formulierNaam);
            formulierenOverzicht.toevoegenFormulier()
                    .withSoortAanmelding(FormulierSoortAanmeldingNieuwPage.SoortAanmelding.INDIVIDUELE_INSCHRIJVING)
                    .volgende()
                    .withNaamFormulier(formulierNaam)
                    .withSoortDeelnemers(FormulierBasisNieuwPage.SoortDeelnemers.PERSONEN)
                    .withProjectCode(regel.getProjectcode())
                    .withMailadresEvenement("info@hit.scouting.nl")
                    .withMailadresVoorInschrijfvragen("info@hit.scouting.nl")
                    .withEvenementStart(regel.getEvenementStart(), regel.getEvenementStartTijd())
                    .withEvenementEind(regel.getEvenementEind(), regel.getEvenementEindTijd())
                    .withInschrijvingStart(regel.getInschrijvingStart(), "12:00")
                    .withInschrijvingEind(regel.getInschrijvingEind())
                    .volgende()
                    .voltooien()
                    .controleerMelding(BevestigingsTekst.FORMULIER_TOEGEVOEGD)
                    .meer().naarAlleFormulieren();
            System.out.println(" [OK]");
        }
    }

    /**
     * Vult alle formulieren met de rest van de gegevens uit de export.
     */
    public void vulFormulierenMetDeRest() {
        final TabFormulierenOverzichtPage formulierenOverzicht = solHomePage.hoofdmenu()
                .openSpelVanMijnSpeleenheid(naamSpeleenheid)
                .openEvenement(naamEvenement);
        data.forEach(regel -> vulFormulierMetDeRest(regel.getFormulierNaam(), formulierenOverzicht, regel));
    }

    private void vulFormulierMetDeRest(final String formulierNaam, final TabFormulierenOverzichtPage formulierenOverzicht, final KampInfoFormulierExportRegel regel) {
        if (!formulierenOverzicht.hasFormulier(regel)) {
            throw new IllegalArgumentException(String.format("Formulier %s bestaat niet!", formulierNaam));
        } else {
            System.out.println("Verwerking van formulier " + formulierNaam);
        }
        final AbstractFormulierPage<?> geopendFormulier = formulierenOverzicht.openFormulier(regel);
        vulTabBasis(geopendFormulier, regel);
        vulTabDeelnamecondities(geopendFormulier, regel);
        vulTabFinancien(geopendFormulier, regel);
        vulTabSamenstellen(geopendFormulier, regel);
        vulTabSubgroepen(geopendFormulier, regel);
        vulTabAanpassenMails(geopendFormulier, this.datumDeelnemersinformatie);

        // Volgende!
        geopendFormulier.meer().naarAlleFormulieren();
    }

    private FormulierWijzigBasisPage vulTabBasis(final AbstractFormulierPage<?> geopendFormulier, final KampInfoFormulierExportRegel regel) {
        return geopendFormulier.submenu().openTabBasis()
                .withNaamFormulier(regel.getFormulierNaam())
                .withLocatie(regel.getLocatie())
                .withProjectCode(regel.getProjectcode())
                .withWebsite("https://hit.scouting.nl")
                .withWebsiteLocatie("https://hit.scouting.nl/" + regel.getLocatie().toLowerCase())
                .withLinkDeelnemersvoorwaarden("https://hit.scouting.nl/startpagina/deelnemersvoorwaarden-hit-" + regel.getJaar())
                .withEvenementStart(regel.getEvenementStart(), regel.getEvenementStartTijd())
                .withEvenementEind(regel.getEvenementEind(), regel.getEvenementEindTijd())
                .withInschrijvingStart(regel.getInschrijvingStart(), "03:00")
                .withInschrijvingEind(regel.getInschrijvingEind())
                .withStuurTicket(JaNee.NEE)
                .opslaanWijzigingen()
                .controleerMelding(BevestigingsTekst.FORMULIER_GEWIJZIGD)
                ;
    }

    private FormulierWijzigDeelnameconditiesPage vulTabDeelnamecondities(final AbstractFormulierPage<?> geopendFormulier, final KampInfoFormulierExportRegel regel) {
        return geopendFormulier.submenu().openTabDeelnamecondities()
                .withEenmaalInschrijven(JaNee.JA)
                .withDeelnemerMoetEmailadresHebben(JaNee.JA)
                .withDeelnemerMoetInDoelgroepZitten(JaNee.NEE)
                .withMinimumLeeftijd(regel.getLeeftijd().getMinimum())
                .withMaximumLeeftijd(regel.getLeeftijd().getMaximum())
                .withMinimumLeeftijdMarge(regel.getLeeftijdMarge().getMinimum())
                .withMaximumLeeftijdMarge(regel.getLeeftijdMarge().getMaximum())
                .withInschrijvingToegestaanDoor(FormulierWijzigDeelnameconditiesPage.InschrijvingToegstaanDoor.ALLEEN_SCOUTINGLEDEN)
                .withMinimumDeelnemersaantal(delenDoorTweeBijOuderKindKamp(regel, regel.getAantalDeelnemers().getMinimum()))
                .withMaximumDeelnemersaantal(delenDoorTweeBijOuderKindKamp(regel, regel.getAantalDeelnemers().getMaximum()))
                .withMaximumAantalUitEengroep(delenDoorTweeBijOuderKindKamp(regel, regel.getMaximumAantalUitEenGroep()))
                // Bij de Patsboem is die aangepast naar géén wachtlijst, daarom moet dit na de eerste totaalvulling niet meer gebeuren
                //.withWachtlijst(JaNee.JA)
                //.withStandaardWachtlijst(JaNee.JA)
                .withMaximumAantalExterneDeelnemers(0)
                .opslaanWijzigingen()
                .controleerMelding(BevestigingsTekst.FORMULIER_GEWIJZIGD)
                ;
    }

    private int delenDoorTweeBijOuderKindKamp(final KampInfoFormulierExportRegel regel, final int waarde) {
        return (regel.isOuderKindKamp()) ? waarde / 2 : waarde;
    }

    private FormulierWijzigFinancienPage vulTabFinancien(final AbstractFormulierPage<?> geopendFormulier, final KampInfoFormulierExportRegel regel) {
        return geopendFormulier.submenu().openTabFinancien()
                .withKosteloosAnnulerenTot(regel.getKosteloosAnnulerenTot())
                .withAnnuleringstype(FormulierWijzigFinancienPage.Annuleringstype.VAST_BEDRAG_VOOR_DE_HELE_INSCHRIJVING)
                .withAnnuleringsBedrag("10,00")
                .withVolledigeKostenAnnulerenVanaf(regel.getVolledigeKostenAnnulerenVanaf())
                // .withAnnuleringsredenVerplicht(JaNee.JA)
                .withGebruikGroepsrekening(FormulierWijzigFinancienPage.GebruikGroepsrekening.NIET_TOEGSTAAN)
                .withGebruikPersoonlijkeRekening(JaNee.JA)
                .withBetalingswijze(FormulierWijzigFinancienPage.Betalingswijze.IDEAL)
                .opslaanWijzigingen()
                .controleerMelding(BevestigingsTekst.FORMULIER_GEWIJZIGD)
                ;
    }

    private FormulierWijzigSamenstellenPage vulTabSamenstellen(final AbstractFormulierPage<?> geopendFormulier, final KampInfoFormulierExportRegel regel) {
        final FormulierWijzigSamenstellenPage samenstellen = geopendFormulier.submenu().openTabSamenstellen();
        // Koppel formulier aan basisformulier indien dat nog niet zo was
        final String basisformulierNaam = regel.getBasisformulierNaam();
        if (!samenstellen.isGekoppeldAanFormulier(basisformulierNaam)) {
            samenstellen
                    .setFieldSelecteerFormulier(basisformulierNaam)
                    .gebruikVeldenVanGeselecteerdFormulier()
                    .controleerMelding(BevestigingsTekst.FORMULIER_GEWIJZIGD);
        }

        // Selecteer veld 'deelnamekosten' of maak deze eerst aan indien dat nog niet zo was
        final DeelnamekostenWijzigen deelnamekosten;
        if (samenstellen.hasVeld("deelnamekosten")) {
            deelnamekosten = (DeelnamekostenWijzigen) samenstellen
                    .selecteerVeld("deelnamekosten");
        } else {
            deelnamekosten = (DeelnamekostenWijzigen) samenstellen
                    .withSelecteerNieuwVeld(FormulierWijzigSamenstellenPage.NieuwVeld.DEELNAMEKOSTEN)
                    .toevoegen();
        }
        deelnamekosten
                .withKoptekst(String.format("Deelnamekosten HIT %d", regel.getJaar()))
                .withHelptekst("Betaling kan alleen met iDEAL.  iDEAL is een online betaalmethode via internetbankieren van je eigen bank.  Het is helaas niet mogelijk om via incasso of een andere wijze te betalen voor de HIT.  Beschik je niet over iDEAL, regel de betaling dan via de rekening van iemand anders.")
                .withGrootboek("901 - Deelnemersbijdrage")
                .withGebruikOmslagdatum(JaNee.NEE)
                .withAnderBedragPerSoortLid(JaNee.NEE);

        if (regel.isOuderKindKamp()) {
            deelnamekosten
                    .createAndChangeTermijn(regel, String.format("Deelname HIT %d kind", regel.getJaar()))
                    .createAndChangeTermijn(regel, String.format("Deelname HIT %d ouder", regel.getJaar()));
        } else {
            deelnamekosten
                    .createAndChangeTermijn(regel, String.format("Deelname HIT %d", regel.getJaar()));
        }
        return deelnamekosten
                .opslaanWijzigingen()
                .controleerMelding(BevestigingsTekst.VELD_GEWIJZIGD);
    }

    private static class FormulierVelden {

        private static AbstractVeldWijzigen<?> naarVeld(final FormulierWijzigSamenstellenPage samenstellen, final int volgnummer, final FormulierWijzigSamenstellenPage.NieuwVeld soort) {
            if (samenstellen.hasVeld(String.valueOf(volgnummer))) {
                return samenstellen
                        .selecteerVeld(String.valueOf(volgnummer));
            }
            return samenstellen
                    .withSelecteerNieuwVeld(soort)
                    .toevoegen();
        }

        private static FormulierWijzigSamenstellenPage opslaan(final AbstractVeldWijzigen<?> veld) {
            return veld
                    .opslaanWijzigingen()
                    .controleerMelding(VELD_GEWIJZIGD);
        }

        public static FormulierWijzigSamenstellenPage createOrUpdateKoptekst(final int volgnummer, final FormulierWijzigSamenstellenPage.NieuwVeld soort, final String tekst, final FormulierWijzigSamenstellenPage samenstellen) {
            return opslaan(
                    naarVeld(samenstellen, volgnummer, soort)
                            .withKoptekst(tekst));
        }

        public static FormulierWijzigSamenstellenPage createOrUpdateToelichting(final int volgnummer, final String tekst, final FormulierWijzigSamenstellenPage samenstellen) {
            return opslaan(
                    ((ToelichtendeTekstWijzigen) naarVeld(samenstellen, volgnummer, FormulierWijzigSamenstellenPage.NieuwVeld.TOELICHTING))
                            .withInhoud(tekst));
        }
    }

    private FormulierWijzigSamenstellenPage vulTabSamenstellenBasisformulier(final AbstractFormulierPage<?> geopendFormulier, final KampInfoFormulierExportRegel regel) {
        final FormulierWijzigSamenstellenPage samenstellen = geopendFormulier.submenu().openTabSamenstellen();

        final String volgnummer;

        // veld 1: koptekst1
        FormulierVelden.createOrUpdateKoptekst(1
                , FormulierWijzigSamenstellenPage.NieuwVeld.KOPTEKST1
                , "Welkom bij de inschrijving voor de " + this.naamEvenement
                , samenstellen);

        FormulierVelden.createOrUpdateKoptekst(2
                , FormulierWijzigSamenstellenPage.NieuwVeld.KOPTEKST2
                , "Met dit formulier schrijf je je in voor een Ouder/Kind activiteit van de HIT. Voor het inschrijven moet het SOL account van het kind worden gebruikt, de ouder hoeft niet apart in te schrijven."
                , samenstellen);

        FormulierVelden.createOrUpdateToelichting(3
                , "Leuk dat jullie dit jaar mee willen doen aan de HIT. Lees dit formulier hieronder goed door en vul het samen zorgvuldig in. Bij vragen met een sterretje* is een antwoord verplicht. Heb je uitleg nodig, klik dan op het vraagteken. Veel succes!"
                , samenstellen);

        FormulierVelden.createOrUpdateKoptekst(2
                , FormulierWijzigSamenstellenPage.NieuwVeld.KOPTEKST2
                , "Let op! Doe de HIT inschrijving bij voorkeur op een laptop of desktop computer. De inschrijving op een tablet of telefoon kan fout gaan!"
                , samenstellen);
        {
            // veld 5: radio
            volgnummer = "5";
            final RadioButtonWijzigen radiobutton;
            if (samenstellen.hasVeld(volgnummer)) {
                radiobutton = (RadioButtonWijzigen) samenstellen.selecteerVeld(volgnummer);
            } else {
                radiobutton = (RadioButtonWijzigen) samenstellen
                        .withSelecteerNieuwVeld(FormulierWijzigSamenstellenPage.NieuwVeld.RADIOBUTTON)
                        .toevoegen();
            }
            // FIXME: radiobutton.
        }


        // Selecteer veld 'deelnamekosten' of maak deze eerst aan indien dat nog niet zo was
        final DeelnamekostenWijzigen deelnamekosten;
        if (samenstellen.hasVeld("deelnamekosten")) {
            deelnamekosten = (DeelnamekostenWijzigen) samenstellen
                    .selecteerVeld("deelnamekosten");
        } else {
            deelnamekosten = (DeelnamekostenWijzigen) samenstellen
                    .withSelecteerNieuwVeld(FormulierWijzigSamenstellenPage.NieuwVeld.DEELNAMEKOSTEN)
                    .toevoegen();
        }
        deelnamekosten
                .withKoptekst(String.format("Deelnamekosten HIT %d", regel.getJaar()))
                .withHelptekst("Betaling kan alleen met iDEAL.  iDEAL is een online betaalmethode via internetbankieren van je eigen bank.  Het is helaas niet mogelijk om via incasso of een andere wijze te betalen voor de HIT.  Beschik je niet over iDEAL, regel de betaling dan via de rekening van iemand anders.")
                .withGrootboek("901 - Deelnemersbijdrage")
                .withGebruikOmslagdatum(JaNee.NEE)
                .withAnderBedragPerSoortLid(JaNee.NEE);

        if (regel.isOuderKindKamp()) {
            deelnamekosten
                    .createAndChangeTermijn(regel, String.format("Deelname HIT %d kind", regel.getJaar()))
                    .createAndChangeTermijn(regel, String.format("Deelname HIT %d ouder", regel.getJaar()));
        } else {
            deelnamekosten
                    .createAndChangeTermijn(regel, String.format("Deelname HIT %d", regel.getJaar()));
        }
        return deelnamekosten
                .opslaanWijzigingen()
                .controleerMelding(BevestigingsTekst.VELD_GEWIJZIGD);
    }

    private FormulierWijzigSubgroepenPage vulTabSubgroepen(final AbstractFormulierPage<?> geopendFormulier, final KampInfoFormulierExportRegel regel) {
        FormulierWijzigSubgroepenPage subgroepenOverzicht = geopendFormulier.submenu().openTabSubgroepen();

        AbstractFormulierSubgroepenCategoriePage<?> nieuwOfWijzig;
        if (subgroepenOverzicht.hasSubgroepCategorie(regel.getKoppelgroepjeNaam())) {
            nieuwOfWijzig = subgroepenOverzicht.openSubgroepCategorie(regel.getKoppelgroepjeNaam());
        } else {
            nieuwOfWijzig = subgroepenOverzicht.toevoegenSubgroepCategorie();
        }

        nieuwOfWijzig
                //.withZichtbaarVoorDeelnemer(JaNee.JA)
                .withAanduiding(regel.getKoppelgroepjeNaam())
                .withAantalSubgroepen(regel.getAantalSubgroepen())
                .withAantalDeelnemers(regel.getAantalDeelnemersInSubgroep())
                .withMagSubgroepAanmaken(JaNee.JA)
                .withSubgroepVerplicht(JaNee.JA)
                .withTeltHetMaxAantalMee(JaNee.JA)
                .withContactpersoonVermelden(JaNee.JA)
                .withAantalDagenIncompleet(10)
                .withAutomatischeIncompleetMailDeelnemers(JaNee.JA)
                .withAutomatischeIncompleetMailOrganisatie(JaNee.JA);

        if (regel.isOuderKindKamp()) {
            final String toelichtingOKK = "De ouder-kind-activiteiten zijn bedoeld voor koppels die bestaan uit één kind en één " +
                    "ouder of andere volwassene. Het kind is lid van Scouting Nederland, voor de volwassene is dit " +
                    "lidmaatschap niet verplicht. Op het inschrijfformulier schrijven het kind en de volwassene zich samen " +
                    "in; het kind wordt ingeschreven als deelnemer en de volwassene als begeleider. De inschrijving via dit " +
                    "formulier is voor twee personen, en er wordt in de procedure ook voor twee personen betaald. Na " +
                    "afronding van het formulier zijn beide personen ingeschreven. Je maakt hier dus een groepje van één " +
                    "persoon aan waar je met twee personen kunt inschrijven.";
            nieuwOfWijzig = nieuwOfWijzig
                    .withToelichting(toelichtingOKK)
                    .withDeelbaarDoor(1);
        } else {
            final String toelichting = "Bij de inschrijving voor de HIT moet je eerst een koppelgroepje aanmaken of een bestaand " +
                    "koppelgroepje selecteren. Kies in het lijstje jouw koppelgroepje, of maak een nieuw koppelgroepje aan " +
                    "als jij de eerste van jouw groepje bent. Als je bij deze activiteit ook alleen mag inschrijven dan maak " +
                    "je een groepje van 1 persoon aan. Als je met meer personen inschrijft, wacht dan even tot de eerste " +
                    "zich heeft ingeschreven met het nieuwe koppelgroepje.";
            nieuwOfWijzig = nieuwOfWijzig
                    .withToelichting(toelichting)
                    .withDeelbaarDoor(regel.getDeelbaarDoor());
        }

        if (nieuwOfWijzig instanceof FormulierSubgroepenCategorieNieuwPage nieuwPage) {
            subgroepenOverzicht = nieuwPage
                    .gegevensOpslaan()
                    .controleerMelding(BevestigingsTekst.SUBGROEPCATEGORIE_OPGESLAGEN);
        } else if (nieuwOfWijzig instanceof FormulierSubgroepenCategorieWijzigPage wijzigPage) {
            subgroepenOverzicht = wijzigPage
                    .opslaanGegevens()
                    .controleerMelding(BevestigingsTekst.SUBGROEPCATEGORIE_GEWIJZIGD)
                    .terugNaarSubgroepoverzicht();
        }
        return subgroepenOverzicht;
    }

    private FormulierWijzigAanpassenMailsPage vulTabAanpassenMails(
            final AbstractFormulierPage<?> geopendFormulier,
            final String datumDeelnemersInformatie
    ) {
        return geopendFormulier.submenu().openTabAanpassenMails()

                .withSelecteerBericht(FormulierWijzigAanpassenMailsPage.Bericht.BEVESTIGING_ANNULERING_VAN_DEELNEMER_AAN_ORGANISATIE)
                .laadBericht()
                .withSoortBericht(FormulierWijzigAanpassenMailsPage.SoortBericht.GEWIJZIGD_BERICHT)
                .withGewijzigdBericht(MailTekstGenerator.annuleringsmailVanDeelnemerAanOrganisatie(this.hitJaar))
                .wijzigingenOpslaan()
                .controleerMelding(BevestigingsTekst.MAIL_GEWIJZIGD)

                .withSelecteerBericht(FormulierWijzigAanpassenMailsPage.Bericht.BEVESTIGING_INSCHRIJVING_AAN_DEELNEMER)
                .laadBericht()
                .withSoortBericht(FormulierWijzigAanpassenMailsPage.SoortBericht.GEWIJZIGD_BERICHT)
                .withGewijzigdBericht(MailTekstGenerator.bevestigingVanInschrijvingAanDeelnemer(datumDeelnemersInformatie))
                .wijzigingenOpslaan()
                .controleerMelding(BevestigingsTekst.MAIL_GEWIJZIGD)

                .withSelecteerBericht(FormulierWijzigAanpassenMailsPage.Bericht.BEVESTIGING_INSCHRIJVING_AAN_OUDERS)
                .laadBericht()
                .withSoortBericht(FormulierWijzigAanpassenMailsPage.SoortBericht.GEWIJZIGD_BERICHT)
                .withGewijzigdBericht(MailTekstGenerator.bevestigingVanInschrijvingAanOudersVerzorgers())
                .wijzigingenOpslaan()
                .controleerMelding(BevestigingsTekst.MAIL_GEWIJZIGD)

                .withSelecteerBericht(FormulierWijzigAanpassenMailsPage.Bericht.STATUSWIJZIGING_NAAR_KOSTELOOS_GEANNULEERD)
                .laadBericht()
                .withSoortBericht(FormulierWijzigAanpassenMailsPage.SoortBericht.GEWIJZIGD_BERICHT)
                .withGewijzigdBericht(MailTekstGenerator.mailBijStatuswijzigingNaarKosteloosGeannuleerd())
                .wijzigingenOpslaan()
                .controleerMelding(BevestigingsTekst.MAIL_GEWIJZIGD)

                .withSelecteerBericht(FormulierWijzigAanpassenMailsPage.Bericht.BEVESTIGING_WACHTLIJST_AAN_DEELNEMER)
                .laadBericht()
                .withSoortBericht(FormulierWijzigAanpassenMailsPage.SoortBericht.GEWIJZIGD_BERICHT)
                .withGewijzigdBericht(MailTekstGenerator.bevestigingVanWachtlijstAanDeelnemer(this.hitJaar))
                .wijzigingenOpslaan()
                .controleerMelding(BevestigingsTekst.MAIL_GEWIJZIGD)
                ;
    }

}
