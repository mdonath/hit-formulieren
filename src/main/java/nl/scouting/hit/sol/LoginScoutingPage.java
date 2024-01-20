package nl.scouting.hit.sol;

import nl.scouting.hit.common.AbstractPage;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * De OpenID inlogpagina van https://login.scouting.nl.
 */
public class LoginScoutingPage extends AbstractPage<LoginScoutingPage> {

    public static final String DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    @FindBy(name = "username")
    private WebElement fieldUsername;
    @FindBy(xpath = "//input[@name = 'password']")
    private WebElement fieldPassword;
    @FindBy(css = "button[type=\"submit\"]")
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
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(fieldPassword));

        fieldPassword.sendKeys(password);
        return this;
    }

    public <T extends AbstractPage<?>> T login(final T returnPage) {
        scrollIntoViewAndClick(submitButton);
        return returnPage;
    }

    private void loadCookies() {
        final Path filePath = Paths.get("cookies.dat").toAbsolutePath();
        try {
            final String cookies = Files.readString(filePath);
            Stream.of(cookies.split("\n"))
                    .forEach(cookieString -> {
                                final StringTokenizer token = new StringTokenizer(cookieString, ";");
                                while (token.hasMoreTokens()) {
                                    Cookie cookie = new Cookie.Builder(token.nextToken(), token.nextToken())
                                            .domain(token.nextToken())
                                            .path(token.nextToken())
                                            .expiresOn(getExpiry(token.nextToken()))
                                            .isSecure(Boolean.parseBoolean(token.nextToken()))
                                            .build();
                                    if (!cookie.getDomain().equals("sol.scouting.nl")) {
                                        driver.manage().addCookie(cookie);
                                    }
                                }
                            }
                    );
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private Date getExpiry(final String val) {
        Date expiry = null;
        if (!val.equals("null")) {
            try {
                expiry = new SimpleDateFormat(DATETIME_PATTERN).parse(val);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return expiry;
    }

    private void dumpCookies() {
        final Path path = Paths.get("cookies.dat").toAbsolutePath();
        try {
            System.out.println(path);
            Files.writeString(path,
                    driver.manage().getCookies().stream()
                            .map(ck -> String.join(";", Arrays.asList(
                                    ck.getName(),
                                    ck.getValue(),
                                    ck.getDomain(),
                                    ck.getPath(),
                                    ck.getExpiry() == null ? "null" : new SimpleDateFormat(DATETIME_PATTERN).format(ck.getExpiry()),
                                    ck.isSecure() ? "true" : "false"))
                            ).collect(Collectors.joining("\n")));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
