package nl.scouting.hit.sol;

import nl.scouting.hit.common.Util;
import nl.scouting.hit.kampinfo.InschrijfformulierAanpasser;
import nl.scouting.hit.kampinfo.ScoutsOnlineVuller;
import nl.scouting.hit.sol.evenement.tab.formulier.TabFormulierenOverzichtPage;

public class Main {

    public static void main(final String[] args) throws Exception {
        final String baseUrl = "https://sol.scouting.nl";
        final String username = Util.readUsernameFromFile();
        final String password = Util.readPasswordFromFile();
        final String idEvenement = "26172";
        final String naamEvenement = "HIT 2022";
        final String naamSpeleenheid = "HIT Helpdeskgroep";
        final String datumDeelnemersinformatie = "6 maart 2022";

        vulScoutsOnline(baseUrl, username, password, naamEvenement, datumDeelnemersinformatie);
        // extraVeldenFormulier(baseUrl, username, password, naamEvenement);
        // postfixHIT2020(baseUrl, username, password, idEvenement, naamEvenement, datumDeelnemersinformatie);
        // lijstMetInschrijflinks(baseUrl, username, password, naamEvenement, naamSpeleenheid);
    }

    protected static void vulScoutsOnline(final String baseUrl, final String username, final String password, final String naamEvenement, final String datumDeelnemersinformatie) throws Exception {
        try (final ScoutsOnline sol = new ScoutsOnline(baseUrl, username, password)) {
            final ScoutsOnlineVuller vuller = new ScoutsOnlineVuller(sol.solHomePage, naamEvenement, "HIT Helpdeskgroep");
            vuller.setDatumDeelnemersinformatie(datumDeelnemersinformatie);

            // vuller.verwijderAlleFormulieren();
            // vuller.maakInitieleFormulieren();
            vuller.vulFormulierenMetDeRest();
            // vuller.maakFormulierenActief(JaNee.JA);

            // vuller.maakBasisFormulieren();
        }
    }

    protected static void extraVeldenFormulier(final String baseUrl, final String username, final String password, final String idEvenement, final String naamEvenement) throws Exception {
        try (final ScoutsOnline sol = new ScoutsOnline(baseUrl, username, password)) {
            final InschrijfformulierAanpasser aanpasser = new InschrijfformulierAanpasser(sol.solHomePage, idEvenement, naamEvenement, "HIT Helpdeskgroep");
            // aanpasser.maakDieet();
            // aanpasser.maakDieetKind();
            aanpasser.maakDieetOuder();
        }
    }

    protected static void postfixHIT2020(final String baseUrl, final String username, final String password, final String idEvenement, final String naamEvenement, final String datumDeelnemersinformatie) throws Exception {
        try (final ScoutsOnline sol = new ScoutsOnline(baseUrl, username, password)) {
            final InschrijfformulierAanpasser aanpasser = new InschrijfformulierAanpasser(sol.solHomePage, idEvenement, naamEvenement, "HIT Helpdeskgroep");
            aanpasser.setDatumDeelnemersinformatie(datumDeelnemersinformatie);

//            aanpasser.postfixHIT2020();
            aanpasser.postfixHIT2020MailTeksten();
        }
    }

    protected static void lijstMetInschrijflinks(final String baseUrl, final String username, final String password, final String naamEvenement, final String naamSpeleenheid) throws Exception {
        try (final ScoutsOnline sol = new ScoutsOnline(baseUrl, username, password)) {
            final TabFormulierenOverzichtPage formulierenOverzicht = sol.solHomePage.hoofdmenu()
                    .openSpelVanMijnSpeleenheid(naamSpeleenheid)
                    .openEvenement(naamEvenement);

            formulierenOverzicht.getFormulieren().stream()
                    .forEach((formulier) ->
                            System.out.println(String.format("%s\t%s"
                                    , formulier.naam
                                    , formulier.inschrijfLink(baseUrl + "/as/form/%s/participant/new/")
                            )));
        }
    }
}
