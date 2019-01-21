package nl.scouting.hit.sol;

import nl.scouting.hit.common.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * De OpenID inlogpagina van https://login.scouting.nl.
 */
public class LoginScoutingPage extends AbstractPage {

    @FindBy(name="username")
    private WebElement fieldUsername;
    @FindBy(name="password")
    private WebElement fieldPassword;
    @FindBy(css = "#login_form input[type=\"submit\"]")
    private WebElement submitButton;

    public LoginScoutingPage(WebDriver driver) {
        super(driver);
    }

    public SolHomePage login(String username, String password) {
        return withUsername(username)
                .withPassword(password)
                .login(new SolHomePage(driver));
    }

    public LoginScoutingPage withUsername(String username) {
        fieldUsername.sendKeys(username);
        return this;
    }

    public LoginScoutingPage withPassword(String password) {
        fieldPassword.sendKeys(password);
        return this;
    }

    public <T extends AbstractPage> T login(T returnPage) {
        scrollIntoViewAndClick(submitButton);
        return returnPage;
    }
}
