package nl.scouting.hit.hitsite;

import nl.scouting.hit.common.AbstractWebApplication;
import nl.scouting.hit.common.Util;
import nl.scouting.hit.sol.HitConstants;

/**
 * Haalt de formulieren op uit SOL en vult de ShantiId's in bij de juiste kampen in KampInfo op hit.scouting.nl.
 */
public class Main extends AbstractWebApplication {

    public static void main(final String[] args) throws Exception {
        final int jaar = HitConstants.HIT_JAAR;

        final String kiBaseUrl = "https://hit.scouting.nl";
        final String solBaseUrl = "https://sol.scouting.nl";
        final String solUsername = Util.readUsernameFromFile();
        final String solPassword = Util.readPasswordFromFile();

        KampInfoVuller.neemShantiIdOverInKampInfo(jaar, kiBaseUrl, solBaseUrl, solUsername, solPassword);
    }

}
