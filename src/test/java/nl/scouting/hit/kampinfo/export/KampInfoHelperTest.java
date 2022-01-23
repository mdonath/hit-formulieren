package nl.scouting.hit.kampinfo.export;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class KampInfoHelperTest {

    private final File file = new File("data-2022.json");

    @Test
    void testDownload() {
        if (file.delete()) {
            System.out.println("Het bestand was er al, maar nu niet meer!");
        }
        assertFalse(file.exists());
        KampInfoHelper.download();
        assertTrue(file.exists());
    }

    @Test
    void testExpand() throws IOException {
        if (!file.exists()) {
            System.out.println("DOWNLOADING");
            KampInfoHelper.download();
        }

        final List<KampInfoFormulierExportRegel> data = KampInfoHelper.readData();
        assertEquals(89, data.size());

        final List<KampInfoFormulierExportRegel> expanded = KampInfoHelper.expand(data);
        assertTrue(expanded.size() > data.size());
        assertEquals(91, expanded.size());
        expanded.forEach(regel -> System.out.printf("[%d] %s%n", regel.getShantiID(), regel.getFormulierNaam()));
    }
}