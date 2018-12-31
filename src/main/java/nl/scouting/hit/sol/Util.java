package nl.scouting.hit.sol;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class Util {

    public static WebDriver getWebDriver() {
        final WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1200, 900));
        return driver;
    }

    public static String readPasswordFromFile() throws IOException {
         return new String(
                 Base64.getDecoder().decode(Files.readAllBytes(Paths.get("mypassword.txt"))),
                 "UTF-8");
     }

     public static void delay(long millis) {
         try {
             Thread.sleep(millis);
         } catch (InterruptedException ignore) {
             // NOP
         }
     }
 }
