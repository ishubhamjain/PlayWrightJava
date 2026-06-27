package Functionality.experiment;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import java.util.Collections;

public class TestSpecializedLocators {

    static Playwright playwright;
    static Browser browser;
    static Page page;

    public static void main(String[] args) throws InterruptedException {
        try {
            playwright = Playwright.create();
            browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(false)
                            .setArgs(Collections.singletonList("--start-maximized"))
            );

            BrowserContext context = browser.newContext(
                    new Browser.NewContextOptions().setViewportSize(null) // null for maximized window
            );
            page = context.newPage();
            page.navigate("https://www.way2automation.com/way2auto_jquery/accordion.php#load_box");

            page.getByText("Customize icons").click();
          //  page.locator("a").filter(new Locator.FilterOptions().setHasText("Home")).click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("trainer@way2automation.com")).click();
          //  page.getByRole(AriaRole., new Page.GetByRoleOptions().setName("trainer@way2automation.com")).click();
            page.waitForTimeout(5000);


        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (page != null && !page.isClosed()) page.close();
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
        }
    }
}
