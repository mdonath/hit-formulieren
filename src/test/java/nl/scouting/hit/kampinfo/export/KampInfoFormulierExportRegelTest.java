package nl.scouting.hit.kampinfo.export;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KampInfoFormulierExportRegelTest {

    private KampInfoFormulierExportRegel regel;

    @BeforeEach
    public void setup() {
        regel = new KampInfoFormulierExportRegel();
    }

    @Test
    public void testCleanKampNaam() {
        String cleaned = KampInfoFormulierExportRegel.cleanKampnaam("a!B@c#D$e%F^g&H*i(J)k-l?M.n,o");
        assertEquals("aBcDeFgHi(J)k-l?M.n,o", cleaned, "alle illegale karakters moeten weg zijn");
    }

    @Test
    public void testAccentjes() {
        String cleaned = KampInfoFormulierExportRegel.cleanKampnaam("Fryslân Australië");
        assertEquals("Fryslân Australië", cleaned, "alle illegale karakters moeten weg zijn");
    }

    @Test
    public void buildKampNaam_te_lang_wordt_afgekapt_tot_70() {
        String kampNaam = KampInfoFormulierExportRegel.buildKampNaam("Heerenveen", "Scouts Guide to an Apocalypse presents The Quest For Riches", 12345);
        assertEquals("HIT Heerenveen Scouts Guide to an Apocalypse presents The Ques (12345)", kampNaam, "max 70");
    }

    @Test
    public void buildKampNaam_indien_kort_genoeg_dan_niets_doen() {
        String kampNaam = KampInfoFormulierExportRegel.buildKampNaam("Mook", "ZeilHIT", 123);
        assertEquals("HIT Mook ZeilHIT (123)", kampNaam, "max 70");
    }

}