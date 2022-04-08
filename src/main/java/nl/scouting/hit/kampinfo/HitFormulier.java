package nl.scouting.hit.kampinfo;

import nl.scouting.hit.sol.evenement.tab.formulier.Formulier;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HitFormulier extends Formulier {

    public static final Pattern FORMULIERNAAM_PATTERN = Pattern.compile("HIT ([^ ]+) (.*) \\((\\d+)\\)");
    public static final Pattern GEKOPPELD_FORMULIERNAAM_PATTERN = Pattern.compile("HIT ([^ ]+) (.*) \\((.*)\\) \\((\\d+)\\) \\(\\((\\d+)\\)\\)");

    public final String plaats;
    public final String kamp;
    public final String kampinfoID;
    public final String gekoppeldShantiID;
    public String redenKoppeling;


    public HitFormulier(final Formulier formulier) {
        super(formulier.shantiID, formulier.naam, formulier.aantalDeelnemers, formulier.maximumAantalDeelnemers, formulier.gereserveerd);
        final Matcher gekoppeldFormulierMatcher = GEKOPPELD_FORMULIERNAAM_PATTERN.matcher(naam);

        if (gekoppeldFormulierMatcher.matches()) {
            plaats = gekoppeldFormulierMatcher.group(1);
            kamp = gekoppeldFormulierMatcher.group(2);
            redenKoppeling = gekoppeldFormulierMatcher.group(3);
            kampinfoID = gekoppeldFormulierMatcher.group(4);
            gekoppeldShantiID = gekoppeldFormulierMatcher.group(5);
        } else {
            final Matcher matcher = FORMULIERNAAM_PATTERN.matcher(naam);
            if (matcher.matches()) {
                plaats = matcher.group(1);
                kamp = matcher.group(2);
                kampinfoID = matcher.group(3);
            } else {
                plaats = null;
                kamp = null;
                kampinfoID = null;
            }
            gekoppeldShantiID = null;
        }
    }

    public boolean isBasisFormulier() {
        return naam.contains("Basisformulier");
    }

    public boolean isInschrijfFormulier() {
        return this.kampinfoID != null;
    }

    public boolean isGekoppeldFormulier() {
        return gekoppeldShantiID != null;
    }
}
