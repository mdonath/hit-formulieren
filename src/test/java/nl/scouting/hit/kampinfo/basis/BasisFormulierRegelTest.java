package nl.scouting.hit.kampinfo.basis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasisFormulierRegelTest {

    @Test
    public void createEnkelvoudig() {
        BasisFormulierRegel regel = new BasisFormulierRegel.BasisFormulierRegelBuilder()
                .withJaar(2020)
                .withKampnaam("Basisformulier")
                .build();
        assertEquals("HIT 2020 Basisformulier (niet wijzigen)", regel.getFormulierNaam());
    }

    @Test
    public void createOuderKindIsLid() {
        BasisFormulierRegel regel = new BasisFormulierRegel.BasisFormulierRegelBuilder()
                .withJaar(2020)
                .withKampnaam("Basisformulier Ouder-Kind")
                .build();
        assertEquals("HIT 2020 Basisformulier Ouder-Kind (niet wijzigen)", regel.getFormulierNaam());
    }
}