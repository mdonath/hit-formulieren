package nl.scouting.hit.sol.evenement.tab.formulier;

import nl.scouting.hit.kampinfo.KampInfoFormulierExportRegel;
import nl.scouting.hit.sol.evenement.AbstractEvenementPage;
import nl.scouting.hit.sol.evenement.tab.formulier.nieuw.FormulierSoortAanmeldingNieuwPage;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.FormulierWijzigBasisPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Formulieren van een evenement.
 */
public class TabFormulierenOverzichtPage extends AbstractEvenementPage {

    public static class Formulier {
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
    }

    @FindBy(linkText = "Formulier toevoegen")
    private WebElement buttonFormulierToevoegen;

    public TabFormulierenOverzichtPage(final WebDriver driver) {
        super(driver);
    }

    public FormulierSoortAanmeldingNieuwPage toevoegenFormulier() {
        scrollIntoViewAndClick(buttonFormulierToevoegen);
        return new FormulierSoortAanmeldingNieuwPage(driver);
    }

    public boolean hasFormulier(final String formulierNaam) {
        return !driver.findElements(getLocatorFormulierNaam(formulierNaam)).isEmpty();
    }

    public boolean hasFormulier(final KampInfoFormulierExportRegel regel) {
        return hasFormulier(regel.getFormulierNaam()) ||
                hasFormulierMetKampInfoID(regel);
    }

    protected boolean hasFormulierMetKampInfoID(final KampInfoFormulierExportRegel regel) {
        return !driver.findElements(getLocatorKampID(regel)).isEmpty();
    }

    private By getLocatorKampID(final KampInfoFormulierExportRegel regel) {
        return By.partialLinkText(String.format(" (%d)", regel.getKampID()));
    }

    private By getLocatorFormulierNaam(final String formulierNaam) {
        return By.linkText(formulierNaam);
    }

    public FormulierWijzigBasisPage openFormulier(final KampInfoFormulierExportRegel regel) {
        driver.findElement(getLocatorKampID(regel)).click();
        return new FormulierWijzigBasisPage(driver);
    }

    public FormulierWijzigBasisPage openFormulier(final String formulierNaam) {
        driver.findElement(getLocatorFormulierNaam(formulierNaam)).click();
        return new FormulierWijzigBasisPage(driver);
    }

    public List<Formulier> getFormulieren() {
        final List<Formulier> result = new ArrayList<>();
        driver.findElements(By.xpath("//table[@class='filter']/tbody/tr"))
                .forEach(row -> result.add(Formulier.create(row)));
        return result;
    }
}
