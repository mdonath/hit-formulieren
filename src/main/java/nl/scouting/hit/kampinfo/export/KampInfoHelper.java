package nl.scouting.hit.kampinfo.export;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;

public final class KampInfoHelper {

    private static final File FILE = new File("data-2022.json");

    /**
     * Private constructor.
     */
    private KampInfoHelper() {
        super();
    }

    public static void download() {
        if (!FILE.exists()) {
            try {
                final URL url = new URL("https://hit.scouting.nl/index.php?option=com_kampinfo&view=shanti&format=raw");
                try (final ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                     final FileOutputStream fos = new FileOutputStream(FILE)
                ) {
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                }
            } catch (final java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<KampInfoFormulierExportRegel> readData() throws IOException {
        final ObjectMapper om = new ObjectMapper();
        return om.readValue(FILE, new TypeReference<List<KampInfoFormulierExportRegel>>() {
        });
    }

}
