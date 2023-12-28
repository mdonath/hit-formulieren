package nl.scouting.hit.sol.evenement.tab.formulier;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Formulier {

    public final String naam;
    public final String shantiID;
    public final int aantalDeelnemers;
    public final int maximumAantalDeelnemers;
    public final int gereserveerd;

    protected Formulier(final WebElement row) {
        this(
                extractId(row),
                extractNaam(row),
                extractAantalDeelnemers(row),
                extractMaximumAantalDeelnemers(row),
                extractGereserveerd(row)
        );
    }

    public Formulier(final String shantiID, final String naam, final int aantalDeelnemers, final int maximumAantalDeelnemers, final int gereserveerd) {
        this.shantiID = shantiID;
        this.naam = naam;
        this.aantalDeelnemers = aantalDeelnemers;
        this.maximumAantalDeelnemers = maximumAantalDeelnemers;
        this.gereserveerd = gereserveerd;
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

    private static int extractAantalDeelnemers(final WebElement row) {
        return extractAantal(row, 2);
    }

    private static int extractMaximumAantalDeelnemers(WebElement row) {
        return extractAantal(row, 3);
    }

    private static int extractGereserveerd(WebElement row) {
        return 0; // extractAantal(row, 5);
    }

    private static int extractAantal(WebElement row, int index) {
        String aantal = row.findElements(By.tagName("td")).get(index).getText();
        if ("".equals(aantal)) {
            return 0;
        }
        return Integer.parseInt(aantal);
    }


    public String inschrijfLink(String linkFormat) {
        return String.format(linkFormat, this.shantiID);
    }

}

