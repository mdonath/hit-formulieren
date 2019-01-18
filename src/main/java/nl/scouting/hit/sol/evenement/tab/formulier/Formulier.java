package nl.scouting.hit.sol.evenement.tab.formulier;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Formulier {
    public static final Pattern FORMULIERNAAM_PATTERN = Pattern.compile("HIT ([^ ]+) (.*) \\((\\d+)\\)");

    public final String plaats;
    public final String naam;
    public final String kamp;
    public final String kampinfoID;
    public final String shantiID;

    private Formulier(final String naam, final String shantiID, final String kamp, final String plaats, final String kampinfoID) {
        this.naam = naam;
        this.plaats = plaats;
        this.shantiID = shantiID;
        this.kamp = kamp;
        this.kampinfoID = kampinfoID;
    }

    public static Formulier create(final WebElement row) {
        final String naam = extractNaam(row);
        final Matcher matcher = FORMULIERNAAM_PATTERN.matcher(naam);
        if (matcher.matches()) {
            return new Formulier(naam, extractId(row), matcher.group(2), matcher.group(1), matcher.group(3));
        } else {
            return new Formulier(naam, extractId(row), null, null, null);
        }
    }

    private static String extractId(final WebElement row) {
        final String[] split = row.findElement(By.tagName("a"))
                .getAttribute("href")
                .split("/");
        return split[split.length - 1];
    }

    private static String extractNaam(final WebElement row) {
        final WebElement link = row.findElement(By.tagName("a"));
        return link.getText();
    }

    public boolean isBasisFormulier() {
        return naam.contains("Basisformulier");
    }

    public boolean isInschrijfFormulier() {
        return this.kampinfoID != null;
    }
}
