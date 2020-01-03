package nl.scouting.hit.sol;

import nl.scouting.hit.common.Util;
import nl.scouting.hit.kampinfo.InschrijfformulierAanpasser;
import nl.scouting.hit.kampinfo.ScoutsOnlineVuller;

public class Main {

    public static void main(final String[] args) throws Exception {
        final String baseUrl = "https://sol.scouting.nl";
        final String username = Util.readUsernameFromFile();
        final String password = Util.readPasswordFromFile();
        final String naamEvenement = "HIT 2020";
        final String datumDeelnemersinformatie = "9 maart 2020";

        vulScoutsOnline(baseUrl, username, password, naamEvenement, datumDeelnemersinformatie);
        // extraVeldenFormulier(baseUrl, username, password, naamEvenement);
        // fixAnnuleringsdatum(baseUrl, username, password, naamEvenement);
    }

    protected static void vulScoutsOnline(final String baseUrl, final String username, final String password, final String naamEvenement, final String datumDeelnemersinformatie) throws Exception {
        try (final ScoutsOnline sol = new ScoutsOnline(baseUrl, username, password)) {
            final ScoutsOnlineVuller vuller = new ScoutsOnlineVuller(sol.solHomePage, naamEvenement, "HIT Helpdeskgroep");
            vuller.setDatumDeelnemersinformatie(datumDeelnemersinformatie);

            // vuller.verwijderAlleFormulieren();
//            vuller.maakInitieleFormulieren();
//            vuller.vulFormulierenMetDeRest();
            // vuller.maakFormulierenActief(JaNee.JA);

            vuller.maakBasisFormulieren();
        }
    }

    protected static void extraVeldenFormulier(final String baseUrl, final String username, final String password, final String naamEvenement) throws Exception {
        try (final ScoutsOnline sol = new ScoutsOnline(baseUrl, username, password)) {
            final InschrijfformulierAanpasser aanpasser = new InschrijfformulierAanpasser(sol.solHomePage, naamEvenement, "HIT Helpdeskgroep");
            // aanpasser.maakDieet();
            // aanpasser.maakDieetKind();
            aanpasser.maakDieetOuder();
        }
    }

    protected static void fixAnnuleringsdatum(final String baseUrl, final String username, final String password, final String naamEvenement) throws Exception {
        try (final ScoutsOnline sol = new ScoutsOnline(baseUrl, username, password)) {
            final ScoutsOnlineVuller vuller = new ScoutsOnlineVuller(sol.solHomePage, naamEvenement, "HIT Helpdeskgroep");
            vuller.fixAnnuleringsdatum();
        }
    }
}
