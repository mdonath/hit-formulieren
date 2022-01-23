package nl.scouting.hit.sol;

import nl.scouting.hit.common.AbstractWebApplication;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Opent ScoutsOnline en logt in.
 */
public class ScoutsOnline extends AbstractWebApplication {

    public final SolHomePage solHomePage;

    /**
     * Constructor.
     *
     * @param baseUrl  de url waar Scouts Online te vinden is, kan ook een testomgeving zijn
     * @param username de gebruikersnaam van het account dat moet inloggen en de juiste rechten heeft om evenementen aan te maken
     * @param password het wachtwoord van het account dat moet inloggen
     */
    public ScoutsOnline(final String baseUrl, final String username, final String password) {
        super();
        this.solHomePage = getHomePage(baseUrl, username, password);
    }

    private SolHomePage getHomePage(final String baseUrl, final String username, final String password) {
        return new PreLoginPage(driver, baseUrl)
                .login()
                .login(username, password);
    }

    @Override
    public void close() throws IOException {
        Files.copy(
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).toPath()
                , new File("screenshot.png").toPath()
                , StandardCopyOption.REPLACE_EXISTING);
        driver.quit();
    }
}