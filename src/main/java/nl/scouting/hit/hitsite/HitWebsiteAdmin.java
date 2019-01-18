package nl.scouting.hit.hitsite;

import nl.scouting.hit.common.AbstractWebApplication;
import nl.scouting.hit.joomla.JoomlaAdminHomePage;
import nl.scouting.hit.joomla.JoomlaAdminLoginPage;
import nl.scouting.hit.kampinfo.KampInfoInfoPage;

public class HitWebsiteAdmin extends AbstractWebApplication {

    private final JoomlaAdminHomePage adminHomePage;

    public HitWebsiteAdmin(final String kiBaseUrl, final String solUsername, final String solPassword) {
        super();
        this.adminHomePage = getJoomlaAdminPage(kiBaseUrl, solUsername, solPassword);
        this.adminHomePage.registerComponent("HIT KampInfo", KampInfoInfoPage.class);
    }

    private JoomlaAdminHomePage getJoomlaAdminPage(final String baseUrl, final String username, final String password) {
        return new JoomlaAdminLoginPage(driver, baseUrl)
                .login(username, password);
    }

    public KampInfoInfoPage openKampInfo() {
        return this.adminHomePage.openComponent("HIT KampInfo");
    }
}
