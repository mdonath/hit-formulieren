package nl.scouting.hit.kampinfo.export;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KampInfoHelperTest {

    @Test
    void testDownload() {
        final File file = new File("data-2022.json");
        if (file.delete()) {
            System.out.println("Het bestand was er al, maar nu niet meer!");
        }
        assertFalse(file.exists());
        KampInfoHelper.download();
        assertTrue(file.exists());
    }
}