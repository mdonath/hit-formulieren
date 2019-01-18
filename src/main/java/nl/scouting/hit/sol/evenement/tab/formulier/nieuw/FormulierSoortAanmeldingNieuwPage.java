package nl.scouting.hit.sol.evenement.tab.formulier.nieuw;

import nl.scouting.hit.sol.AbstractSolMetHoofdMenuPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class FormulierSoortAanmeldingNieuwPage extends AbstractSolMetHoofdMenuPage<FormulierSoortAanmeldingNieuwPage> {

    @FindBy(xpath = "//*[@id=\"buttonbar\"]/div[1]/input")
    private WebElement buttonVolgende;

    public enum SoortAanmelding {
        INDIVIDUELE_INSCHRIJVING(1),
        GROEPSINSCHRIJVING_ZONDER_INDIVIDUEEL(2),
        GROEPSINSCHRIJVING_MET_INDIVIDUEEL(4);

        public final int id;

        SoortAanmelding(int id) {
            this.id = id;
        }

    }

    @FindBy(id = "fty_id")
    private WebElement fieldSoortAanmelding;

    public FormulierSoortAanmeldingNieuwPage(WebDriver driver) {
        super(driver);
    }

    public FormulierSoortAanmeldingNieuwPage setFieldSoortAanmelding(String s) {
        new Select(fieldSoortAanmelding).selectByVisibleText(s);
        return this;
    }

    public FormulierSoortAanmeldingNieuwPage setFieldSoortAanmelding(SoortAanmelding soortAanmelding) {
        new Select(fieldSoortAanmelding).selectByValue(String.valueOf(soortAanmelding.id));
        return this;
    }

    public FormulierBasisNieuwPage volgende() {
        scrollIntoViewAndClick(buttonVolgende);
        return new FormulierBasisNieuwPage(driver);
    }
}
