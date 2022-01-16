package nl.scouting.hit.sol.evenement.tab.formulier;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TabFormulierenOverzichtZoekModusPage extends TabFormulierenOverzichtPage {

    @FindBy(id = "filter_frm_location_nm")
    private WebElement filterLocatie;

    @FindBy(id = "filter_frm_nm")
    private WebElement filterFormuliernaam;

    @FindBy(id = "filter_frm_project_ds")
    private WebElement filterProjectcode;

    @FindBy(className="filterSearch")
    private WebElement buttonZoek;

    public TabFormulierenOverzichtZoekModusPage(final WebDriver driver) {
        super(driver);
    }

    public TabFormulierenOverzichtZoekModusPage filterLocatie(final String s) {
        clearAndSendKeys(filterLocatie, s);
        return this;
    }

    public TabFormulierenOverzichtZoekModusPage filterFormuliernaam(final String s) {
        clearAndSendKeys(filterFormuliernaam, s);
        return this;
    }

    public TabFormulierenOverzichtZoekModusPage filterProjectcode(final String s) {
        clearAndSendKeys(filterProjectcode, s);
        return this;
    }

    public TabFormulierenOverzichtZoekModusPage zoekMetFilter() {
        scrollIntoViewCenteredAndClick(buttonZoek);
        return this;
    }

}
