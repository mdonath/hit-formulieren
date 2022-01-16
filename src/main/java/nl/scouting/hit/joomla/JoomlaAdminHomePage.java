package nl.scouting.hit.joomla;

import nl.scouting.hit.common.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class JoomlaAdminHomePage extends AbstractPage<JoomlaAdminHomePage> {

    @FindBy(linkText = "Componenten")
    private WebElement menuComponenten;

    private Map<String, Class<? extends AbstractPage>> register;

    public JoomlaAdminHomePage(final WebDriver driver) {
        super(driver);
    }

    public <T extends AbstractPage<?>> T openComponent(final String componentName) {
        menuComponenten.click();
        driver.findElement(By.linkText(componentName)).click();
        return (T) instantiateComponent(componentName);
    }

    private AbstractPage<?> instantiateComponent(final String componentName) {
        try {
            return register.get(componentName).getConstructor(WebDriver.class).newInstance(driver);
        } catch (final InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Kan component '" + componentName + "' niet vinden");
        }
    }

    public void registerComponent(final String componentName, final Class<? extends AbstractPage> componentPageClass) {
        if (register == null) {
            register = new HashMap<>();
        }
        register.put(componentName, componentPageClass);
    }

}
