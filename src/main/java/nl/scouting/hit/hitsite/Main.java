package nl.scouting.hit.hitsite;

import nl.scouting.hit.common.AbstractWebApplication;
import nl.scouting.hit.common.Util;
import nl.scouting.hit.joomla.JoomlaJaNee;
import nl.scouting.hit.kampinfo.kamp.HitKampenPage;

/**
 * Haalt de formulieren op uit SOL en vult de ShantiId's in bij de juiste kampen in KampInfo op hit.scouting.nl.
 */
public class Main extends AbstractWebApplication {

    public static void main(final String[] args) throws Exception {
        final int jaar = 2022;

        final String kiBaseUrl = "https://hit.scouting.nl";
        final String solBaseUrl = "https://sol.scouting.nl";
        final String solUsername = Util.readUsernameFromFile();
        final String solPassword = Util.readPasswordFromFile();

        KampInfoVuller.neemShantiIdOverInKampInfo(jaar, kiBaseUrl, solBaseUrl, solUsername, solPassword);

        // testje(kiBaseUrl, solUsername, solPassword);
    }

    private static void testje(String kiBaseUrl, String solUsername, String solPassword) throws Exception {
        try (final HitWebsiteAdmin hitwebsite = new HitWebsiteAdmin(kiBaseUrl, solUsername, solPassword)) {
            final HitKampenPage kampenLijst = hitwebsite.openKampInfo().submenu().openHitKampen();
            kampenLijst
                    .setFilterPlaats("Alphen", 2019)
                    .openHitKamp("Alphen Hakt! [GAAT NIET DOOR]")
                    .tabs().gegevensVanEenHitKamp()
                    .withHitPlaats("Alphen", 2019)
                    .withStartDatumTijd("19-04-2019 19:00")
                    .withEindDatumTijd("22-04-2019 15:00")
                    .withDeelnamekosten(42)
                    .withIsOuderKind(JoomlaJaNee.JA)
                    .withAfwijkendeStartlokatie(JoomlaJaNee.NEE)
                    .withSublocatie("")
                    .withAkkoordKamp(JoomlaJaNee.JA)
                    .withAkkoordPlaats(JoomlaJaNee.JA)
            ;
            System.out.println("Done!");
        }
    }

}
