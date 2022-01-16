package nl.scouting.hit.sol;

import nl.scouting.hit.common.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * De OpenID inlogpagina van https://login.scouting.nl.
 */
public class LoginScoutingPage extends AbstractPage<LoginScoutingPage> {

    @FindBy(name = "username")
    private WebElement fieldUsername;
    @FindBy(xpath = "//input[@name = 'password']")
    private WebElement fieldPassword;
    @FindBy(css = "#login_form input[type=\"submit\"]")
    private WebElement submitButton;

    public LoginScoutingPage(final WebDriver driver) {
        super(driver);
    }

    public SolHomePage login(final String username, final String password) {
        return withUsername(username)
                .withPassword(password)
                .login(new SolHomePage(driver));
    }

    public LoginScoutingPage withUsername(final String username) {
        fieldUsername.sendKeys(username);
        return this;
    }

    public LoginScoutingPage withPassword(final String password) {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOf(fieldPassword));

        fieldPassword.sendKeys(password);
        return this;
    }

    public <T extends AbstractPage<?>> T login(final T returnPage) {
        scrollIntoViewAndClick(submitButton);
        return returnPage;
    }

}
