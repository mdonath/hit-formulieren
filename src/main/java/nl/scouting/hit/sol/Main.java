package nl.scouting.hit.sol;

import nl.scouting.hit.common.Util;
import nl.scouting.hit.kampinfo.InschrijfformulierAanpasser;
import nl.scouting.hit.kampinfo.ScoutsOnlineVuller;
import nl.scouting.hit.sol.evenement.tab.formulier.Formulier;

import java.util.List;

public class Main {

    public static void main(final String[] args) throws Exception {
        final String baseUrl = "https://sol.scouting.nl";
        final String username = Util.readUsernameFromFile();
        final String password = Util.readPasswordFromFile();

        // vulScoutsOnline(baseUrl, username, password);
        try (final ScoutsOnline sol = new ScoutsOnline(baseUrl, username, password)) {
            final InschrijfformulierAanpasser aanpasser = new InschrijfformulierAanpasser(sol.solHomePage, "HIT 2019", "HIT Helpdeskgroep");
            aanpasser.maakDieet();
        }
    }

    protected static void vulScoutsOnline(final String baseUrl, final String username, final String password) throws Exception {
        try (final ScoutsOnline sol = new ScoutsOnline(baseUrl, username, password)) {
            final ScoutsOnlineVuller vuller = new ScoutsOnlineVuller(sol.solHomePage, "HIT 2019", "HIT Helpdeskgroep");

            // vuller.verwijderAlleFormulieren();
            // vuller.maakInitieleFormulieren();
            // vuller.vulFormulierenMetDeRest();
            vuller.maakFormulierenActief(JaNee.JA);
            final List<Formulier> formulieren = sol.solHomePage.hoofdmenu()
                    .openSpelVanMijnSpeleenheid("HIT Helpdeskgroep")
                    .openEvenement("HIT 2019")
                    .submenu().openTabFormulieren()
                    .getFormulieren();
        }
    }
}
