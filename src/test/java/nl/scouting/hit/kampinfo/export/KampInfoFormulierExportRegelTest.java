package nl.scouting.hit.kampinfo.export;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KampInfoFormulierExportRegelTest {

    @Test
    void testCleanKampNaam() {
        final String cleaned = KampInfoFormulierExportRegel.cleanKampnaam("a!B@c#D$e%F^g&H*i(J)k-l?M.n,o");
        assertEquals("aBcDeFgHi(J)k-l?M.n,o", cleaned, "alle illegale karakters moeten weg zijn");
    }

    @Test
    void testAccentjes() {
        final String cleaned = KampInfoFormulierExportRegel.cleanKampnaam("Fryslân Australië");
        assertEquals("Fryslan Australie", cleaned, "alle illegale karakters moeten weg zijn");
    }

    @Test
    void buildKampNaam_te_lang_wordt_afgekapt_tot_70() {
        final KampInfoFormulierExportRegel regel = new KampInfoFormulierExportRegel();
        regel.locatie = "Heerenveen";
        regel.kampnaam = "Scouts Guide to an Apocalypse presents The Quest For Riches";
        regel.kampID = 12345;
        final String kampNaam = regel.getFormulierNaam();
        assertEquals("HIT Heerenveen Scouts Guide to an Apocalypse presents The Ques (12345)", kampNaam, "max 70");
    }

    @Test
    void buildKampNaam_indien_kort_genoeg_dan_niets_doen() {
        final KampInfoFormulierExportRegel regel = new KampInfoFormulierExportRegel();
        regel.locatie = "Mook";
        regel.kampnaam = "ZeilHIT";
        regel.kampID = 123;
        final String kampNaam = regel.getFormulierNaam();
        assertEquals("HIT Mook ZeilHIT (123)", kampNaam, "max 70");
    }

    @Test
    void buildKampNaam_ouder_is_lid_formulier() {
        final KampInfoFormulierExportRegel regel = new KampInfoFormulierExportRegel();
        regel.locatie = "Mook";
        regel.kampnaam = "Outdoor Ouder-KinderKamp";
        regel.isOuderKindKamp = true;
        regel.isOuderLid = false;
        regel.shantiID = 42999;
        regel.kampShantiID = 30000;
        regel.kampShantiOuderID = 40010;
        regel.kampID = 1234;

        assertEquals("HIT Mook Outdoor Ouder-KinderKamp (1234)", regel.getFormulierNaam(), "Moet niets toegevoegd hebben");

        regel.isOuderLid = true;
        final KampInfoKindFormulierExportRegel kindRegel = new KampInfoKindFormulierExportRegel(regel);
        assertEquals("HIT Mook Outdoor Ouder-KinderKamp (KIND) (1234)", kindRegel.getFormulierNaam(), "Moet (KIND) toegevoegd hebben");

        final KampInfoOuderFormulierExportRegel ouderRegel = new KampInfoOuderFormulierExportRegel(regel);
        assertEquals("HIT Mook Outdoor Ouder-KinderKamp (OUDER) (1234) ((30000))", ouderRegel.getFormulierNaam(), "Moet (OUDER) toegevoegd hebben");

        // indien te lang, dan moet het van de naam af en niet van de specifier
//        regel.kampnaam = "Scouts Guide to an Apocalypse presents The Quest For Riches";
//        assertEquals("HIT Mook Scouts Guide to an Apocalypse presents The Qu (OUDER) (12345)", regel.getOuderFormulierNaam(), "Moet (OUDER) toegevoegd hebben");
//        assertEquals("HIT Mook Scouts Guide to an Apocalypse presents The Que (KIND) (12345)", regel.getFormulierNaam(), "Moet (KIND) toegevoegd hebben");
    }

}