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

    public JoomlaAdminLoginPage(final WebDriver driver, final String baseUrl) {
        super(driver);
        driver.get(baseUrl + "/administrator");
    }

    public JoomlaAdminLoginPage withUsername(final String username) {
        fieldUsername.sendKeys(username);
        return this;
    }

    public JoomlaAdminLoginPage withPassword(final String password) {
        fieldPassword.sendKeys(password);
        return this;
    }

    public JoomlaAdminHomePage login(final String username, final String password) {
        return withUsername(username)
                .withPassword(password)
                .login();
    }

    public JoomlaAdminHomePage login() {
        scrollIntoViewAndClick(submitButton);
        return new JoomlaAdminHomePage(driver);
    }

    public JoomlaAdminHomePage loginSOL(final Supplier<JoomlaAdminHomePage> supplier) {
        scrollIntoViewAndClick(submitButton);
        return supplier.get();
    }

}
