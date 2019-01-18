package nl.scouting.hit.joomla;

import nl.scouting.hit.common.AbstractPage;
import nl.scouting.hit.sol.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class JoomlaAdminLoginPage extends AbstractPage<JoomlaAdminLoginPage> {

    @FindBy(name = "username")
    private WebElement usernameField;
    @FindBy(name = "passwd")
    private WebElement passwordField;
    @FindBy(className = "login-button")
    private WebElement submitButton;

    public JoomlaAdminLoginPage(WebDriver driver, String baseUrl) {
        super(driver);
        driver.get(baseUrl + "/administrator");
    }

    public JoomlaAdminHomePage login(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(""); // leeg zodat via login.scouting.nl moet worden ingelogd
        scrollIntoViewAndClick(submitButton);
        return new LoginPage(driver).login(username, password, new JoomlaAdminHomePage(driver));
    }
}
