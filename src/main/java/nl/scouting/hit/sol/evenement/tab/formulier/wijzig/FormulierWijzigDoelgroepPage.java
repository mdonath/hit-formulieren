package nl.scouting.hit.sol.evenement.tab.formulier.wijzig;

import nl.scouting.hit.sol.evenement.tab.formulier.common.AbstractFormulierPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FormulierWijzigDoelgroepPage extends AbstractFormulierPage<FormulierWijzigDoelgroepPage> {

// ALGEMEEN

    // ttar_title
    // ttar_txt
    // ttar_release_dt_day
    // ttar_release_dt_month
    // ttar_release_dt_year
    // ttar_expire_dt_day
    // ttar_expire_dt_month
    // ttar_expire_dt_year
    // ttar_public_yn


    //
    // Button
    //
    @FindBy(xpath = "//input[@value=\"Toevoegen\"]")
    private WebElement buttonWijzigingenOpslaan;

    /**
     * Constructor.
     *
     * @param driver
     */
    public FormulierWijzigDoelgroepPage(WebDriver driver) {
        super(driver);
    }

    public FormulierWijzigDoelgroepPage wijzigingenOpslaan() {
        scrollIntoViewAndClick(buttonWijzigingenOpslaan);
        return this;
    }

}
