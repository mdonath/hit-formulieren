package nl.scouting.hit.joomla;

import nl.scouting.hit.common.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.function.Supplier;

public class JoomlaAdminLoginPage extends AbstractPage<JoomlaAdminLoginPage> {

    @FindBy(name = "username")
    private WebElement fieldUsername;
    @FindBy(name = "passwd")
    private WebElement fieldPassword;
    @FindBy(className = "login-button")
    private WebElement submitButton;

    public JoomlaAdminLoginPage(WebDriver driver, String baseUrl) {
        super(driver);
        driver.get(baseUrl + "/administrator");
    }

    public JoomlaAdminLoginPage withUsername(String username) {
        fieldUsername.sendKeys(username);
        return this;
    }

    public JoomlaAdminLoginPage withPassword(String password) {
        fieldPassword.sendKeys(password);
        return this;
    }

    public JoomlaAdminHomePage login(String username, String password) {
        return withUsername(username)
                .withPassword(password)
                .login();
    }

    public JoomlaAdminHomePage login() {
        scrollIntoViewAndClick(submitButton);
        return new JoomlaAdminHomePage(driver);
    }

    public JoomlaAdminHomePage loginSOL(Supplier<JoomlaAdminHomePage> supplier) {
        scrollIntoViewAndClick(submitButton);
        return supplier.get();
    }

}
