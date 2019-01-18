package nl.scouting.hit.sol.evenement.tab.formulier;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;

class TabFormulierenOverzichtPageTest {

    @Test
    public void uiteenrafelen_naam() {
        Matcher m = Formulier.FORMULIERNAAM_PATTERN.matcher("HIT Mook The Paranoia Project (466)");
        assertTrue(m.matches());
        assertEquals("Mook", m.group(1));
        assertEquals("The Paranoia Project", m.group(2));
        assertEquals("466", m.group(3));
    }

    @Test
    public void wat_doet_het_basisformulier() {
        Matcher m = Formulier.FORMULIERNAAM_PATTERN.matcher("HIT 2019 Basisformulier Ouder-Kind (niet wijzigen)");
        assertFalse(m.matches());
    }
}