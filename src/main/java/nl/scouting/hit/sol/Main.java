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
                .idEvenement("26172")
                .naamEvenement("HIT 2022")
                .naamSpeleenheid("HIT Helpdeskgroep")
                .datumDeelnemersinformatie("7 maart 2022")
                .build();

        final Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            // vulScoutsOnline(context);
            // extraVeldenFormulier(baseUrl, username, password, naamEvenement);
            // postfixHIT2020(baseUrl, username, password, idEvenement, naamEvenement, datumDeelnemersinformatie);
            // lijstMetInschrijflinks(baseUrl, username, password, naamEvenement, naamSpeleenheid);

            postfixHIT2022(context);
        } finally {
            System.out.println(stopwatch.stop());
        }
    }

    protected static void vulScoutsOnline(final HitContext context) throws IOException {
        try (final ScoutsOnline sol = new ScoutsOnline(context.baseUrl, context.username, context.password)) {
            final ScoutsOnlineVuller vuller = new ScoutsOnlineVuller(sol.solHomePage, context.naamEvenement, context.naamSpeleenheid);
            vuller.setDatumDeelnemersinformatie(context.datumDeelnemersinformatie);

            //vuller.verwijderAlleFormulieren();
            // vuller.maakInitieleFormulieren();
            // vuller.vulFormulierenMetDeRest();

            //vuller.maakInitieleFormulierenVoorOuderLid();
            //vuller.vulFormulierenMetDeRestVoorOuderLid();

            // vuller.maakFormulierenActief(JaNee.JA);

            // vuller.maakBasisFormulieren();
        }
    }

    protected static void postfixHIT2022(final HitContext context) throws IOException {
        try (final ScoutsOnline sol = new ScoutsOnline(context.baseUrl, context.username, context.password)) {
            final InschrijfformulierAanpasser aanpasser = new InschrijfformulierAanpasser(sol.solHomePage, context);
//            aanpasser.setStartTijdInschrijvingOpTwaalfEnKosteloosAnnulerenOpEindeInschrijving("12:00");
//            aanpasser.setSubgroepLimietenOpNul();
//             aanpasser.testOpenenKoppelgroepjesDetailBijOuderKindKampen();
//            aanpasser.postfixHIT2022MailTeksten();
//            aanpasser.ronde2MetUitstel();
            aanpasser.ronde3();
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
