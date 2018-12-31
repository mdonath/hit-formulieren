package nl.scouting.hit.sol;

import nl.scouting.hit.kampinfo.KampInfo;
import nl.scouting.hit.kampinfo.KampInfoFormulierExportRegel;
import nl.scouting.hit.sol.evenement.tab.formulier.TabFormulierenOverzichtPage;
import nl.scouting.hit.sol.evenement.tab.formulier.common.AbstractFormulierPage;
import nl.scouting.hit.sol.evenement.tab.formulier.nieuw.FormulierBasisNieuwPage;
import nl.scouting.hit.sol.evenement.tab.formulier.nieuw.FormulierSoortAanmeldingNieuwPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.*;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.samenstellen.DeelnamekostenWijzigen;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.subgroepen.AbstractFormulierSubgroepenCategoriePage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.subgroepen.FormulierSubgroepenCategorieNieuwPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.subgroepen.FormulierSubgroepenCategorieWijzigPage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

public class ScoutsOnline implements AutoCloseable {

    public static final String KOPPELGROEPJE = "Koppelgroepje";
    private final WebDriver driver;
    private final SolHomePage solHomePage;

    public static void main(String[] args) throws Exception {
        String baseUrl = "https://sol.scouting.nl";
        String username = "martijn_donath";
        String password = Util.readPasswordFromFile();
        try (final ScoutsOnline sol = new ScoutsOnline(baseUrl, username, password)) {
            // sol.verwijderAlleFormulieren("HIT 2019");

            final List<KampInfoFormulierExportRegel> data = readExportFromKampInfo();

            // sol.maakInitieleFormulieren("HIT 2019", data);

            sol.vulFormulierenMetDeRest("HIT 2019", data);
        }
    }

    private static List<KampInfoFormulierExportRegel> readExportFromKampInfo() throws IOException {
        KampInfo.download();
        return KampInfo.readData();
    }

    /**
     * Constructor.
     *
     * @param baseUrl  de url waar Scouts Online te vinden is, kan ook een testomgeving zijn
     * @param username de gebruikersnaam van het account dat moet inloggen en de juiste rechten heeft om evenementen aan te maken
     * @param password het wachtwoord van het account dat moet inloggen
     */
    public ScoutsOnline(final String baseUrl, final String username, final String password) {
        this.driver = Util.getWebDriver();
        this.solHomePage = getSolHomePage(driver, baseUrl, username, password);
    }

    /**
     * Bij het kopiëren van het evenement van vorig jaar komen ook alle formulieren mee. Maar deze moeten allemaal
     * verwijderd worden omdat er elk jaar volledig nieuwe formulieren aangemaakt moeten worden.
     *
     * @param naamEvenement
     */
    public void verwijderAlleFormulieren(final String naamEvenement) {
        if (true) return; // GUARD!
        final TabFormulierenOverzichtPage tabFormulieren = solHomePage
                .hoofdmenu().openSpelVanMijnSpeleenheid()
                .openEvenement(naamEvenement)
                .submenu().openTabFormulieren();

        tabFormulieren.getFormulieren().stream()
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
     *
     * @param naamEvenement de naam van het evenement zoals dat te zien is in het overzicht, bijvoorbeeld "HIT 2019"
     * @param data          de export uit KampInfo
     */
    public void maakInitieleFormulieren(final String naamEvenement, final List<KampInfoFormulierExportRegel> data) {
        final TabFormulierenOverzichtPage formulierenOverzicht = solHomePage.hoofdmenu().openSpelVanMijnSpeleenheid()
                .openEvenement(naamEvenement);

        final List<String> bestaandeFormulierNamen = formulierenOverzicht.getFormulieren().stream()
                .filter(formulier -> formulier.kampinfoID != null)
                .map(formulier -> formulier.naam)
                .collect(Collectors.toList());

        data.forEach(regel -> {
            final String formulierNaam = regel.getFormulierNaam();
            if (bestaandeFormulierNamen.contains(formulierNaam)) {
                System.out.printf("Formulier %s bestaat al!\n", formulierNaam);
            } else {
                formulierenOverzicht.toevoegenFormulier()
                        .setFieldSoortAanmelding(FormulierSoortAanmeldingNieuwPage.SoortAanmelding.INDIVIDUELE_INSCHRIJVING)
                        .volgende()
                        .setFieldNaamFormulier(formulierNaam)
                        .setFieldSoortDeelnemers(FormulierBasisNieuwPage.SoortDeelnemers.PERSONEN)
                        .setfieldProjectCode(regel.getProjectcode())
                        .setFieldMailadresEvenement("info@hit.scouting.nl")
                        .setFieldMailadresVoorInschrijfvragen("info@hit.scouting.nl")
                        .setFieldsEvenementStart(regel.getEvenementStart(), regel.getEvenementStartTijd())
                        .setFieldsEvenementEind(regel.getEvenementEind(), regel.getEvenementEindTijd())
                        .setFieldsInschrijvingStart(regel.getInschrijvingStart(), "10:00")
                        .setFieldsInschrijvingEind(regel.getInschrijvingEind())
                        .volgende()
                        .voltooien()
                        .controleerMelding("Formulier toegevoegd")
                        .meer().naarAlleFormulieren();
            }
        });
    }

    /**
     * Vult alle formulieren met de rest van de gegevens uit de export.
     *
     * @param naamEvenement de naam van het evenement ("HIT 2019")
     * @param data          de export uit KampInfo
     */
    public void vulFormulierenMetDeRest(final String naamEvenement, final List<KampInfoFormulierExportRegel> data) {
        final TabFormulierenOverzichtPage formulierenOverzicht = solHomePage.hoofdmenu().openSpelVanMijnSpeleenheid()
                .openEvenement(naamEvenement);
        data.stream()
                .skip(12)
                .forEach(regel -> {
                    final String formulierNaam = regel.getFormulierNaam();
                    if (!formulierenOverzicht.hasFormulier(regel)) {
                        throw new RuntimeException(String.format("Formulier %s bestaat niet!", formulierNaam));
                    } else {
                        System.out.println("Verwerking van formulier "+ formulierNaam);
                    }
                    AbstractFormulierPage<?> geopendFormulier = formulierenOverzicht.openFormulier(regel);
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
                .setFieldNaamFormulier(regel.getFormulierNaam())
                .setFieldLocatie(regel.getLocatie())
                .setfieldProjectCode(regel.getProjectcode())
                .setFieldWebsite("https://hit.scouting.nl")
                .setFieldWebsiteLocatie("https://hit.scouting.nl/" + regel.getLocatie().toLowerCase())
                .setFieldLinkDeelnemersvoorwaarden("https://hit.scouting.nl/startpagina/deelnemersvoorwaarden-hit-" + regel.getJaar())
                .setFieldsEvenementStart(regel.getEvenementStart(), regel.getEvenementStartTijd())
                .setFieldsEvenementEind(regel.getEvenementEind(), regel.getEvenementEindTijd())
                .setFieldsInschrijvingStart(regel.getInschrijvingStart(), "10:00")
                .setFieldsInschrijvingEind(regel.getInschrijvingEind())
                .setFieldStuurTicket(JaNee.NEE)
                .wijzigingenOpslaan()
                .controleerMelding("Formulier gewijzigd")
                ;
    }

    private FormulierWijzigDeelnameconditiesPage vulTabDeelnamecondities(final AbstractFormulierPage<?> geopendFormulier, final KampInfoFormulierExportRegel regel) {
        return geopendFormulier.submenu().openTabDeelnamecondities()
                .setFieldEenmaalInschrijven(JaNee.JA)
                .setFieldDeelnemerMoetEmailadresHebben(JaNee.JA)
                .setFieldDeelnemerMoetInDoelgroepZitten(JaNee.NEE)
                .setFieldMinimumLeeftijd(regel.getLeeftijd().getMinimum())
                .setFieldMaximumLeeftijd(regel.getLeeftijd().getMaximum())
                .setFieldMinimumLeeftijdMarge(regel.getLeeftijdMarge().getMinimum())
                .setFieldMaximumLeeftijdMarge(regel.getLeeftijdMarge().getMaximum())
                .setFieldInschrijvingToegestaanDoor(FormulierWijzigDeelnameconditiesPage.InschrijvingToegstaanDoor.ALLEEN_SCOUTINGLEDEN)
                .setFieldMinimumDeelnemersaantal(delenDoorTweeBijOuderKindKamp(regel, regel.getAantalDeelnemers().getMinimum()))
                .setFieldMaximumDeelnemersaantal(delenDoorTweeBijOuderKindKamp(regel, regel.getAantalDeelnemers().getMaximum()))
                .setFieldMaximumAantalUitEengroep(delenDoorTweeBijOuderKindKamp(regel, regel.getMaximumAantalUitEenGroep()))
                .setFieldWachtlijst(JaNee.NEE)
                .setFieldMaximumAantalExterneDeelnemers(0)
                .wijzigingenOpslaan()
                .controleerMelding("Formulier gewijzigd")
                ;
    }

    private int delenDoorTweeBijOuderKindKamp(KampInfoFormulierExportRegel regel, int waarde) {
        return (regel.isOuderKindKamp()) ? waarde / 2 : waarde;
    }

    private FormulierWijzigFinancienPage vulTabFinancien(final AbstractFormulierPage<?> geopendFormulier, final KampInfoFormulierExportRegel regel) {
        return geopendFormulier.submenu().openTabFinancien()
                .setFieldsKosteloosAnnulerenTot(regel.getKosteloosAnnulerenTot())
                .setFieldAnnuleringstype(FormulierWijzigFinancienPage.Annuleringstype.VAST_BEDRAG_VOOR_DE_HELE_INSCHRIJVING)
                .setFieldAnnuleringsBedrag("10,00")
                .setFieldsVolledigeKostenAnnulerenVanaf(regel.getVolledigeKostenAnnulerenVanaf())
                .setFieldAnnuleringsredenVerplicht(JaNee.JA)
                .setFieldGebruikGroepsrekening(FormulierWijzigFinancienPage.GebruikGroepsrekening.NIET_TOEGSTAAN)
                .setFieldGebruikPersoonlijkeRekening(JaNee.JA)
                .setFieldBetalingswijze(FormulierWijzigFinancienPage.Betalingswijze.IDEAL)
                .wijzigingenOpslaan()
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
        if (samenstellen.heeftVeld("deelnamekosten")) {
            deelnamekosten = (DeelnamekostenWijzigen) samenstellen
                    .selecteerVeld("deelnamekosten");
        } else {
            deelnamekosten = (DeelnamekostenWijzigen) samenstellen
                    .setFieldSelecteerNieuwVeld(FormulierWijzigSamenstellenPage.NieuwVeld.DEELNAMEKOSTEN)
                    .toevoegen();
        }
        deelnamekosten
                .setFieldKoptekst(String.format("Deelnamekosten HIT %d", regel.getJaar()))
                .setFieldHelptekst("Betaling kan alleen met iDEAL.  iDEAL is een online betaalmethode via internetbankieren van je eigen bank.  Het is helaas niet mogelijk om via incasso of een andere wijze te betalen voor de HIT.  Beschik je niet over iDEAL, regel de betaling dan via de rekening van iemand anders.")
                .setFieldGrootboek("901 - Deelnemersbijdrage")
                .setFieldGebruikOmslagdatum(JaNee.NEE)
                .setFieldAnderBedragPerSoortLid(JaNee.NEE);

        if (regel.isOuderKindKamp()) {
            deelnamekosten
                    .createAndChangeTermijn(regel, String.format("Deelname HIT %d kind", regel.getJaar()))
                    .createAndChangeTermijn(regel, String.format("Deelname HIT %d ouder", regel.getJaar()));
        } else {
            deelnamekosten
                    .createAndChangeTermijn(regel, String.format("Deelname HIT %d", regel.getJaar()));
        }
        return deelnamekosten
                .wijzigingenOpslaan()
                .controleerMelding("Veld gewijzigd");
    }

    private FormulierWijzigSubgroepenPage vulTabSubgroepen(final AbstractFormulierPage<?> geopendFormulier, final KampInfoFormulierExportRegel regel) {
        FormulierWijzigSubgroepenPage subgroepenOverzicht = geopendFormulier.submenu().openTabSubgroepen();

        AbstractFormulierSubgroepenCategoriePage nieuwOfWijzig;
        if (subgroepenOverzicht.heeftSubgroepCategorie(KOPPELGROEPJE)) {
            nieuwOfWijzig = subgroepenOverzicht.openSubgroepCategorie(KOPPELGROEPJE);
        } else {
            nieuwOfWijzig = subgroepenOverzicht.toevoegenSubgroepCategorie();
        }

        nieuwOfWijzig
                //.setFieldZichtbaarVoorDeelnemer(JaNee.JA)
                .setFieldAanduiding(KOPPELGROEPJE)
                .setFieldsAantalSubgroepen(regel.getAantalSubgroepen())
                .setFieldsAantalDeelnemers(regel.getAantalDeelnemersInSubgroep())
                .setFieldMagSubgroepAanmaken(JaNee.JA)
                .setFieldSubgroepVerplicht(JaNee.JA)
                .setFieldTeltHetMaxAantalMee(JaNee.JA)
                .setFieldContactpersoonVermelden(JaNee.JA)
                .setFieldAantalDagenIncompleet(10)
                .setFieldAutomatischeIncompleetMailDeelnemers(JaNee.JA)
                .setFieldAutomatischeIncompleetMailOrganisatie(JaNee.JA);

        if (regel.isOuderKindKamp()) {
            final String toelichtingOKK = "De ouder-kind-activiteiten zijn bedoeld voor koppels die bestaan uit één kind en één " +
                    "ouder of andere volwassene. Het kind is lid van Scouting Nederland, voor de volwassene is dit " +
                    "lidmaatschap niet verplicht. Op het inschrijfformulier schrijven het kind en de volwassene zich samen " +
                    "in; het kind wordt ingeschreven als deelnemer en de volwassene als begeleider. De inschrijving via dit " +
                    "formulier is voor twee personen, en er wordt in de procedure ook voor twee personen betaald. Na " +
                    "afronding van het formulier zijn beide personen ingeschreven. Je maakt hier dus een groepje van één " +
                    "persoon aan waar je met twee personen kunt inschrijven.";
            nieuwOfWijzig = nieuwOfWijzig
                    .setFieldToelichting(toelichtingOKK)
                    .setFieldDeelbaarDoor(1);
        } else {
            final String toelichting = "Bij de inschrijving voor de HIT moet je eerst een koppelgroepje aanmaken of een bestaand " +
                    "koppelgroepje selecteren. Kies in het lijstje jouw koppelgroepje, of maak een nieuw koppelgroepje aan " +
                    "als jij de eerste van jouw groepje bent. Als je bij deze activiteit ook alleen mag inschrijven dan maak " +
                    "je een groepje van 1 persoon aan. Als je met meer personen inschrijft, wacht dan even tot de eerste " +
                    "zich heeft ingeschreven met het nieuwe koppelgroepje.";
            nieuwOfWijzig = nieuwOfWijzig
                    .setFieldToelichting(toelichting)
                    .setFieldDeelbaarDoor(regel.getDeelbaarDoor());
        }

        if (nieuwOfWijzig instanceof FormulierSubgroepenCategorieNieuwPage) {
            subgroepenOverzicht = ((FormulierSubgroepenCategorieNieuwPage) nieuwOfWijzig)
                    .gegevensOpslaan()
                    .controleerMelding("Subgroepcategorie is opgeslagen.");
        } else if (nieuwOfWijzig instanceof FormulierSubgroepenCategorieWijzigPage) {
            subgroepenOverzicht = ((FormulierSubgroepenCategorieWijzigPage) nieuwOfWijzig)
                    .gegevensOpslaan()
                    .controleerMelding("Wijzigen subgroepcategorie gelukt")
                    .terugNaarSubgroepoverzicht();
        }
        return subgroepenOverzicht;
    }

    private FormulierWijzigAanpassenMailsPage vulTabAanpassenMails(final AbstractFormulierPage<?> geopendFormulier, final KampInfoFormulierExportRegel regel, final String datumDeelnemersInformatie) {
        return geopendFormulier.submenu().openTabAanpassenMails()
                .setFieldSelecteerBericht(FormulierWijzigAanpassenMailsPage.Bericht.BEVESTIGING_ANNULERING_AAN_DEELNEMER_DOOR_ORGANISATIE)
                .laadBericht()
                .setSoortBericht(FormulierWijzigAanpassenMailsPage.SoortBericht.GEWIJZIGD_BERICHT)
                .setFieldGewijzigdBericht("Beste %per_fullname%,\n\n" +
                        "De evenementbeheerder heeft je annulering van het evenement %evt_nm% (%frm_nm%) bevestigd.\n\n" +
                        "Je bent nu afgemeld voor dit evenement.\n\n" +
                        "Met vriendelijke groet,\n\n" +
                        "Het HIT-team")
                .wijzigingenOpslaan()
                .controleerMelding("Mail gewijzigd")

                .setFieldSelecteerBericht(FormulierWijzigAanpassenMailsPage.Bericht.BEVESTIGING_INSCHRIJVING_AAN_DEELNEMER)
                .laadBericht()
                .setSoortBericht(FormulierWijzigAanpassenMailsPage.SoortBericht.GEWIJZIGD_BERICHT)
                .setFieldGewijzigdBericht(
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

                .setFieldSelecteerBericht(FormulierWijzigAanpassenMailsPage.Bericht.BEVESTIGING_INSCHRIJVING_AAN_OUDERS)
                .laadBericht()
                .setSoortBericht(FormulierWijzigAanpassenMailsPage.SoortBericht.GEWIJZIGD_BERICHT)
                .setFieldGewijzigdBericht(
                        "Beste ouder/verzorger van %per_fullname%,\n\n" +
                                "%per_fullname% heeft zich opgegeven als deelnemer voor %frm_nm% tijdens de HIT " + regel.getJaar() + ".\n" +
                                "Deze mail dient uitsluitend om u te informeren over deze inschrijving. Alle verdere correspondentie zal via het in Scouts Online geregistreerde e-mailadres %per_email% van %per_fullname% verlopen.\n" +
                                "Deze activiteit start op %frm_from_dt% om %frm_from_time% uur en duurt tot %frm_till_dt% tot %frm_till_time% uur.\n" +
                                "De deelnamekosten voor deze activiteit zijn " +
                                (regel.isOuderKindKamp() ? "twee maal" : "") +
                                "%frm_price% euro.\n\n" +
                                "De deelnemer heeft na inschrijving 10 dagen bedenktijd en kan binnen deze 10 dagen kosteloos annuleren. Binnen deze tijd kun je kosteloos annuleren. Je krijgt dan het deelnamegeld helemaal teruggestort. Dit is ook de termijn waarbinnen het groepje compleet moet zijn. Na 10 dagen kan er nog wel geannuleerd worden maar krijg de deelnemer het deelnamegeld terug verminderd met € 10 administratiekosten. Na de sluiting van de inschrijving kan er niet meer geannuleerd worden en krijgt de deelnemer geen geld meer terug.\n\n" +
                                "Zijn er problemen met inschrijven of heeft u een andere vraag? Kijk dan op http://hit.scouting.nl bij het onderdeel ’inschrijven’.\n\n" +
                                "Met vriendelijke groet,\n\n" +
                                "De HIT-organisatie\n" +
                                "Scouting Nederland")
                .wijzigingenOpslaan()
                .controleerMelding("Mail gewijzigd")

                .setFieldSelecteerBericht(FormulierWijzigAanpassenMailsPage.Bericht.STATUSWIJZIGING_NAAR_KOSTELOOS_GEANNULEERD)
                .laadBericht()
                .setSoortBericht(FormulierWijzigAanpassenMailsPage.SoortBericht.GEWIJZIGD_BERICHT)
                .setFieldGewijzigdBericht(
                        "Beste %per_fullname%,\n\n" +
                                "Je inschrijving voor het evenement %evt_nm% (%frm_nm%) is kosteloos geannuleerd.\n" +
                                "Je bent niet meer ingeschreven voor dit evenement.\n\n" +
                                "De HIT organisatie")
                ;
    }

    private SolHomePage getSolHomePage(WebDriver driver, String baseUrl, String username, String password) {
        driver.get(baseUrl);

        PreLoginPage preLoginPage = new PreLoginPage(driver, baseUrl);
        LoginPage loginPage = preLoginPage.login();
        return loginPage.login(username, password);
    }

    @Override
    public void close() throws Exception {
        Files.copy(
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).toPath()
                , new File("screenshot.png").toPath()
                , StandardCopyOption.REPLACE_EXISTING);
        driver.quit();
    }
}