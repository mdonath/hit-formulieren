package nl.scouting.hit.common;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

public class Util {

    private static final Properties configuration = new Properties();

    static {
        try {
            configuration.load(new FileInputStream(new File("configuration.properties")));
            String encodedPassword = configuration.getProperty("password");
            String decodedPassword = new String(Base64.getDecoder().decode(encodedPassword), "UTF-8");
            configuration.put("password", decodedPassword);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static WebDriver getWebDriver() {
        final WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1200, 900));
        return driver;
    }

    public static String readUsernameFromFile() throws IOException {
        return configuration.getProperty("username");
    }

    public static String readPasswordFromFile() throws IOException {
        return configuration.getProperty("password");
    }

}
