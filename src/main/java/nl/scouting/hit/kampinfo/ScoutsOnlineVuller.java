package nl.scouting.hit.kampinfo;

import nl.scouting.hit.kampinfo.export.KampInfoFormulierExportRegel;
import nl.scouting.hit.kampinfo.export.KampInfoHelper;
import nl.scouting.hit.sol.JaNee;
import nl.scouting.hit.sol.SolHomePage;
import nl.scouting.hit.sol.evenement.tab.formulier.Formulier;
import nl.scouting.hit.sol.evenement.tab.formulier.TabFormulierenOverzichtPage;
import nl.scouting.hit.sol.evenement.tab.formulier.common.AbstractFormulierPage;
import nl.scouting.hit.sol.evenement.tab.formulier.nieuw.FormulierBasisNieuwPage;
import nl.scouting.hit.sol.evenement.tab.formulier.nieuw.FormulierSoortAanmeldingNieuwPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.*;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.samenstellen.DeelnamekostenWijzigen;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.subgroepen.AbstractFormulierSubgroepenCategoriePage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.subgroepen.FormulierSubgroepenCategorieNieuwPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.subgroepen.FormulierSubgroepenCategorieWijzigPage;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Vult ScoutsOnline met de gegevens uit KampInfo.
 */
public final class ScoutsOnlineVuller {

    private static final String KOPPELGROEPJE = "Koppelgroepje";

    private final SolHomePage solHomePage;
    private final String naamEvenement;
    private final String naamSpeleenheid;

    private final List<KampInfoFormulierExportRegel> data;

    private static List<KampInfoFormulierExportRegel> readExportFromKampInfo() throws IOException {
        KampInfoHelper.download();
        return KampInfoHelper.readData();
    }

    /**
     * Maakt een ScoutsOnlineVuller aan.
     *
     * @param sol             de connectie naar ScoutsOnline
     * @param naamEvenement   de naam van het evenement zoals dat te zien is in het overzicht, bijvoorbeeld "HIT 2019"
     * @param naamSpeleenheid de naam van de organiserende speleenheid
     * @throws IOException
     */
    public ScoutsOnlineVuller(final SolHomePage sol, final String naamEvenement, final String naamSpeleenheid) throws IOException {
        this.solHomePage = sol;
        this.naamEvenement = naamEvenement;
        this.naamSpeleenheid = naamSpeleenheid;
        this.data = readExportFromKampInfo();
    }

    /**
     * Maak alle formulieren actief of inactief.
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

        // Activeer eerst de basisformulieren
        final List<HitFormulier> alleFormulieren = tabFormulieren.getFormulieren().stream().map(HitFormulier::new).collect(Collectors.toList());
        maakActief(tabFormulieren, alleFormulieren, actief
                , formulier -> actief.asBoolean() == formulier.isBasisFormulier());
        // Activeer daarna de inschrijfformulieren
        maakActief(tabFormulieren, alleFormulieren, actief
                , formulier -> actief.asBoolean() != formulier.isBasisFormulier());
    }

    protected void maakActief(final TabFormulierenOverzichtPage tabFormulieren, final List<HitFormulier> alleFormulieren, final JaNee actief, final Predicate<HitFormulier> formulierPredicate) {
        alleFormulieren.stream()
                // .limit(4)
                .filter(formulierPredicate)
                .forEach(formulier -> {
                    System.out.println(formulier.naam);
                    tabFormulieren.openFormulier(formulier.naam)
                            .withFormulierActief(actief)
                            .opslaanWijzigingen()
                            .controleerMelding("Formulier gewijzigd")
                            .meer().naarAlleFormulieren();
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

    /**
     * Maakt alleen de basis van elk formulier aan. In een volgende stap hoeven dan altijd alleen maar wijzigingen plaats te vinden.
     * Dat is handig als er tijdens het verwerken iets fout gaat, of er slechts een klein aspect aangepast hoeft te worden.
     */
    public void maakInitieleFormulieren() {
        final TabFormulierenOverzichtPage formulierenOverzicht = solHomePage.hoofdmenu()
                .openSpelVanMijnSpeleenheid(naamSpeleenheid)
                .openEvenement(naamEvenement);

        final List<String> bestaandeFormulierNamen = formulierenOverzicht.getFormulieren().stream()
                .map(HitFormulier::new)
                .filter(formulier -> formulier.kampinfoID != null)
                .map(formulier -> formulier.naam)
                .collect(Collectors.toList());

        data.forEach(regel -> {
            final String formulierNaam = regel.getFormulierNaam();
            if (bestaandeFormulierNamen.contains(formulierNaam)) {
                System.out.printf("Formulier %s bestaat al!\n", formulierNaam);
            } else {
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
                        .withInschrijvingStart(regel.getInschrijvingStart(), "10:00")
                        .withInschrijvingEind(regel.getInschrijvingEind())
                        .volgende()
                        .voltooien()
                        .controleerMelding("Formulier toegevoegd")
                        .meer().naarAlleFormulieren();
            }
        });
    }

    /**
     * Vult alle formulieren met de rest van de gegevens uit de export.
     */
    public void vulFormulierenMetDeRest() {
        final TabFormulierenOverzichtPage formulierenOverzicht = solHomePage.hoofdmenu()
                .openSpelVanMijnSpeleenheid(naamSpeleenheid)
                .openEvenement(naamEvenement);
        data.stream()
                .forEach(regel -> {
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
                    vulTabSubgroepen(geopendFormulier, regel);
                    vulTabAanpassenMails(geopendFormulier, regel, "18 maart 2019");

                    // Volgende!
                    geopendFormulier.meer().naarAlleFormulieren();
                });
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
                .withInschrijvingStart(regel.getInschrijvingStart(), "10:00")
                .withInschrijvingEind(regel.getInschrijvingEind())
                .withStuurTicket(JaNee.NEE)
                .opslaanWijzigingen()
                .controleerMelding("Formulier gewijzigd")
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
                .withWachtlijst(JaNee.NEE)
                .withMaximumAantalExterneDeelnemers(0)
                .opslaanWijzigingen()
                .controleerMelding("Formulier gewijzigd")
                ;
    }

    private int delenDoorTweeBijOuderKindKamp(KampInfoFormulierExportRegel regel, int waarde) {
        return (regel.isOuderKindKamp()) ? waarde / 2 : waarde;
    }

    private FormulierWijzigFinancienPage vulTabFinancien(final AbstractFormulierPage<?> geopendFormulier, final KampInfoFormulierExportRegel regel) {
        return geopendFormulier.submenu().openTabFinancien()
                .withKosteloosAnnulerenTot(regel.getKosteloosAnnulerenTot())
                .withAnnuleringstype(FormulierWijzigFinancienPage.Annuleringstype.VAST_BEDRAG_VOOR_DE_HELE_INSCHRIJVING)
                .withAnnuleringsBedrag("10,00")
                .withVolledigeKostenAnnulerenVanaf(regel.getVolledigeKostenAnnulerenVanaf())
                .withAnnuleringsredenVerplicht(JaNee.JA)
                .withGebruikGroepsrekening(FormulierWijzigFinancienPage.GebruikGroepsrekening.NIET_TOEGSTAAN)
                .withGebruikPersoonlijkeRekening(JaNee.JA)
                .withBetalingswijze(FormulierWijzigFinancienPage.Betalingswijze.IDEAL)
                .opslaanWijzigingen()
                .controleerMelding("Formulier gewijzigd")
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
                    .controleerMelding("Formulier gewijzigd");
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
                .controleerMelding("Veld gewijzigd");
    }

    private FormulierWijzigSubgroepenPage vulTabSubgroepen(final AbstractFormulierPage<?> geopendFormulier, final KampInfoFormulierExportRegel regel) {
        FormulierWijzigSubgroepenPage subgroepenOverzicht = geopendFormulier.submenu().openTabSubgroepen();

        AbstractFormulierSubgroepenCategoriePage nieuwOfWijzig;
        if (subgroepenOverzicht.hasSubgroepCategorie(KOPPELGROEPJE)) {
            nieuwOfWijzig = subgroepenOverzicht.openSubgroepCategorie(KOPPELGROEPJE);
        } else {
            nieuwOfWijzig = subgroepenOverzicht.toevoegenSubgroepCategorie();
        }

        nieuwOfWijzig
                //.withZichtbaarVoorDeelnemer(JaNee.JA)
                .withAanduiding(KOPPELGROEPJE)
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

        if (nieuwOfWijzig instanceof FormulierSubgroepenCategorieNieuwPage) {
            subgroepenOverzicht = ((FormulierSubgroepenCategorieNieuwPage) nieuwOfWijzig)
                    .gegevensOpslaan()
                    .controleerMelding("Subgroepcategorie is opgeslagen.");
        } else if (nieuwOfWijzig instanceof FormulierSubgroepenCategorieWijzigPage) {
            subgroepenOverzicht = ((FormulierSubgroepenCategorieWijzigPage) nieuwOfWijzig)
                    .opslaanGegevens()
                    .controleerMelding("Wijzigen subgroepcategorie gelukt")
                    .terugNaarSubgroepoverzicht();
        }
        return subgroepenOverzicht;
    }

    private FormulierWijzigAanpassenMailsPage vulTabAanpassenMails(final AbstractFormulierPage<?> geopendFormulier, final KampInfoFormulierExportRegel regel, final String datumDeelnemersInformatie) {
        return geopendFormulier.submenu().openTabAanpassenMails()
                .withSelecteerBericht(FormulierWijzigAanpassenMailsPage.Bericht.BEVESTIGING_ANNULERING_AAN_DEELNEMER_DOOR_ORGANISATIE)
                .laadBericht()
                .withSoortBericht(FormulierWijzigAanpassenMailsPage.SoortBericht.GEWIJZIGD_BERICHT)
                .withGewijzigdBericht("Beste %per_fullname%,\n\n" +
                        "De evenementbeheerder heeft je annulering van het evenement %evt_nm% (%frm_nm%) bevestigd.\n\n" +
                        "Je bent nu afgemeld voor dit evenement.\n\n" +
                        "Met vriendelijke groet,\n\n" +
                        "Het HIT-team")
                .wijzigingenOpslaan()
                .controleerMelding("Mail gewijzigd")

                .withSelecteerBericht(FormulierWijzigAanpassenMailsPage.Bericht.BEVESTIGING_INSCHRIJVING_AAN_DEELNEMER)
                .laadBericht()
                .withSoortBericht(FormulierWijzigAanpassenMailsPage.SoortBericht.GEWIJZIGD_BERICHT)
                .withGewijzigdBericht(
                        "Betreft: Bevestiging inschrijving HIT " + regel.getJaar() + "\n" +
                                "(bewaar dit mailtje goed)\n\n" +
                                "Beste %per_fullname%,\n\n" +
                                "Wat leuk! Je hebt je opgegeven voor een spectaculair weekend tijdens de HIT " + regel.getJaar() +
                                " voor het onderdeel %frm_nm%. Deze activiteit start op %frm_from_dt% om %frm_from_time% uur en duurt tot %frm_till_dt% tot %frm_till_time% uur.\n\n" +
                                "De deelnamekosten voor deze activiteit zijn " + (regel.isOuderKindKamp() ? "twee maal" : "") +
                                "%frm_price% euro. Als je deze via iDEAL voldaan hebt, is je inschrijving compleet. Als er iets mis ging met de betaling of je bent door iemand anders, zoals je teamleider, ingeschreven is jouw betaling nog niet compleet. Je ontvangt dan een aparte mail met een betalingsverzoek en een iDEAL-link om je betaling te voldoen. Pas dan ben je officieel ingeschreven.\n\n" +
                                "Na je inschrijving heb je 10 dagen bedenktijd. Binnen deze tijd kun je kosteloos annuleren. Je krijgt dan het deelnamegeld helemaal teruggestort. Dit is ook de termijn waarbinnen jouw groepje compleet moet zijn. Na 10 dagen kun je nog wel annuleren maar krijg je het deelnamegeld terug verminderd met € 10 administratiekosten. Na de sluiting van de inschrijving kun je niet meer annuleren en krijg je geen geld meer terug.\n\n" +
                                "Annuleren kan alleen door in te loggen op sol.scouting.nl en in het ‘Mijn Scouting’-menu bij ‘Mijn inschrijvingen’ naar de HIT-inschrijving te gaan. Daar kun je in het tabblad ‘deelnamestatus’ jouw inschrijving annuleren. Vul altijd een reden in. Is er sprake van bijzondere omstandigheden waardoor je moet annuleren, neem dan altijd contact op met de HIT-Helpdesk. Na de sluiting vaan de inschrijving is het niet meer mogelijk om te annuleren! Wel is het mogelijk om een andere deelnemer voor jou in de plaats te laten deelnemen.\n\n" +
                                "We willen je er ook op attenderen dat het soms voorkomt dat een HIT-onderdeel niet doorgaat. We geven dit dan natuurlijk zo snel mogelijk aan je door. Je krijgt dan een aantal alternatieve activiteiten aangeboden of je kunt kosteloos annuleren.\n\n" +
                                "Via Scouts Online (https://sol.scouting.nl) kun je de inschrijving van jouw groepje bekijken. Klik na het inloggen op ‘Mijn Scouting’ – ‘Mijn inschrijvingen’ en kies daar de inschrijving van de HIT. Onder ‘subgroep(en)’ zie je wie zich al voor jouw groepje heeft aangemeld. Dit aanmelden moet binnen 10 dagen. Dus schud je groepje wakker en zorg dat ze zich op tijd inschrijven! \n\n" +
                                "Zijn er problemen met inschrijven of heb je een andere vraag? Kijk dan op http://hit.scouting.nl bij het onderdeel ‘Inschrijven’ of mail de helpdesk via helpdesk@hit.scouting.nl. \n" +
                                "Rond " + datumDeelnemersInformatie + " zal de deelnemersinformatie van %frm_nm% beschikbaar zijn op de HIT-website. Daarin staat o.a. waar je moet zijn en wat je mee moet nemen. We sturen je een mail als de informatie van jouw HIT onderdeel beschikbaar is.\n\n" +
                                "Tot ziens op de HIT.\n\n" +
                                "De HIT organisatie\n" +
                                "Scouting Nederland\n\n" +
                                "PS: Je lidnummer is: %per_id%\n" +
                                "En je inschrijfnummer is: %prt_id%")
                .wijzigingenOpslaan()
                .controleerMelding("Mail gewijzigd")

                .withSelecteerBericht(FormulierWijzigAanpassenMailsPage.Bericht.BEVESTIGING_INSCHRIJVING_AAN_OUDERS)
                .laadBericht()
                .withSoortBericht(FormulierWijzigAanpassenMailsPage.SoortBericht.GEWIJZIGD_BERICHT)
                .withGewijzigdBericht(
                        "Beste ouder/verzorger van %per_fullname%,\n\n" +
                                "%per_fullname% heeft zich opgegeven als deelnemer voor %frm_nm% tijdens de HIT " + regel.getJaar() + ".\n" +
                                "Deze mail dient uitsluitend om u te informeren over deze inschrijving. Alle verdere correspondentie zal via het in Scouts Online geregistreerde e-mailadres %per_email% van %per_fullname% verlopen.\n" +
                                "Deze activiteit start op %frm_from_dt% om %frm_from_time% uur en duurt tot %frm_till_dt% tot %frm_till_time% uur.\n" +
                                "De deelnamekosten voor deze activiteit zijn " + (regel.isOuderKindKamp() ? "twee maal" : "") + "%frm_price% euro.\n\n" +
                                "De deelnemer heeft na inschrijving 10 dagen bedenktijd en kan binnen deze 10 dagen kosteloos annuleren. Binnen deze tijd kun je kosteloos annuleren. Je krijgt dan het deelnamegeld helemaal teruggestort. Dit is ook de termijn waarbinnen het groepje compleet moet zijn. Na 10 dagen kan er nog wel geannuleerd worden maar krijg de deelnemer het deelnamegeld terug verminderd met € 10 administratiekosten. Na de sluiting van de inschrijving kan er niet meer geannuleerd worden en krijgt de deelnemer geen geld meer terug.\n\n" +
                                "Zijn er problemen met inschrijven of heeft u een andere vraag? Kijk dan op http://hit.scouting.nl bij het onderdeel ’inschrijven’.\n\n" +
                                "Met vriendelijke groet,\n\n" +
                                "De HIT-organisatie\n" +
                                "Scouting Nederland")
                .wijzigingenOpslaan()
                .controleerMelding("Mail gewijzigd")

                .withSelecteerBericht(FormulierWijzigAanpassenMailsPage.Bericht.STATUSWIJZIGING_NAAR_KOSTELOOS_GEANNULEERD)
                .laadBericht()
                .withSoortBericht(FormulierWijzigAanpassenMailsPage.SoortBericht.GEWIJZIGD_BERICHT)
                .withGewijzigdBericht(
                        "Beste %per_fullname%,\n\n" +
                                "Je inschrijving voor het evenement %evt_nm% (%frm_nm%) is kosteloos geannuleerd.\n" +
                                "Je bent niet meer ingeschreven voor dit evenement.\n\n" +
                                "De HIT organisatie")
                ;
    }

}
