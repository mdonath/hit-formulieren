package nl.scouting.hit.sol;

import com.google.common.base.Stopwatch;
import nl.scouting.hit.common.Util;
import nl.scouting.hit.kampinfo.InschrijfformulierAanpasser;
import nl.scouting.hit.kampinfo.ScoutsOnlineVuller;
import nl.scouting.hit.sol.evenement.tab.formulier.TabFormulierenOverzichtPage;

import java.io.IOException;

public class Main {

    public static void main(final String[] args) throws Exception {
        final HitContext context = HitContext.Builder.create()
                .baseUrl("https://sol.scouting.nl")
                .username(Util.readUsernameFromFile())
                .password(Util.readPasswordFromFile())
                .idEvenement(HitConstants.ID_EVENEMENT)
                .naamEvenement(HitConstants.NAAM_EVENEMENT)
                .naamSpeleenheid("HIT Helpdeskgroep")
                .datumDeelnemersinformatie("24 februari 2023")
                .build();

        final Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            // Vul alle formulieren op basis van de export
            // vulScoutsOnline(context);

            // Voer aanpassingen achteraf op ALLE inschrijfformulieren door
            // postfixHIT2023(context);

            doeAanpassingenVoorFase2(context);

            // Deze is ergens voor nodig, maar weet niet meer waarvoor.
            // lijstMetInschrijflinks(baseUrl, username, password, naamEvenement, naamSpeleenheid);
        } finally {
            System.out.println(stopwatch.stop());
        }
    }

    protected static void vulScoutsOnline(final HitContext context) throws IOException {
        try (final ScoutsOnline sol = new ScoutsOnline(context.baseUrl, context.username, context.password)) {
            final ScoutsOnlineVuller vuller = new ScoutsOnlineVuller(sol.solHomePage, context.naamEvenement, context.naamSpeleenheid, null);
            // vuller.setDatumDeelnemersinformatie(context.datumDeelnemersinformatie);

            // STAP 1: Verwijderen oude formulieren
            // vuller.verwijderAlleFormulieren();

            // STAP 2: Aanmaken initiele formulieren voor gewone inschrijfformulieren
            // vuller.maakInitieleFormulieren();

            // STAP 3: Breng de formuliernummers weer naar KampInfo
            // Zie hitsite/Main.java
            // KampInfoHelper.deleteDatafile();

            // STAP 4:
            // Handmatig: neem de formuliernummers van bovenstaande formulieren over in KampInfo in het veld voor het ouder-formulier
            // KampInfoHelper.deleteDatafile();

            // STAP 5: Aanmaken Ouder-kind formulieren waarbij de ouder zich inschrijft met een niet-lid kind
            // vuller.maakInitieleFormulierenVoorOuderLid();

            // STAP 6:
            // vuller.vulFormulierenMetDeRest();

            // STAP 7:
            // Handmatig: maak voor de kampen waar meerdere kinderen met één ouder kunnen komen, aparte formulieren aan
            // - kopieer de data.json naar een data-extra.json en verwijder alles behalve de betreffende ouder-kind-kampen
            // final ScoutsOnlineVuller extraVuller = new ScoutsOnlineVuller(sol.solHomePage, context.naamEvenement, context.naamSpeleenheid, "data-extra");
            // extraVuller.maakInitieleFormulieren();
            // extraVuller.vulFormulierenMetDeRest();

            // STAP 8: Maak alles actief
            // vuller.maakFormulierenActief(JaNee.JA);
        }
    }

    protected static void postfixHIT2023(final HitContext context) throws IOException {
        try (final ScoutsOnline sol = new ScoutsOnline(context.baseUrl, context.username, context.password)) {
            final InschrijfformulierAanpasser aanpasser = new InschrijfformulierAanpasser(sol.solHomePage, context);
            // Reden: De Patsboemers hebben een lijst met uitzonderingen aangeleverd. Alleen deze mensen mogen inschrijven.
            // aanpasser.vulUitzonderingenPatsBoem();

            // De subgroepen maken het formulier nu vol, dat moet niet en kan uitgeschakeld worden via een optie op de subgroep
            aanpasser.setSubgroepLimietenVoorFase1();

//           aanpasser.setStartTijdInschrijvingOpTwaalfEnKosteloosAnnulerenOpEindeInschrijving("12:00");
//            aanpasser.testOpenenKoppelgroepjesDetailBijOuderKindKampen();
//            aanpasser.postfixHIT2022MailTeksten();
//            aanpasser.ronde2MetUitstel();
//            aanpasser.ronde3();
        }
    }

    protected static void doeAanpassingenVoorFase2(final HitContext context) throws IOException {
        try (final ScoutsOnline sol = new ScoutsOnline(context.baseUrl, context.username, context.password)) {
            final InschrijfformulierAanpasser aanpasser = new InschrijfformulierAanpasser(sol.solHomePage, context);
            aanpasser.fase2();
        }
    }

    protected static void lijstMetInschrijflinks(final String baseUrl, final String username, final String password, final String naamEvenement, final String naamSpeleenheid) {
        try (final ScoutsOnline sol = new ScoutsOnline(baseUrl, username, password)) {
            final TabFormulierenOverzichtPage formulierenOverzicht = sol.solHomePage.hoofdmenu()
                    .openSpelVanMijnSpeleenheid(naamSpeleenheid)
                    .openEvenement(naamEvenement);

            formulierenOverzicht.getFormulieren()
                    .forEach(formulier ->
                            System.out.printf("%s\t%s%n"
                                    , formulier.naam
                                    , formulier.inschrijfLink(baseUrl + "/as/form/%s/participant/new/")
                            ));
        } catch (final IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
