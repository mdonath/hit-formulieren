package nl.scouting.hit.sol.evenement.tab.formulier.wijzig.samenstellen;

import nl.scouting.hit.sol.JaNee;
import nl.scouting.hit.sol.evenement.tab.formulier.wijzig.FormulierWijzigSamenstellenPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckboxWijzigen extends AbstractVeldWijzigen<CheckboxWijzigen> {

    @FindBy(name = "new_option_txt")
    private WebElement fieldNieuweOptie;

    @FindBy(name = "add_option")
    private WebElement buttonToevoegen;
    @FindBy(xpath = "//*[@id=\"buttonbar\"]/div[1]/input")
    private WebElement buttonWijzigingenOpslaan;


    public CheckboxWijzigen(WebDriver driver) {
        super(driver);
    }

    public CheckboxWijzigen setFieldNieuweOptie(String s) {
        clearAndSendKeys(fieldNieuweOptie, s);
        return this;
    }

    public CheckboxWijzigen toevoegen() {
        scrollIntoViewAndClick(buttonToevoegen);
        return this;
    }

    public CheckboxWijzigen setFieldVolgorde(String naamOptie, int volgorde) {
        return setFieldOptionValue(naamOptie, String.valueOf(volgorde), "opt_order_no");
    }

    public CheckboxWijzigen setFieldNaam(String naamOptie, String naam) {
        return setFieldOptionValue(naamOptie, naam, "opt_order_no");
    }

    public CheckboxWijzigen setFieldActief(String naamOptie, JaNee jaNee) {
        return setFieldOptionValue(naamOptie, jaNee, "opt_active_yn");
    }

    public CheckboxWijzigen setFieldOpenVraag(String naamOptie, JaNee jaNee) {
        return setFieldOptionValue(naamOptie, jaNee, "opt_reaction_yn");
    }

    public CheckboxWijzigen setFieldMaximum(String naamOptie, int maximum) {
        return setFieldOptionValue(naamOptie, String.valueOf(maximum), "opt_max_ct");
    }

    private CheckboxWijzigen setFieldOptionValue(final String naamOptie, final String value, final String fieldName) {
        final By by = By.xpath("//table//input[@value='" + naamOptie + "']/../following-sibling::td/input[contains(@name, '" + fieldName + "')]");
        clearAndSendKeys(driver.findElement(by), value);
        return this;
    }

    private CheckboxWijzigen setFieldOptionValue(final String naamOptie, final JaNee value, final String fieldName) {
        final By by = By.xpath("//table//input[@value='" + naamOptie + "']/../following-sibling::td/input[contains(@name, '" + fieldName + "')]");
        final WebElement checkboxField = driver.findElement(by);
        if (value.asBoolean() != checkboxField.isSelected()) {
            checkboxField.click();
        }
        return this;
    }

    public FormulierWijzigSamenstellenPage wijzigingenOpslaan() {
        scrollIntoViewAndClick(buttonWijzigingenOpslaan);
        return new FormulierWijzigSamenstellenPage(driver);
    }

}
