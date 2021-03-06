package nl.scouting.hit.sol.evenement.tab.formulier;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Formulier {

    public final String naam;
    public final String shantiID;

    protected Formulier(WebElement row) {
        this(extractId(row), extractNaam(row));
    }

    protected Formulier(final String naam, final String shantiID) {
        this.shantiID = shantiID;
        this.naam = naam;
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

}

