package nl.scouting.hit.sol;

import nl.scouting.hit.common.Util;
import nl.scouting.hit.kampinfo.InschrijfformulierAanpasser;
import nl.scouting.hit.kampinfo.ScoutsOnlineVuller;

public class Main {

    public static void main(final String[] args) throws Exception {
        final String baseUrl = "https://sol.scouting.nl";
        final String username = Util.readUsernameFromFile();
        final String password = Util.readPasswordFromFile();

//        vulScoutsOnline(baseUrl, username, password);
//        extraVeldenFormulier(baseUrl, username, password);
        fixAnnuleringsdatum(baseUrl, username, password);
    }

    protected static void fixAnnuleringsdatum(final String baseUrl, final String username, final String password) throws Exception {
        try (final ScoutsOnline sol = new ScoutsOnline(baseUrl, username, password)) {
            final ScoutsOnlineVuller vuller = new ScoutsOnlineVuller(sol.solHomePage, "HIT 2019", "HIT Helpdeskgroep");
            vuller.fixAnnuleringsdatum();
        }
    }

    protected static void vulScoutsOnline(final String baseUrl, final String username, final String password) throws Exception {
        try (final ScoutsOnline sol = new ScoutsOnline(baseUrl, username, password)) {
            final ScoutsOnlineVuller vuller = new ScoutsOnlineVuller(sol.solHomePage, "HIT 2019", "HIT Helpdeskgroep");

            // vuller.verwijderAlleFormulieren();
            // vuller.maakInitieleFormulieren();
            // vuller.vulFormulierenMetDeRest();
            vuller.maakFormulierenActief(JaNee.JA);
        }
    }

    protected static void extraVeldenFormulier(final String baseUrl, final String username, final String password) throws Exception {
        try (final ScoutsOnline sol = new ScoutsOnline(baseUrl, username, password)) {
            final InschrijfformulierAanpasser aanpasser = new InschrijfformulierAanpasser(sol.solHomePage, "HIT 2019", "HIT Helpdeskgroep");
//            aanpasser.maakDieet();
//            aanpasser.maakDieetKind();
            aanpasser.maakDieetOuder();
        }
    }
}
