package nl.scouting.hit.sol;

import com.google.common.base.Stopwatch;
import nl.scouting.hit.common.Util;
import nl.scouting.hit.kampinfo.InschrijfformulierAanpasser;
import nl.scouting.hit.kampinfo.ScoutsOnlineVuller;
import nl.scouting.hit.kampinfo.export.KampInfoHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) throws Exception {
        LOGGER.info("START");
        final HitContext context = HitContext.Builder.create()
                .baseUrl("https://sol.scouting.nl")
                .username(Util.readUsernameFromFile())
                .password(Util.readPasswordFromFile())
                .idEvenement(HitConstants.ID_EVENEMENT)
                .naamEvenement(HitConstants.NAAM_EVENEMENT)
                .naamSpeleenheid("HIT Helpdeskgroep")
                .datumDeelnemersinformatie(HitConstants.DATUM_DEELNEMERSINFORMATIE)
                .build();

        final Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            // Vul alle formulieren op basis van de export
            vulScoutsOnline(context);

            // Voer aanpassingen achteraf op ALLE inschrijfformulieren door
            // postfixHIT2024(context);

            // doeAanpassingenVoorFase2(context);

        } finally {
            LOGGER.info(stopwatch.stop().toString());
        }
    }

    protected static void vulScoutsOnline(final HitContext context) throws IOException {
        try (final ScoutsOnline sol = new ScoutsOnline(context.baseUrl, context.username, context.password)) {
            final ScoutsOnlineVuller vuller = new ScoutsOnlineVuller(sol.solHomePage, context.naamEvenement, context.naamSpeleenheid, null);
            vuller.setDatumDeelnemersinformatie(context.datumDeelnemersinformatie);

            // [x] STAP 0: Handmatig: In SOL kopiëren van HIT jaar-1 naar HIT jaar.

            // [x] STAP 1: Verwijderen oude formulieren
            // vuller.verwijderAlleFormulieren();

            // [x] STAP 2: Aanmaken initiele formulieren voor gewone inschrijfformulieren
            // vuller.maakInitieleFormulieren();

            // [x] STAP 3: Breng de formuliernummers weer naar KampInfo; de OUDERLID-met-kind formulieren hebben het SOL-formuliernummer nodig in hun naam
            // Zie hitsite/Main.java
            // deleteCurrentDataFile();

            // [x] STAP 4: Aanmaken Ouder-kind formulieren waarbij de ouder zich inschrijft met een niet-lid kind, haal opnieuw data op uit KampInfo!!!
            // vuller.maakInitieleFormulierenVoorOuderLid();

            // [X] STAP 5: Breng ook de formuliernummers van deze nieuwe formulieren over naar Kampinfo.
            // Handmatig: neem de formuliernummers van de OUDERLID-met-kind formulieren over in KampInfo in het veld voor het ouder-formulier
            // deleteCurrentDataFile();

            // [X] STAP 6:
            // vuller.vulFormulierenMetDeRest();

            // [-] STAP 7:
            // Handmatig: maak voor de kampen waar meerdere kinderen met één ouder kunnen komen, aparte formulieren aan
            // - kopieer de data.json naar een data-extra.json en verwijder alles behalve de betreffende ouder-kind-kampen
            // final ScoutsOnlineVuller extraVuller = new ScoutsOnlineVuller(sol.solHomePage, context.naamEvenement, context.naamSpeleenheid, "data-extra");
            // extraVuller.maakInitieleFormulieren();
            // extraVuller.vulFormulierenMetDeRest();

            // STAP 8: Maak alles actief
            vuller.maakFormulierenActief(JaNee.JA);
        }
    }

    protected static void postfixHIT2024(final HitContext context) throws IOException {
        try (final ScoutsOnline sol = new ScoutsOnline(context.baseUrl, context.username, context.password)) {
            final InschrijfformulierAanpasser aanpasser = new InschrijfformulierAanpasser(sol.solHomePage, context);

            aanpasser.foutjesInAanmakenFormulieren2024();

        }
    }

    protected static void doeAanpassingenVoorFase2(final HitContext context) throws IOException {
        try (final ScoutsOnline sol = new ScoutsOnline(context.baseUrl, context.username, context.password)) {
            final InschrijfformulierAanpasser aanpasser = new InschrijfformulierAanpasser(sol.solHomePage, context);
            aanpasser.fase2();
        }
    }

    private static void deleteCurrentDataFile() {
        KampInfoHelper.deleteDatafile("data-" + HitConstants.HIT_JAAR + ".json");
    }

}
