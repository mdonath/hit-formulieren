package nl.scouting.hit.kampinfo;

import nl.scouting.hit.sol.HitContext;
import nl.scouting.hit.sol.JaNee;
import nl.scouting.hit.sol.SolHomePage;
import nl.scouting.hit.sol.evenement.tab.formulier.TabFormulierenOverzichtPage;
import nl.scouting.hit.sol.evenement.tab.formulier.common.AbstractFormulierPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.FormulierWijzigAanpassenMailsPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.FormulierWijzigBasisPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.FormulierWijzigDeelnameconditiesPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.FormulierWijzigSamenstellenPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.samenstellen.CheckboxWijzigen;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.samenstellen.MeerdereTekstRegelsWijzigen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;

import static nl.scouting.hit.kampinfo.BevestigingsTekst.FORMULIER_GEWIJZIGD;

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
     * Fixt de de volgende zaken die fout waren gegenereerd voor HIT 2020.
     * - start inschrijving is zondag 19-01-2020 en niet 18-01-2020
     * - datum tot wanneer kosteloos annuleren is 29-02-2020 (gelijk aan sluitingsdatum inschrijving)
     */
    public void postfixHIT2020() {
        final TabFormulierenOverzichtPage tabFormulieren = openTabFormulieren();

        tabFormulieren.getFormulieren().stream()
                .map(HitFormulier::new)
                .filter(formulier -> formulier.isInschrijfFormulier() || formulier.isGekoppeldFormulier())
                .forEach(formulier -> {
                    System.out.printf("Fixing formulier %s", formulier.naam);
                    tabFormulieren.openFormulier(formulier.naam)
                            .withInschrijvingStart(19, 1, 2020)
                            .opslaanWijzigingen()
                            .controleerMelding(FORMULIER_GEWIJZIGD)

                            .submenu().openTabFinancien()
                            .withVolledigeKostenAnnulerenVanaf(29, 2, 2020)
                            .opslaanWijzigingen()
                            .controleerMelding(FORMULIER_GEWIJZIGD)
                    ;
                    tabFormulieren.clickLink(getEvenementLink());
                    System.out.println(" [OK]");
                });
    }

    /**
     * Fixt de de volgende zaken die fout waren gegenereerd voor HIT 2020.
     * - in de mailtekst bij Ouder Kind Kampen is al de totaal prijs beschikbaar in %frm_price% en dan moet er niet bij gezegd worden dat het twee keer dat bedrag is.
     */
    public void postfixHIT2020MailTeksten() {
        final TabFormulierenOverzichtPage tabFormulieren = openTabFormulieren();

        tabFormulieren.getFormulieren().stream()
                .map(HitFormulier::new)
                .filter(formulier -> formulier.isInschrijfFormulier() || formulier.isGekoppeldFormulier())
                // .filter(formulier -> formulier.naam.startsWith("HIT Zeeland") || formulier.naam.startsWith("HIT Zeewolde"))
                .forEach(formulier -> {
                    System.out.printf("Fixing formulier %s", formulier.naam);
                    final FormulierWijzigBasisPage formulierWijzigBasisPage = tabFormulieren
                            .zetZoekFilterAan()
                            .filterFormuliernaam(formulier.naam)
                            .zoekMetFilter()
                            .openFormulier(formulier.naam);

                    // vulTabAanpassenMails(formulierWijzigBasisPage, datumDeelnemersinformatie);
                    statusWijzigingNaarKosteloosGeannuleerdHIT2020(formulierWijzigBasisPage, datumDeelnemersinformatie);

                    tabFormulieren.clickLink(getEvenementLink());
                    System.out.println(" [OK]");
                });
    }

    private FormulierWijzigAanpassenMailsPage wijzigAnnuleringsmail(final AbstractFormulierPage<?> geopendFormulier, final String datumDeelnemersInformatie) {
        return geopendFormulier.submenu().openTabAanpassenMails()
                .withSelecteerBericht(FormulierWijzigAanpassenMailsPage.Bericht.BEVESTIGING_ANNULERING_VAN_DEELNEMER_AAN_ORGANISATIE)
                .laadBericht()
                .withSoortBericht(FormulierWijzigAanpassenMailsPage.SoortBericht.GEWIJZIGD_BERICHT)
                .withGewijzigdBericht(MailTekstGenerator.annuleringsmailVanDeelnemerAanOrganisatie(Integer.valueOf(this.jaar)))
                .wijzigingenOpslaan()
                .controleerMelding(BevestigingsTekst.MAIL_GEWIJZIGD)
                ;
    }

    private FormulierWijzigAanpassenMailsPage statusWijzigingNaarKosteloosGeannuleerdHIT2020(final AbstractFormulierPage<?> geopendFormulier, final String datumDeelnemersInformatie) {
        return geopendFormulier.submenu().openTabAanpassenMails()
                .withSelecteerBericht(FormulierWijzigAanpassenMailsPage.Bericht.STATUSWIJZIGING_NAAR_KOSTELOOS_GEANNULEERD)
                .laadBericht()
                .withSoortBericht(FormulierWijzigAanpassenMailsPage.SoortBericht.GEWIJZIGD_BERICHT)
                .withGewijzigdBericht(MailTekstGenerator.hit2020MailBijStatuswijzigingNaarKosteloosGeannuleerd(Integer.valueOf(this.jaar)))
                .wijzigingenOpslaan()
                .controleerMelding(BevestigingsTekst.MAIL_GEWIJZIGD)
                ;
    }

    private FormulierWijzigAanpassenMailsPage vulTabAanpassenMails2020(final AbstractFormulierPage<?> geopendFormulier, final String datumDeelnemersInformatie) {
        return geopendFormulier.submenu().openTabAanpassenMails()
                .withSelecteerBericht(FormulierWijzigAanpassenMailsPage.Bericht.BEVESTIGING_ANNULERING_VAN_DEELNEMER_AAN_ORGANISATIE)
                .laadBericht()
                .withSoortBericht(FormulierWijzigAanpassenMailsPage.SoortBericht.GEWIJZIGD_BERICHT)
                .withGewijzigdBericht(MailTekstGenerator.annuleringsmailVanDeelnemerAanOrganisatie(Integer.valueOf(this.jaar)))
                .wijzigingenOpslaan()
                .controleerMelding(BevestigingsTekst.MAIL_GEWIJZIGD)

                .withSelecteerBericht(FormulierWijzigAanpassenMailsPage.Bericht.BEVESTIGING_INSCHRIJVING_AAN_DEELNEMER)
                .laadBericht()
                .withSoortBericht(FormulierWijzigAanpassenMailsPage.SoortBericht.GEWIJZIGD_BERICHT)
                .withGewijzigdBericht(
                        "Betreft: Bevestiging inschrijving %evt_nm%\n" +
                                "(bewaar dit mailtje goed)\n\n" +
                                "Beste %per_fullname%,\n\n" +
                                "Wat leuk! Je hebt je opgegeven voor een spectaculair weekend tijdens de %evt_nm% voor het onderdeel %frm_nm%. Deze activiteit start op %frm_from_dt% om %frm_from_time% uur en duurt tot %frm_till_dt% tot %frm_till_time% uur.\n\n" +
                                "De deelnamekosten voor deze activiteit zijn %frm_price% euro. Als je deze via iDEAL voldaan hebt, is je inschrijving compleet. Als er iets mis ging met de betaling of je bent door iemand anders, zoals je teamleider, ingeschreven is jouw betaling nog niet compleet. Je ontvangt dan een aparte mail met een betalingsverzoek en een iDEAL-link om je betaling te voldoen. Pas dan ben je officieel ingeschreven.\n\n" +
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
                .controleerMelding(BevestigingsTekst.MAIL_GEWIJZIGD)

                .withSelecteerBericht(FormulierWijzigAanpassenMailsPage.Bericht.BEVESTIGING_INSCHRIJVING_AAN_OUDERS)
                .laadBericht()
                .withSoortBericht(FormulierWijzigAanpassenMailsPage.SoortBericht.GEWIJZIGD_BERICHT)
                .withGewijzigdBericht("""
                        Beste ouder/verzorger van %per_fullname%,

                        %per_fullname% heeft zich opgegeven als deelnemer voor %frm_nm% tijdens de %evt_nm%.
                        Deze mail dient uitsluitend om u te informeren over deze inschrijving. Alle verdere correspondentie zal via het in Scouts Online geregistreerde e-mailadres %per_email% van %per_fullname% verlopen.
                        Deze activiteit start op %frm_from_dt% om %frm_from_time% uur en duurt tot %frm_till_dt% tot %frm_till_time% uur.
                        De deelnamekosten voor deze activiteit zijn %frm_price% euro.

                        De deelnemer heeft na inschrijving 10 dagen bedenktijd en kan binnen deze 10 dagen kosteloos annuleren. Binnen deze tijd kun je kosteloos annuleren. Je krijgt dan het deelnamegeld helemaal teruggestort. Dit is ook de termijn waarbinnen het groepje compleet moet zijn. Na 10 dagen kan er nog wel geannuleerd worden maar krijg de deelnemer het deelnamegeld terug verminderd met € 10 administratiekosten. Na de sluiting van de inschrijving kan er niet meer geannuleerd worden en krijgt de deelnemer geen geld meer terug.

                        Zijn er problemen met inschrijven of heeft u een andere vraag? Kijk dan op http://hit.scouting.nl bij het onderdeel ’inschrijven’.

                        Met vriendelijke groet,

                        De HIT-organisatie
                        Scouting Nederland
                        """)
                .wijzigingenOpslaan()
                .controleerMelding(BevestigingsTekst.MAIL_GEWIJZIGD)

                .withSelecteerBericht(FormulierWijzigAanpassenMailsPage.Bericht.STATUSWIJZIGING_NAAR_KOSTELOOS_GEANNULEERD)
                .laadBericht()
                .withSoortBericht(FormulierWijzigAanpassenMailsPage.SoortBericht.GEWIJZIGD_BERICHT)
                .withGewijzigdBericht("""
                        Beste %per_fullname%,

                        Je inschrijving voor het evenement %evt_nm% (%frm_nm%) is kosteloos geannuleerd.
                        Je bent niet meer ingeschreven voor dit evenement.

                        De HIT organisatie
                        """)
                .wijzigingenOpslaan()
                .controleerMelding(BevestigingsTekst.MAIL_GEWIJZIGD)
                ;
    }

    public void maakDieet() {
        final FormulierWijzigSamenstellenPage samenstellen = solHomePage
                .hoofdmenu().openSpelVanMijnSpeleenheid(naamSpeleenheid)
                .openEvenement("HIT 2019")
                .submenu().openTabFormulieren()
                .openFormulier("HIT 2019 Basisformulier (niet wijzigen)")
                .submenu().openTabSamenstellen();

        final CheckboxWijzigen checkbox = (CheckboxWijzigen) samenstellen
                .withSelecteerNieuwVeld(FormulierWijzigSamenstellenPage.NieuwVeld.CHECKBOX)
                .toevoegen();

        checkbox.withKoptekst("Dieet")
                .withLocatieVanVeldNa("'Gezondheid en Voeding'")
                .withVerplichtVeld(JaNee.NEE)
                .withZichtbaarVoorGebruiker(JaNee.JA)
                .withNieuweOptie("Geen dieet").toevoegen()
                .withNieuweOptie("Veganistisch").toevoegen()
                .withNieuweOptie("Vegetarisch").toevoegen()
                .withNieuweOptie("Melk allergie").toevoegen()
                .withNieuweOptie("Lactose intolerantie").toevoegen()
                .withNieuweOptie("Noten allergie").toevoegen()
                .withNieuweOptie("Glutenvrij").toevoegen()
                .withNieuweOptie("Dieet gebaseerd op godsdienst, namelijk").toevoegen()
                .withOpenVraag("Dieet gebaseerd op godsdienst, namelijk", JaNee.JA)
                .withNieuweOptie("Anders, namelijk").toevoegen()
                .withOpenVraag("Anders, namelijk", JaNee.JA)
                .wijzigingenOpslaan()
        ;
    }

    public void maakDieetKind() {
        final FormulierWijzigSamenstellenPage samenstellen = solHomePage
                .hoofdmenu().openSpelVanMijnSpeleenheid(naamSpeleenheid)
                .openEvenement("HIT 2019")
                .submenu().openTabFormulieren()
                .openFormulier("HIT 2019 Basisformulier Ouder-Kind (niet wijzigen)")
                .submenu().openTabSamenstellen();

//        final CheckboxWijzigen checkbox = (CheckboxWijzigen) samenstellen
//                .withSelecteerNieuwVeld(FormulierWijzigSamenstellenPage.NieuwVeld.CHECKBOX)
//                .toevoegen();
//        checkbox.withKoptekst("Dieet (kind)")
//                .withLocatieVanVeldNa("Heb je een zwemdiploma")
//                .withVerplichtVeld(JaNee.NEE)
//                .withZichtbaarVoorGebruiker(JaNee.JA)
//                .withNieuweOptie("Geen dieet").toevoegen()
//                .withNieuweOptie("Veganistisch").toevoegen()
//                .withNieuweOptie("Vegetarisch").toevoegen()
//                .withNieuweOptie("Melk allergie").toevoegen()
//                .withNieuweOptie("Lactose intolerantie").toevoegen()
//                .withNieuweOptie("Noten allergie").toevoegen()
//                .withNieuweOptie("Glutenvrij").toevoegen()
//                .withNieuweOptie("Dieet gebaseerd op godsdienst, namelijk").toevoegen()
//                .withOpenVraag("Dieet gebaseerd op godsdienst, namelijk", JaNee.JA)
//                .withNieuweOptie("Anders, namelijk").toevoegen()
//                .withOpenVraag("Anders, namelijk", JaNee.JA)
//                .wijzigingenOpslaan()
//        ;

        final MeerdereTekstRegelsWijzigen meerdere = (MeerdereTekstRegelsWijzigen) samenstellen
                .withSelecteerNieuwVeld(FormulierWijzigSamenstellenPage.NieuwVeld.TEKSTREGELS)
                .toevoegen();
        meerdere
                .withKoptekst("Heeft de deelnemer (kind) een allergie, lichamelijke of geestelijke beperkingen of gebruikt hij/zij medicijnen?")
                .withHelptekst("")
                .withLocatieVanVeldNa("Dieet (kind)")
                .withAantalRegels(3)
                .withBreedte(MeerdereTekstRegelsWijzigen.Breedte.BREED)
                .opslaanWijzigingen()
        ;
    }

    public void maakDieetOuder() {
        final FormulierWijzigSamenstellenPage samenstellen = solHomePage
                .hoofdmenu().openSpelVanMijnSpeleenheid(naamSpeleenheid)
                .openEvenement("HIT 2019")
                .submenu().openTabFormulieren()
                .openFormulier("HIT 2019 Basisformulier Ouder-Kind (niet wijzigen)")
                .submenu().openTabSamenstellen();

        final CheckboxWijzigen checkbox = (CheckboxWijzigen) samenstellen
                .withSelecteerNieuwVeld(FormulierWijzigSamenstellenPage.NieuwVeld.CHECKBOX)
                .toevoegen();
        checkbox.withKoptekst("Dieet (ouder)")
                .withLocatieVanVeldNa("Ouder is lid van Scouting")
                .withVerplichtVeld(JaNee.NEE)
                .withZichtbaarVoorGebruiker(JaNee.JA)
                .withNieuweOptie("Geen dieet").toevoegen()
                .withNieuweOptie("Veganistisch").toevoegen()
                .withNieuweOptie("Vegetarisch").toevoegen()
                .withNieuweOptie("Melk allergie").toevoegen()
                .withNieuweOptie("Lactose intolerantie").toevoegen()
                .withNieuweOptie("Noten allergie").toevoegen()
                .withNieuweOptie("Glutenvrij").toevoegen()
                .withNieuweOptie("Dieet gebaseerd op godsdienst, namelijk").toevoegen()
                .withOpenVraag("Dieet gebaseerd op godsdienst, namelijk", JaNee.JA)
                .withNieuweOptie("Anders, namelijk").toevoegen()
                .withOpenVraag("Anders, namelijk", JaNee.JA)
                .wijzigingenOpslaan()
        ;

        final MeerdereTekstRegelsWijzigen meerdere = (MeerdereTekstRegelsWijzigen) samenstellen
                .withSelecteerNieuwVeld(FormulierWijzigSamenstellenPage.NieuwVeld.TEKSTREGELS)
                .toevoegen();
        meerdere
                .withKoptekst("Heeft de deelnemer (ouder) een allergie, lichamelijke of geestelijke beperkingen of gebruikt hij/zij medicijnen?")
                .withHelptekst("")
                .withLocatieVanVeldNa("Dieet (ouder)")
                .withAantalRegels(3)
                .withBreedte(MeerdereTekstRegelsWijzigen.Breedte.BREED)
                .opslaanWijzigingen()
        ;
    }

    /**
     * Eerste aanpassing van 2022.
     */
    public void setStartTijdInschrijvingOpTwaalfEnKosteloosAnnulerenOpEindeInschrijving(final String startTijdInschrijving) {
        final TabFormulierenOverzichtPage tabFormulieren = openTabFormulieren();

        tabFormulieren.getFormulieren().stream()
                .map(HitFormulier::new)
                .filter(formulier -> formulier.isInschrijfFormulier() || formulier.isGekoppeldFormulier())
                .forEach(formulier -> {
                    System.out.printf("Fixing formulier %s", formulier.naam);
                    tabFormulieren.openFormulier(formulier.naam)
                            .withInschrijvingStarttijd(startTijdInschrijving)
                            .opslaanWijzigingen()
                            .controleerMelding(FORMULIER_GEWIJZIGD)

                            .submenu().openTabFinancien()
                            .withVolledigeKostenAnnulerenVanaf(27, 2, 2022)
                            .opslaanWijzigingen()
                            .controleerMelding(FORMULIER_GEWIJZIGD)
                    ;
                    tabFormulieren.clickLink(getEvenementLink());
                    System.out.println(" [OK]");
                });
    }

    /**
     * Limieten op subgroepjes maken dat de wachtlijst vol is en geen deelnemers meer accepteert.
     */
    public void setSubgroepLimietenVoorFase1() {
        final TabFormulierenOverzichtPage tabFormulieren = openTabFormulieren();

        tabFormulieren.getFormulieren().stream()
                .map(HitFormulier::new)
                .filter(formulier -> formulier.isInschrijfFormulier() || formulier.isGekoppeldFormulier())
                .forEach(formulier -> {
                    System.out.printf("Fixing formulier %s", formulier.naam);
                    tabFormulieren.openFormulier(formulier.naam)
                            .submenu().openTabSubgroepen()
                            .openSubgroepCategorie(ScoutsOnlineVuller.KOPPELGROEPJE)
                            //.vergrootMaximumAantalSubGroepen(100)
                            .withTeltHetMaxAantalMee(JaNee.NEE)
                            .opslaanGegevens()
                            .controleerMelding(BevestigingsTekst.SUBGROEPCATEGORIE_GEWIJZIGD)
                    ;
                    tabFormulieren.clickLink(getEvenementLink());
                    System.out.println(" [OK]");
                });
    }

    public void testOpenenKoppelgroepjesDetailBijOuderKindKampen() {
        final TabFormulierenOverzichtPage tabFormulieren = openTabFormulieren();

        tabFormulieren.getFormulieren().stream()
                .map(HitFormulier::new)
                .filter(formulier -> formulier.isInschrijfFormulier() || formulier.isGekoppeldFormulier())
                .forEach(formulier -> {
                    System.out.printf("Testing formulier %s", formulier.naam);
                    tabFormulieren.openFormulier(formulier.naam)
                            .submenu().openTabSubgroepen()
                            .openSubgroepCategorie(ScoutsOnlineVuller.KOPPELGROEPJE)
                            .terugNaarSubgroepoverzicht()
                    ;
                    tabFormulieren.clickLink(getEvenementLink());
                    System.out.println(" [OK]");
                });
    }

    public void postfixHIT2022MailTeksten() {
        final TabFormulierenOverzichtPage tabFormulieren = openTabFormulieren();

        tabFormulieren.getFormulieren().stream()
                .map(HitFormulier::new)
                .filter(formulier -> formulier.isInschrijfFormulier() || formulier.isGekoppeldFormulier())
                .forEach(formulier -> {
                    System.out.printf("Fixing mails for formulier %s", formulier.naam);
                    final FormulierWijzigBasisPage formulierWijzigBasisPage = tabFormulieren
//                            .zetZoekFilterAan()
//                            .filterFormuliernaam(formulier.naam)
//                            .zoekMetFilter()
                            .openFormulier(formulier.naam);

                    wijzigTabAanpassenMails2022(formulierWijzigBasisPage, datumDeelnemersinformatie);

                    tabFormulieren.clickLink(getEvenementLink());
                    System.out.println(" [OK]");
                });
    }

    private FormulierWijzigAanpassenMailsPage wijzigTabAanpassenMails2022(final AbstractFormulierPage<?> geopendFormulier, final String datumDeelnemersInformatie) {
        return geopendFormulier.submenu().openTabAanpassenMails()
                .withSelecteerBericht(FormulierWijzigAanpassenMailsPage.Bericht.BEVESTIGING_INSCHRIJVING_AAN_DEELNEMER)
                .laadBericht()
                .withSoortBericht(FormulierWijzigAanpassenMailsPage.SoortBericht.GEWIJZIGD_BERICHT)
                .withGewijzigdBericht(MailTekstGenerator.bevestigingVanInschrijvingAanDeelnemer(datumDeelnemersInformatie))
                .wijzigingenOpslaan()
                .controleerMelding(BevestigingsTekst.MAIL_GEWIJZIGD)
                ;
    }

    public void ronde2() {
        final TabFormulierenOverzichtPage tabFormulieren = openTabFormulieren();

        tabFormulieren.getFormulieren().stream()
                .map(HitFormulier::new)
                .filter(formulier -> formulier.isInschrijfFormulier() || formulier.isGekoppeldFormulier())
                .forEach(formulier -> {
                    System.out.printf("Fixing formulier %s for round 2", formulier.naam);
                    final FormulierWijzigBasisPage formulierWijzigBasisPage = tabFormulieren
                            .openFormulier(formulier.naam);

                    boolean isVol = formulier.gereserveerd >= formulier.maximumAantalDeelnemers;
                    System.out.printf(" (%d %d %d): %s",
                            formulier.aantalDeelnemers,
                            formulier.gereserveerd,
                            formulier.maximumAantalDeelnemers,
                            isVol
                    );
                    formulierWijzigBasisPage
                            .withInschrijvingStart(6, 2, 2022)
                            .withInschrijvingEind(13, 2, 2022)
                            .opslaanWijzigingen()
                            .controleerMelding(FORMULIER_GEWIJZIGD);
                    if (isVol) {
                        formulierWijzigBasisPage.submenu().openTabDeelnamecondities()
                                .withWachtlijst(JaNee.NEE)
                                .withStandaardWachtlijst(JaNee.NEE)
                                .opslaanWijzigingen()
                                .controleerMelding(FORMULIER_GEWIJZIGD);
                        System.out.print(" [GEEN WACHTLIJST MEER]");
                    }
                    tabFormulieren.clickLink(getEvenementLink());
                    System.out.println(" [OK]");
                });
    }

    public void ronde2MetUitstel() {
        final TabFormulierenOverzichtPage tabFormulieren = openTabFormulieren();

        tabFormulieren.getFormulieren().stream()
                .map(HitFormulier::new)
                .filter(formulier -> formulier.isInschrijfFormulier() || formulier.isGekoppeldFormulier())
                .forEach(formulier -> {
                    System.out.printf("Fixing formulier %s for round 2 (verlenging)", formulier.naam);
                    final FormulierWijzigBasisPage formulierWijzigBasisPage = tabFormulieren
                            .openFormulier(formulier.naam);

                    formulierWijzigBasisPage
                            .withInschrijvingEind(15, 2, 2022)
                            .opslaanWijzigingen()
                            .controleerMelding(FORMULIER_GEWIJZIGD);

                    tabFormulieren.clickLink(getEvenementLink());
                    System.out.println(" [OK]");
                });
    }

    public void ronde3() {
        final TabFormulierenOverzichtPage tabFormulieren = openTabFormulieren();

        tabFormulieren.getFormulieren().stream()
                .map(HitFormulier::new)
                .filter(formulier -> formulier.isInschrijfFormulier() || formulier.isGekoppeldFormulier())
                .forEach(formulier -> {
                    System.out.printf("Fixing formulier %s for round 3", formulier.naam);
                    final FormulierWijzigBasisPage formulierWijzigBasisPage = tabFormulieren
                            .openFormulier(formulier.naam);

                    System.out.printf(" (%d %d %d) ",
                            formulier.aantalDeelnemers,
                            formulier.gereserveerd,
                            formulier.maximumAantalDeelnemers
                    );
                    // STAP EEN
//                    formulierWijzigBasisPage
//                            .withInschrijvingStart(20, 2, 2022)
//                            .withInschrijvingEind(27, 2, 2022)
//                            .opslaanWijzigingen()
//                            .controleerMelding(BevestigingsTekst.FORMULIER_GEWIJZIGD);

                    // STAP TWEE
//                    formulierWijzigBasisPage.submenu().openTabDeelnamecondities()
//                            .withWachtlijst(JaNee.NEE)
//                            .withStandaardWachtlijst(JaNee.NEE)
//                            .opslaanWijzigingen()
//                            .controleerMelding(BevestigingsTekst.FORMULIER_GEWIJZIGD);


                    // STAP DRIE
                    formulierWijzigBasisPage.submenu().openTabSubgroepen()
                            .openSubgroepCategorie(ScoutsOnlineVuller.KOPPELGROEPJE)
                            .withTeltHetMaxAantalMee(JaNee.JA)
                            .verkleinMaximumAantalSubGroepen(100)
                            .opslaanGegevens()
                            .controleerMelding(BevestigingsTekst.SUBGROEPCATEGORIE_GEWIJZIGD)
                    ;
                    System.out.print(" [GEEN WACHTLIJST MEER]");
                    tabFormulieren.clickLink(getEvenementLink());
                    System.out.println(" [OK]");
                });
    }

    public void fase2() {
        final TabFormulierenOverzichtPage tabFormulieren = openTabFormulieren();

        tabFormulieren.getFormulieren().stream()
                .map(HitFormulier::new)
                .filter(formulier -> formulier.isInschrijfFormulier() || formulier.isGekoppeldFormulier())
                .forEach(formulier -> {
                    System.out.printf("Fixing formulier %s for phase 2", formulier.naam);
                    final FormulierWijzigBasisPage formulierWijzigBasisPage = tabFormulieren
                            .openFormulier(formulier.naam);

                    System.out.printf(" (%d %d %d) ",
                            formulier.aantalDeelnemers,
                            formulier.gereserveerd,
                            formulier.maximumAantalDeelnemers
                    );
                    // STAP EEN
                    formulierWijzigBasisPage
                            .withInschrijvingStart(5, 2, 2023)
                            .withInschrijvingStarttijd("12:00")
                            .withInschrijvingEind(13, 2, 2023)
                            .opslaanWijzigingen()
                            .controleerMelding(BevestigingsTekst.FORMULIER_GEWIJZIGD);

                    // STAP TWEE
                    formulierWijzigBasisPage.submenu().openTabDeelnamecondities()
                            .withWachtlijst(JaNee.NEE)
                            .withStandaardWachtlijst(JaNee.NEE)
                            .opslaanWijzigingen()
                            .controleerMelding(BevestigingsTekst.FORMULIER_GEWIJZIGD);

                    // STAP DRIE
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

    public void vulUitzonderingenPatsBoem() {
        final List<String> uitzonderingen = leesUitzonderingenPatsBoem();
        final TabFormulierenOverzichtPage tabFormulieren = openTabFormulieren();

        tabFormulieren.getFormulieren().stream()
                .map(HitFormulier::new)
                .filter(formulier -> formulier.naam.contains("Pats Boem Exploratie HIT"))
                .findFirst()
                .ifPresent(formulier -> {
                    System.out.printf("Vullen formulier %s met %d uitzonderingen", formulier.naam, uitzonderingen.size());
                    final FormulierWijzigDeelnameconditiesPage formulierWijzigDeelnameconditiesPage = tabFormulieren
                            .openFormulier(formulier.naam)
                            .submenu().openTabDeelnamecondities();
                    uitzonderingen.forEach(lidnummer -> {
                        formulierWijzigDeelnameconditiesPage
                                .withUitzonderingLidnummer(lidnummer)
                                .toevoegenUitzondering()
                                .controleerMelding(FORMULIER_GEWIJZIGD.toLowerCase(Locale.ROOT))
                        ;
                    });
                });
    }

    private List<String> leesUitzonderingenPatsBoem() {
        try {
            return Files.readAllLines(Paths.get("uitzonderingen.txt"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
