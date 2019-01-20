package nl.scouting.hit.hitsite;

import nl.scouting.hit.kampinfo.kamp.HitKampenPage;
import nl.scouting.hit.sol.ScoutsOnline;
import nl.scouting.hit.sol.evenement.tab.formulier.Formulier;

import java.util.List;

/**
 * Vult KampInfo op basis van gegevens in Scouts Online.
 */
public class KampInfoVuller {

    /**
     * Haalt de inschrijfformulieren in ScoutsOnline op van een specifiek jaar en vult het formulier-id (shantiId) in bij
     * elk kamponderdeel in KampInfo.
     *
     * @param jaar
     * @param kiBaseUrl
     * @param solBaseUrl
     * @param solUsername
     * @param solPassword
     * @throws Exception
     */
    public static void neemShantiIdOverInKampInfo(final int jaar, final String kiBaseUrl, final String solBaseUrl, final String solUsername, final String solPassword) throws Exception {
        final List<Formulier> formulieren = getFormulieren(solBaseUrl, solUsername, solPassword, jaar);
        try (final HitWebsiteAdmin hitwebsite = new HitWebsiteAdmin(kiBaseUrl, solUsername, solPassword)) {
            final HitKampenPage kampenLijst = hitwebsite.openKampInfo()
                    .submenu().openHitKampen()
                    .setListLimit(100);

            formulieren.stream()
                    .filter(Formulier::isInschrijfFormulier)
                    .forEach(formulier ->
                            kampenLijst
                                    .setFilterJaar(jaar)
                                    .setFilterPlaats(formulier.plaats, jaar)
                                    .openHitKamp(formulier.kamp)
                                    .tabs().admin()
                                    .setFieldShantiFormuliernummer(formulier.shantiID)
                                    .editButtons().save()
                    );
        }
    }

    private static List<Formulier> getFormulieren(final String solBaseUrl, final String solUsername, final String solPassword, final int jaar) throws Exception {
        try (final ScoutsOnline sol = new ScoutsOnline(solBaseUrl, solUsername, solPassword)) {
            return sol.solHomePage.hoofdmenu()
                    .openSpelVanMijnSpeleenheid("HIT Helpdeskgroep")
                    .openEvenement("HIT " + jaar)
                    .submenu().openTabFormulieren()
                    .getFormulieren();
        }
    }
}
