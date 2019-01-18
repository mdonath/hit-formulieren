package nl.scouting.hit.kampinfo;

import nl.scouting.hit.kampinfo.export.KampInfoDatum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KampInfoDatumTest {

    @Test
    public void omsmurfen_van_maand() {
        KampInfoDatum datum = new KampInfoDatum(1, "januari", 2019);
        assertEquals(1, datum.getMaand());
    }
}