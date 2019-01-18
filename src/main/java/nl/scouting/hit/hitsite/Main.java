package nl.scouting.hit.hitsite;

import nl.scouting.hit.common.AbstractWebApplication;
import nl.scouting.hit.common.Util;
import nl.scouting.hit.joomla.JoomlaPublished;
import nl.scouting.hit.kampinfo.kamp.HitKampenPage;

/**
 * Haalt de formulieren op uit SOL en vult de ShantiId's in bij de juiste kampen in KampInfo op hit.scouting.nl.
 */
public class Main extends AbstractWebApplication {

    public static void main(final String[] args) throws Exception {
        final int jaar = 2019;

        final String kiBaseUrl = "https://hit.scouting.nl";
        final String solBaseUrl = "https://sol.scouting.nl";
        final String solUsername = Util.readUsernameFromFile();
        final String solPassword = Util.readPasswordFromFile();

        KampInfoVuller.neemShantiIdOverInKampInfo(jaar, kiBaseUrl, solBaseUrl, solUsername, solPassword);

        try (final HitWebsiteAdmin ki = new HitWebsiteAdmin(kiBaseUrl, solUsername, solPassword)) {
            final HitKampenPage kampenLijst = ki.openKampInfo().submenu().openHitKampen();
            kampenLijst
                    .unsetFilterJaar()
                    .setFilterJaar(2019)
                    .setFilterPublished(JoomlaPublished.UNPUBLISHED)
                    .unsetFilterStatus()
            ;
            System.out.println("Done!");
        }
    }

}
