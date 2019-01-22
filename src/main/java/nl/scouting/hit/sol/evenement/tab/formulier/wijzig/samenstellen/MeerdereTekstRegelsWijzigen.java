package nl.scouting.hit.sol.evenement.tab.formulier.wijzig.samenstellen;

import nl.scouting.hit.common.Valuable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MeerdereTekstRegelsWijzigen extends AbstractVeldWijzigen<MeerdereTekstRegelsWijzigen> {

    public enum Breedte implements Valuable {
        SMAL("small"),
        NORMAAL("normal"),
        BREED("big");

        private final String id;

        Breedte(String id) {
            this.id = id;
        }

        @Override
        public String getId() {
            return id;
        }
    }

    @FindBy(name = "fld_num_rows")
    private WebElement fieldAantalRegels;

    @FindBy(name = "fld_num_cols")
    private List<WebElement> fieldBreedte;

    @FindBy(name = "fld_std_value")
    private WebElement fieldStandaardwaarde;

    @FindBy(name = "fld_length")
    private WebElement fieldMaximumLengte;

    public MeerdereTekstRegelsWijzigen(final WebDriver driver) {
        super(driver);
    }

    public MeerdereTekstRegelsWijzigen withAantalRegels(int aantal) {
        clearAndSendKeys(fieldAantalRegels, aantal);
        return this;
    }

    public MeerdereTekstRegelsWijzigen withBreedte(Breedte breedte) {
        selectRadio(fieldBreedte, breedte);
        return this;
    }

    public MeerdereTekstRegelsWijzigen withStandaardwaarde(String standaard) {
        clearAndSendKeys(fieldStandaardwaarde, standaard);
        return this;
    }

    public MeerdereTekstRegelsWijzigen withMaximumLengte(int lengte) {
        clearAndSendKeys(fieldMaximumLengte, lengte);
        return this;
    }
}
