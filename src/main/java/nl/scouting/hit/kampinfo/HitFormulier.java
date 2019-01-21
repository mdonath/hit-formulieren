package nl.scouting.hit.kampinfo;

import nl.scouting.hit.sol.evenement.tab.formulier.Formulier;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HitFormulier extends Formulier {

    public static final Pattern FORMULIERNAAM_PATTERN = Pattern.compile("HIT ([^ ]+) (.*) \\((\\d+)\\)");

    public final String plaats;
    public final String kamp;
    public final String kampinfoID;


    public HitFormulier(Formulier formulier) {
        super(formulier.shantiID, formulier.naam);
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
    }

    public boolean isBasisFormulier() {
        return naam.contains("Basisformulier");
    }

    public boolean isInschrijfFormulier() {
        return this.kampinfoID != null;
    }
}
