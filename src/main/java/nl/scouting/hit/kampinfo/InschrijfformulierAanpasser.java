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
                .setFieldSelecteerNieuwVeld(FormulierWijzigSamenstellenPage.NieuwVeld.CHECKBOX)
                .toevoegen();

        checkbox.setFieldKoptekst("Dieet")
                .setLocatieVanVeld("Gezondheid en Voeding") // FIXME: Dit gaat nog mis, hij kan ze niet vinden
                .setfieldVerplichtVeld(JaNee.NEE)
                .setfieldZichtbaarVoorGebruiker(JaNee.JA)
                .setFieldNieuweOptie("Geen dieet").toevoegen()
                .setFieldNieuweOptie("Veganistisch").toevoegen()
                .setFieldNieuweOptie("Vegetarisch").toevoegen()
                .setFieldNieuweOptie("Melk allergie").toevoegen()
                .setFieldNieuweOptie("Lactose intolerantie").toevoegen()
                .setFieldNieuweOptie("Noten allergie").toevoegen()
                .setFieldNieuweOptie("Glutenvrij").toevoegen()
                .setFieldNieuweOptie("Dieet gebaseerd op godsdienst, namelijk").toevoegen().setFieldOpenVraag("Dieet gebaseerd op godsdienst, namelijk", JaNee.JA)
                .setFieldNieuweOptie("Anders, namelijk").toevoegen().setFieldOpenVraag("Anders, namelijk", JaNee.JA)
                .wijzigingenOpslaan()
        ;

    }

}
