package Functionality.experiment;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import java.util.Collections;

public class Random {
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
          //  page.goto("dd");
            page.locator("xyz").pressSequentially("fff", new Locator.PressSequentiallyOptions().setDelay(1000));

            page.locator("xyz").pressSequentially("ff");
            page.locator(".card")
                    .filter(new Locator.FilterOptions().setHasText("some text"));



            page.getByRole(AriaRole.LINK,
                            new Page.GetByRoleOptions().setName("Shop"))
                    .click();

            page.getByRole(AriaRole.BUTTON,
                            new Page.GetByRoleOptions().setName("Shop"))
                    .click();

            boolean visible = page.getByText("required text").isVisible();


        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}