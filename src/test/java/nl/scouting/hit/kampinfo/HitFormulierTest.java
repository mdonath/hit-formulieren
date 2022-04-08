package nl.scouting.hit.kampinfo;

import nl.scouting.hit.sol.evenement.tab.formulier.Formulier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HitFormulierTest {

    public static final String GEKOPPELD_KAMP_GEEN_OKK = "HIT Alphen GPS-hike (Grote Paas Speurtocht) (DUITS) (737) ((30104))";
    public static final String GEKOPPELDKAMP_OKK = "HIT Ommen Bushcraft Ouderkind weekend (Optie OUDER) (750) ((30326))";
    public static final String GEWOON_KAMP_FORMULIER = "HIT Mook The Paranoia Project (466)";
    public static final String BASISFORMULIER_OKK = "HIT 2019 Basisformulier Ouder-Kind (niet wijzigen)";

    @Test
    void uiteenrafelen_naam() {
        final Matcher m = HitFormulier.FORMULIERNAAM_PATTERN.matcher(GEWOON_KAMP_FORMULIER);
        assertTrue(m.matches());
        assertEquals("Mook", m.group(1));
        assertEquals("The Paranoia Project", m.group(2));
        assertEquals("466", m.group(3));
    }

    @Test
    void wat_doet_het_basisformulier() {
        final Matcher m = HitFormulier.FORMULIERNAAM_PATTERN.matcher(BASISFORMULIER_OKK);
        assertFalse(m.matches());
    }

    @Test
    void wat_doet_een_gekoppeld_formulier_DUITS() {
        final Matcher m = HitFormulier.FORMULIERNAAM_PATTERN.matcher(GEKOPPELD_KAMP_GEEN_OKK);
        assertFalse(m.matches());
    }

    @Test
    void wat_doet_een_gekoppeld_formulier_OKK() {
        final Matcher m = HitFormulier.FORMULIERNAAM_PATTERN.matcher(GEKOPPELDKAMP_OKK);
        assertFalse(m.matches());
    }

    @Test
    void is_gekoppeld_formulier_DUITS() {
        final HitFormulier hitFormulier = createHitFormulier("11111", GEKOPPELD_KAMP_GEEN_OKK, 1, 3, 2);

        assertTrue(hitFormulier.isGekoppeldFormulier());
        assertEquals("30104", hitFormulier.gekoppeldShantiID, "Moet aan juiste formulier zijn gekoppeld");
        assertEquals("DUITS", hitFormulier.redenKoppeling, "Moet de reden hebben kunnen extracten");
        assertEquals(1, hitFormulier.aantalDeelnemers, "aantalDeelnemers moet kloppen");
        assertEquals(1, hitFormulier.maximumAantalDeelnemers, "maximumAantalDeelnemers moet kloppen");
        assertEquals(1, hitFormulier.gereserveerd, "gereserveerd moet kloppen");
    }

    @Test
    void is_gekoppeld_formulier_OKK() {
        final HitFormulier hitFormulier = createHitFormulier("11111", GEKOPPELDKAMP_OKK, 0, 0, 0);


        assertTrue(hitFormulier.isGekoppeldFormulier());
        assertEquals("30326", hitFormulier.gekoppeldShantiID, "Moet aan juiste formulier zijn gekoppeld");
        assertEquals("Optie OUDER", hitFormulier.redenKoppeling, "Moet de reden hebben kunnen extracten");
    }

    private static HitFormulier createHitFormulier(final String shantiID, final String naam, int aantalDeelnemers, int maximumAantalDeelnemers, int gerserveerd) {
        return new HitFormulier(new Formulier(shantiID, naam, aantalDeelnemers, maximumAantalDeelnemers, gerserveerd));
    }
}