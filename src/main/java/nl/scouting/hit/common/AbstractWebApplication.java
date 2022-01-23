package nl.scouting.hit.common;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Maakt een WebDriver en sluit deze.
 */
public abstract class AbstractWebApplication implements AutoCloseable {

    protected final WebDriver driver;

    /**
     * Constructor.
     */
    protected AbstractWebApplication() {
        this.driver = Util.getWebDriver();
    }

    @Override
    public void close() throws Exception {
        Files.copy(
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).toPath()
                , new File("screenshot.png").toPath()
                , StandardCopyOption.REPLACE_EXISTING);
        driver.quit();
    }
}