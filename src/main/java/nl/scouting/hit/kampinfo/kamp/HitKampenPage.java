package nl.scouting.hit.kampinfo.kamp;

import nl.scouting.hit.common.Valuable;
import nl.scouting.hit.joomla.JoomlaPublished;
import nl.scouting.hit.kampinfo.AbstractKampInfoPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HitKampenPage extends AbstractKampInfoPage<HitKampenPage> {

    @FindBy(id = "list_limit")
    private WebElement listLimit;

    @FindBy(id = "filter_jaar")
    private WebElement filterJaar;

    @FindBy(id = "filter_plaats")
    private WebElement filterPlaats;

    @FindBy(id = "filter_published")
    private WebElement filterPublished;

    public HitKampenPage(final WebDriver driver) {
        super(driver);
    }

    /**
     * List limit.
     * Possible values: 0(all), 5, 10, 15, 20, 25, 30, 50, 100, 200, 500.
     *
     * @param limit
     * @return
     */
    public HitKampenPage setListLimit(final int limit) {
        selectByValue(listLimit, String.valueOf(limit));
        return this;
    }

    public HitKampenPage setFilterPlaats(final String plaatsNaam, final int jaar) {
        selectByVisibleText(filterPlaats, String.format("%s (%d)", plaatsNaam, jaar));
        return this;
    }

    public HitKampenPage unsetFilterPlaats() {
        return unsetFilter(filterPlaats);
    }

    public HitKampenPage setFilterJaar(final int jaar) {
        selectByVisibleText(filterJaar, String.valueOf(jaar));
        return this;
    }

    public HitKampenPage unsetFilterJaar() {
        return unsetFilter(filterJaar);
    }

    public HitKampenPage setFilterPublished(final JoomlaPublished published) {
        selectByValue(filterPublished, published);
        return this;
    }

    public HitKampenPage unsetFilterStatus() {
        return unsetFilter(filterPublished, JoomlaPublished.NOT_SPECIFIED);
    }

    public HitKampBewerkGegevensPage openHitKamp(final String kampNaam) {
        scrollIntoViewCenteredAndClick(driver.findElement(By.linkText(kampNaam)));
        return new HitKampBewerkGegevensPage(driver);
    }

    private HitKampenPage unsetFilter(final WebElement field, final Valuable valuable) {
        scrollToTop();
        selectByValue(field, valuable == null ? "-1" : valuable.getId());
        return this;
    }

    private HitKampenPage unsetFilter(final WebElement field) {
        return unsetFilter(field, null);
    }
}
