package nl.scouting.hit.sol.evenement.tab.formulier.wijzig.samenstellen;

import nl.scouting.hit.sol.JaNee;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.FormulierWijzigSamenstellenPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RadioButtonWijzigen extends AbstractVeldWijzigen<RadioButtonWijzigen> {

    @FindBy(name = "new_option_txt")
    private WebElement fieldNieuweOptie;

    @FindBy(name = "add_option")
    private WebElement buttonToevoegen;
    @FindBy(xpath = "//*[@id=\"buttonbar\"]/div[1]/input")
    private WebElement buttonWijzigingenOpslaan;


    public RadioButtonWijzigen(WebDriver driver) {
        super(driver);
    }

    public RadioButtonWijzigen withNieuweOptie(String s) {
        clearAndSendKeys(fieldNieuweOptie, s);
        return this;
    }

    public RadioButtonWijzigen toevoegen() {
        scrollIntoViewAndClick(buttonToevoegen);
        return this;
    }

    public RadioButtonWijzigen withVolgorde(String naamOptie, int volgorde) {
        return withOptionValue(naamOptie, String.valueOf(volgorde), "opt_order_no");
    }

    public RadioButtonWijzigen withNaam(String naamOptie, String naam) {
        return withOptionValue(naamOptie, naam, "opt_order_no");
    }

    public RadioButtonWijzigen withActief(String naamOptie, JaNee jaNee) {
        return withOptionValue(naamOptie, jaNee, "opt_active_yn");
    }

    public RadioButtonWijzigen withOpenVraag(String naamOptie, JaNee jaNee) {
        return withOptionValue(naamOptie, jaNee, "opt_reaction_yn");
    }

    public RadioButtonWijzigen withMaximum(String naamOptie, int maximum) {
        return withOptionValue(naamOptie, String.valueOf(maximum), "opt_max_ct");
    }

    private RadioButtonWijzigen withOptionValue(final String naamOptie, final String value, final String fieldName) {
        final By by = By.xpath("//table//input[@value='" + naamOptie + "']/../following-sibling::td//input[contains(@name, '" + fieldName + "')]");
        clearAndSendKeys(driver.findElement(by), value);
        return this;
    }

    /**
     * //table//input[@value='Dieet gebaseerd op godsdienst, namelijk']/../following-sibling::td/input[@type='checkbox' and contains(@name, 'opt_reaction_yn')]
     */
    private RadioButtonWijzigen withOptionValue(final String naamOptie, final JaNee value, final String fieldName) {
        final By by = By.xpath("//table//input[@value='" + naamOptie + "']/../following-sibling::td//input[@type='checkbox' and contains(@name, '" + fieldName + "')]");
        final WebElement checkboxField = driver.findElement(by);
        if (value.asBoolean() != checkboxField.isSelected()) {
            scrollIntoView(checkboxField, false);
            checkboxField.sendKeys(Keys.SPACE); // click();
        }
        return this;
    }

    public FormulierWijzigSamenstellenPage wijzigingenOpslaan() {
        scrollIntoViewAndClick(buttonWijzigingenOpslaan);
        return new FormulierWijzigSamenstellenPage(driver);
    }

}
