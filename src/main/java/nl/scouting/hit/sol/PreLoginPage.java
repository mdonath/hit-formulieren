package nl.scouting.hit.sol;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PreLoginPage extends AbstractSolPage {
    @FindBy(css = "#buttonbar > div:nth-child(1) > input")
    private WebElement btnLogin;

    public PreLoginPage(WebDriver driver, String baseUrl) {
        super(driver);
        driver.get(baseUrl);
    }

    public LoginPage login() {
        btnLogin.click();
        return new LoginPage(driver);
    }
}
