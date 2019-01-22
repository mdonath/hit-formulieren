package nl.scouting.hit.kampinfo;

import nl.scouting.hit.sol.JaNee;
import nl.scouting.hit.sol.SolHomePage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.FormulierWijzigSamenstellenPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.samenstellen.CheckboxWijzigen;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.samenstellen.MeerdereTekstRegelsWijzigen;

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
        final FormulierWijzigSamenstellenPage samenstellen = solHomePage
                .hoofdmenu().openSpelVanMijnSpeleenheid(naamSpeleenheid)
                .openEvenement("HIT 2019")
                .submenu().openTabFormulieren()
                .openFormulier("HIT 2019 Basisformulier (niet wijzigen)")
                .submenu().openTabSamenstellen();

        final CheckboxWijzigen checkbox = (CheckboxWijzigen) samenstellen
                .withSelecteerNieuwVeld(FormulierWijzigSamenstellenPage.NieuwVeld.CHECKBOX)
                .toevoegen();

        checkbox.withKoptekst("Dieet")
                .withLocatieVanVeldNa("'Gezondheid en Voeding'")
                .withVerplichtVeld(JaNee.NEE)
                .withZichtbaarVoorGebruiker(JaNee.JA)
                .withNieuweOptie("Geen dieet").toevoegen()
                .withNieuweOptie("Veganistisch").toevoegen()
                .withNieuweOptie("Vegetarisch").toevoegen()
                .withNieuweOptie("Melk allergie").toevoegen()
                .withNieuweOptie("Lactose intolerantie").toevoegen()
                .withNieuweOptie("Noten allergie").toevoegen()
                .withNieuweOptie("Glutenvrij").toevoegen()
                .withNieuweOptie("Dieet gebaseerd op godsdienst, namelijk").toevoegen()
                .withOpenVraag("Dieet gebaseerd op godsdienst, namelijk", JaNee.JA)
                .withNieuweOptie("Anders, namelijk").toevoegen()
                .withOpenVraag("Anders, namelijk", JaNee.JA)
                .wijzigingenOpslaan()
        ;
    }

    public void maakDieetKind() {
        final FormulierWijzigSamenstellenPage samenstellen = solHomePage
                .hoofdmenu().openSpelVanMijnSpeleenheid(naamSpeleenheid)
                .openEvenement("HIT 2019")
                .submenu().openTabFormulieren()
                .openFormulier("HIT 2019 Basisformulier Ouder-Kind (niet wijzigen)")
                .submenu().openTabSamenstellen();

//        final CheckboxWijzigen checkbox = (CheckboxWijzigen) samenstellen
//                .withSelecteerNieuwVeld(FormulierWijzigSamenstellenPage.NieuwVeld.CHECKBOX)
//                .toevoegen();
//        checkbox.withKoptekst("Dieet (kind)")
//                .withLocatieVanVeldNa("Heb je een zwemdiploma")
//                .withVerplichtVeld(JaNee.NEE)
//                .withZichtbaarVoorGebruiker(JaNee.JA)
//                .withNieuweOptie("Geen dieet").toevoegen()
//                .withNieuweOptie("Veganistisch").toevoegen()
//                .withNieuweOptie("Vegetarisch").toevoegen()
//                .withNieuweOptie("Melk allergie").toevoegen()
//                .withNieuweOptie("Lactose intolerantie").toevoegen()
//                .withNieuweOptie("Noten allergie").toevoegen()
//                .withNieuweOptie("Glutenvrij").toevoegen()
//                .withNieuweOptie("Dieet gebaseerd op godsdienst, namelijk").toevoegen()
//                .withOpenVraag("Dieet gebaseerd op godsdienst, namelijk", JaNee.JA)
//                .withNieuweOptie("Anders, namelijk").toevoegen()
//                .withOpenVraag("Anders, namelijk", JaNee.JA)
//                .wijzigingenOpslaan()
//        ;

        final MeerdereTekstRegelsWijzigen meerdere = (MeerdereTekstRegelsWijzigen) samenstellen
                .withSelecteerNieuwVeld(FormulierWijzigSamenstellenPage.NieuwVeld.TEKSTREGELS)
                .toevoegen();
        meerdere
                .withKoptekst("Heeft de deelnemer (kind) een allergie, lichamelijke of geestelijke beperkingen of gebruikt hij/zij medicijnen?")
                .withHelptekst("")
                .withLocatieVanVeldNa("Dieet (kind)")
                .withAantalRegels(3)
                .withBreedte(MeerdereTekstRegelsWijzigen.Breedte.BREED)
                .opslaanWijzigingen()
        ;
    }

    public void maakDieetOuder() {
        final FormulierWijzigSamenstellenPage samenstellen = solHomePage
                .hoofdmenu().openSpelVanMijnSpeleenheid(naamSpeleenheid)
                .openEvenement("HIT 2019")
                .submenu().openTabFormulieren()
                .openFormulier("HIT 2019 Basisformulier Ouder-Kind (niet wijzigen)")
                .submenu().openTabSamenstellen();

        final CheckboxWijzigen checkbox = (CheckboxWijzigen) samenstellen
                .withSelecteerNieuwVeld(FormulierWijzigSamenstellenPage.NieuwVeld.CHECKBOX)
                .toevoegen();
        checkbox.withKoptekst("Dieet (ouder)")
                .withLocatieVanVeldNa("Ouder is lid van Scouting")
                .withVerplichtVeld(JaNee.NEE)
                .withZichtbaarVoorGebruiker(JaNee.JA)
                .withNieuweOptie("Geen dieet").toevoegen()
                .withNieuweOptie("Veganistisch").toevoegen()
                .withNieuweOptie("Vegetarisch").toevoegen()
                .withNieuweOptie("Melk allergie").toevoegen()
                .withNieuweOptie("Lactose intolerantie").toevoegen()
                .withNieuweOptie("Noten allergie").toevoegen()
                .withNieuweOptie("Glutenvrij").toevoegen()
                .withNieuweOptie("Dieet gebaseerd op godsdienst, namelijk").toevoegen()
                .withOpenVraag("Dieet gebaseerd op godsdienst, namelijk", JaNee.JA)
                .withNieuweOptie("Anders, namelijk").toevoegen()
                .withOpenVraag("Anders, namelijk", JaNee.JA)
                .wijzigingenOpslaan()
        ;

        final MeerdereTekstRegelsWijzigen meerdere = (MeerdereTekstRegelsWijzigen) samenstellen
                .withSelecteerNieuwVeld(FormulierWijzigSamenstellenPage.NieuwVeld.TEKSTREGELS)
                .toevoegen();
        meerdere
                .withKoptekst("Heeft de deelnemer (ouder) een allergie, lichamelijke of geestelijke beperkingen of gebruikt hij/zij medicijnen?")
                .withHelptekst("")
                .withLocatieVanVeldNa("Dieet (ouder)")
                .withAantalRegels(3)
                .withBreedte(MeerdereTekstRegelsWijzigen.Breedte.BREED)
                .opslaanWijzigingen()
        ;
    }
}
