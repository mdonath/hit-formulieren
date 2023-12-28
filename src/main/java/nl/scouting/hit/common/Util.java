package nl.scouting.hit.common;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;

public final class Util {

    private static final Properties configuration = new Properties();

    static {
        try (final FileInputStream inStream = new FileInputStream("configuration.properties")) {
            configuration.load(inStream);
            decodeConfiguredPassword();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private Util() {
        // Private Utility Constructor
    }

    private static void decodeConfiguredPassword() {
        final String encodedPassword = configuration.getProperty("password");
        final String decodedPassword = new String(Base64.getDecoder().decode(encodedPassword), StandardCharsets.UTF_8);
        configuration.put("password", decodedPassword);
    }

    public static WebDriver getWebDriver() {
        final ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");

        final WebDriver driver = new ChromeDriver(options);

        driver.manage().window().setSize(new Dimension(1200, 900));
        return driver;
    }

    public static String readUsernameFromFile() {
        return configuration.getProperty("username");
    }

    public static String readPasswordFromFile() {
        return configuration.getProperty("password");
    }

}
