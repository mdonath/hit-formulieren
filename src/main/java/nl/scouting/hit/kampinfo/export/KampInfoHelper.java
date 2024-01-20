package nl.scouting.hit.kampinfo.export;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Stream;

public final class KampInfoHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(KampInfoHelper.class);

    /**
     * Private constructor.
     */
    private KampInfoHelper() {
        super();
    }

    public static List<KampInfoFormulierExportRegel> downloadReadAndExpand(final String fileName) throws IOException {
        download(fileName);
        return expand(readData(fileName));
    }

    public static void download(final String fileName) {
        final File file = new File(fileName);
        if (!file.exists()) {
            LOGGER.info("Ophalen nieuw bestand, opgeslagen in: {}", file.getAbsolutePath());
            try {
                final URL url = new URL("https://hit.scouting.nl/index.php?option=com_kampinfo&view=shanti&format=raw");
                try (final ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                     final FileOutputStream fos = new FileOutputStream(file)
                ) {
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                }
            } catch (final java.io.IOException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            LOGGER.info("Hergebruik bestaand bestand!");
        }
    }

    public static List<KampInfoFormulierExportRegel> readData(final String fileName) throws IOException {
        final ObjectMapper om = new ObjectMapper();
        return om.readValue(new File(fileName), new TypeReference<>() {
        });
    }

    public static List<KampInfoFormulierExportRegel> expand(final List<KampInfoFormulierExportRegel> exported) {
        return exported.stream()
                .flatMap(regel -> {
                    if (regel.isOuderKindKamp() && regel.isOuderLid()) {
                        // Uitsplitsen naar twee varianten
                        return Stream.of(
                                new KampInfoKindFormulierExportRegel(regel),
                                new KampInfoOuderFormulierExportRegel(regel)
                        );
                    }
                    return Stream.of(regel);
                })
                .toList();
    }

    public static void deleteDatafile(final String fileName) {
        final File file = new File(fileName);
        if (file.exists()) {
            try {
                Files.delete(file.toPath());
                LOGGER.info("Bestand {} is verwijderd.", file.getName());
            } catch (final IOException e) {
                LOGGER.error("FOUT bij verwijderen databestand {}: {}", file.getName(), e.getMessage());
            }
        } else {
            LOGGER.error("Bestand {} bestaat NIET!", file.getName());
        }
    }
}
