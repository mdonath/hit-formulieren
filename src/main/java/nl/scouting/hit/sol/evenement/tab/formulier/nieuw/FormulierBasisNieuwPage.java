package nl.scouting.hit.sol.evenement.tab.formulier.nieuw;

import nl.scouting.hit.sol.evenement.tab.formulier.common.AbstractFormulierBasisPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FormulierBasisNieuwPage extends AbstractFormulierBasisPage<FormulierBasisNieuwPage> {

    public enum SoortDeelnemers {
        PERSONEN("per"),
        ORGANISATIE_ONDERDELEN("org");

        public final String id;

        SoortDeelnemers(final String id) {
            this.id = id;
        }
    }

    @FindBy(name = "frm_participant_type")
    private WebElement fieldSoortDeelnemers;

    /**
     * Button Volgende.
     */
    @FindBy(xpath = "//*[@id=\"buttonbar\"]/div[1]/input")
    private WebElement buttonVolgende;

    public FormulierBasisNieuwPage(WebDriver driver) {
        super(driver);
    }


    public FormulierBasisNieuwPage setFieldSoortDeelnemers(final SoortDeelnemers s) {
        selectByValue(fieldSoortDeelnemers, s.id);
        return this;
    }

    public FormulierOverzichtNieuwPage volgende() {
        scrollIntoViewAndClick(buttonVolgende);
        return new FormulierOverzichtNieuwPage(driver);
    }
}
