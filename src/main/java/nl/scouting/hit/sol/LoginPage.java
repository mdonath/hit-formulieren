package nl.scouting.hit.sol;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractSolPage {

    private WebElement usernameField;
    private WebElement passwordField;
    @FindBy(css = "#login_form input[type=\"submit\"]")
    private WebElement submitButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public SolHomePage login(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        scrollIntoViewAndClick(submitButton);
        return new SolHomePage(driver);
    }
}
