package nl.scouting.hit.hitsite;

import nl.scouting.hit.common.AbstractWebApplication;
import nl.scouting.hit.joomla.JoomlaAdminHomePage;
import nl.scouting.hit.joomla.JoomlaAdminLoginPage;
import nl.scouting.hit.kampinfo.KampInfoInfoPage;
import nl.scouting.hit.sol.LoginScoutingPage;

public class HitWebsiteAdmin extends AbstractWebApplication {

    private final JoomlaAdminHomePage adminHomePage;

    public HitWebsiteAdmin(final String kiBaseUrl, final String solUsername, final String solPassword) {
        super();
        this.adminHomePage = getJoomlaAdminPage(kiBaseUrl, solUsername, solPassword);
        this.adminHomePage.registerComponent("HIT KampInfo", KampInfoInfoPage.class);
    }

    private JoomlaAdminHomePage getJoomlaAdminPage(final String baseUrl, final String username, final String password) {
        return new JoomlaAdminLoginPage(driver, baseUrl)
                .withUsername(username)
                .loginSOL(() ->
                        new LoginScoutingPage(driver)
                                .withPassword(password)
                                .login(new JoomlaAdminHomePage(driver)));
    }

    public KampInfoInfoPage openKampInfo() {
        return this.adminHomePage.openComponent("HIT KampInfo");
    }
}
