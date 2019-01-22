package nl.scouting.hit.sol.evenement.tab.formulier.wijzig.samenstellen;

import nl.scouting.hit.common.Datum;
import nl.scouting.hit.kampinfo.export.KampInfoFormulierExportRegel;
import nl.scouting.hit.sol.JaNee;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.FormulierWijzigSamenstellenPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DeelnamekostenWijzigen extends AbstractVeldWijzigen<DeelnamekostenWijzigen> {

    @FindBy(name = "fld_led_id")
    private WebElement fieldGrootboek;

    @FindBy(name = "new_option_txt")
    private WebElement fieldBetalingstermijn;

    @FindBy(xpath = "//input[@name=\"new_option_txt\"]/../input[2]")
    private WebElement buttonBetalingstermijnToevoegen;

    @FindBy(name = "date_of_registration_defines_amount_yn")
    private List<WebElement> fieldGebruikOmslagdatum;

    @FindBy(name = "fld_multiple_amounts_yn")
    private List<WebElement> fieldAnderBedragPerSoortLid;

    @FindBy(name = "fld_minimum_pay_am_inputValue")
    private WebElement fieldMinimumBedrag;

    public DeelnamekostenWijzigen(WebDriver driver) {
        super(driver);
    }

    // UTIL //

    public DeelnamekostenWijzigen createAndChangeTermijn(final KampInfoFormulierExportRegel regel, final String termijn) {
        if (!hasBetalingstermijn(termijn)) {
            withBetalingstermijn(termijn);
            toevoegenBetalingstermijn();
        }
        withBedrag(termijn, String.valueOf(regel.getDeelnemersprijs()));
        withBetaalDatum(termijn, regel.getInschrijvingStart());
        return this;
    }

    //

    public DeelnamekostenWijzigen withGrootboek(String s) {
        selectByVisibleText(fieldGrootboek, s);
        return this;
    }

    public boolean hasBetalingstermijn(String betalingstermijn) {
        return !driver.findElements(By.xpath("//table//input[@value='" + betalingstermijn + "']")).isEmpty();
    }

    public DeelnamekostenWijzigen withBetalingstermijn(String s) {
        clearAndSendKeys(fieldBetalingstermijn, s);
        return this;
    }

    public DeelnamekostenWijzigen toevoegenBetalingstermijn() {
        scrollIntoViewAndClick(buttonBetalingstermijnToevoegen);
        return this;
    }

    public DeelnamekostenWijzigen withBedrag(String betalingstermijn, String bedrag) {
        By by = By.xpath("//table//input[@value='" + betalingstermijn + "']/../following-sibling::td/input[contains(@name, 'opt_am')]");
        clearAndSendKeys(driver.findElement(by), bedrag);
        return this;
    }


    public DeelnamekostenWijzigen withBetaalDatum(String betalingstermijn, Datum datum) {
        return withBetaalDatum(betalingstermijn, datum.getDag(), datum.getMaand(), datum.getJaar());
    }

    public DeelnamekostenWijzigen withBetaalDatum(String betalingstermijn, int dag, int maand, int jaar) {
        By byDag = By.xpath("//table//input[@value='" + betalingstermijn + "']/../following-sibling::td[2]//select[1]");
        By byMaand = By.xpath("//table//input[@value='" + betalingstermijn + "']/../following-sibling::td[2]//select[2]");
        By byJaar = By.xpath("//table//input[@value='" + betalingstermijn + "']/../following-sibling::td[2]//select[3]");
        setFieldsDatum(driver.findElement(byDag), dag
                , driver.findElement(byMaand), maand
                , driver.findElement(byJaar), jaar);
        return this;
    }

    public DeelnamekostenWijzigen withGebruikOmslagdatum(JaNee jaNee) {
        selectRadio(fieldGebruikOmslagdatum, jaNee);
        return this;
    }

    public DeelnamekostenWijzigen withAnderBedragPerSoortLid(JaNee jaNee) {
        selectRadio(fieldAnderBedragPerSoortLid, jaNee);
        return this;
    }

    public DeelnamekostenWijzigen withMinimumBedrag(String s) {
        clearAndSendKeys(fieldMinimumBedrag, s);
        return this;
    }

}
