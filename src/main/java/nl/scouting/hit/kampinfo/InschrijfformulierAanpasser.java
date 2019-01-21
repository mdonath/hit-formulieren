package nl.scouting.hit.kampinfo;

import nl.scouting.hit.sol.JaNee;
import nl.scouting.hit.sol.SolHomePage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.FormulierWijzigSamenstellenPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.samenstellen.CheckboxWijzigen;

import java.io.IOException;

/**
 * Vult ScoutsOnline met de gegevens uit KampInfo.
 */
public final class InschrijfformulierAanpasser {

    private static final String KOPPELGROEPJE = "Koppelgroepje";

    private final SolHomePage solHomePage;
    private final String naamEvenement;
    private final String naamSpeleenheid;

    /**
     * Maakt een ScoutsOnlineVuller aan.
     *
     * @param sol             de connectie naar ScoutsOnline
     * @param naamEvenement   de naam van het evenement zoals dat te zien is in het overzicht, bijvoorbeeld "HIT 2019"
     * @param naamSpeleenheid de naam van de organiserende speleenheid
     * @throws IOException
     */
    public InschrijfformulierAanpasser(final SolHomePage sol, final String naamEvenement, final String naamSpeleenheid) throws IOException {
        this.solHomePage = sol;
        this.naamEvenement = naamEvenement;
        this.naamSpeleenheid = naamSpeleenheid;
    }

    public void maakDieet() {
        final FormulierWijzigSamenstellenPage samenstellen = solHomePage.hoofdmenu().openSpelVanMijnSpeleenheid(naamSpeleenheid)
                .openEvenement("HIT 2019")
                .submenu().openTabFormulieren()
                .openFormulier("HIT 2019 Basisformulier (niet wijzigen)")
                .submenu().openTabSamenstellen();

        CheckboxWijzigen checkbox = (CheckboxWijzigen) samenstellen
                .withSelecteerNieuwVeld(FormulierWijzigSamenstellenPage.NieuwVeld.CHECKBOX)
                .toevoegen();

        checkbox.withKoptekst("Dieet")
                .withLocatieVanVeld("Gezondheid en Voeding") // FIXME: Dit gaat nog mis, hij kan ze niet vinden
                .withVerplichtVeld(JaNee.NEE)
                .withZichtbaarVoorGebruiker(JaNee.JA)
                .withNieuweOptie("Geen dieet").toevoegen()
                .withNieuweOptie("Veganistisch").toevoegen()
                .withNieuweOptie("Vegetarisch").toevoegen()
                .withNieuweOptie("Melk allergie").toevoegen()
                .withNieuweOptie("Lactose intolerantie").toevoegen()
                .withNieuweOptie("Noten allergie").toevoegen()
                .withNieuweOptie("Glutenvrij").toevoegen()
                .withNieuweOptie("Dieet gebaseerd op godsdienst, namelijk").toevoegen().withOpenVraag("Dieet gebaseerd op godsdienst, namelijk", JaNee.JA)
                .withNieuweOptie("Anders, namelijk").toevoegen().withOpenVraag("Anders, namelijk", JaNee.JA)
                .wijzigingenOpslaan()
        ;

    }

}
