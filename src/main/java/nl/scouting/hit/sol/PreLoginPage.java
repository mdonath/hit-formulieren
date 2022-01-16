package nl.scouting.hit.sol;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PreLoginPage extends AbstractSolPage<PreLoginPage> {

    @FindBy(css = "#buttonbar > div:nth-child(1) > input")
    private WebElement btnLogin;

    public PreLoginPage(final WebDriver driver, final String baseUrl) {
        super(driver);
        driver.get(baseUrl);
    }

    public LoginScoutingPage login() {
        btnLogin.click();
        return new LoginScoutingPage(driver);
    }

}
