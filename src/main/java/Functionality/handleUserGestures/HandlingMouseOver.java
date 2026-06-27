package Functionality.handleUserGestures;

import com.microsoft.playwright.*;

import java.util.Collections;

public class HandlingMouseOver {

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
            page.navigate("https://www.way2automation.com/");

            page.locator("a.menu-link").locator("span").nth(4).hover();

            Thread.sleep(5000);

            page.locator("//li[@id='menu-item-27592']//a[@class='menu-link']").click();
            Thread.sleep(5000);



        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (page != null && !page.isClosed()) page.close();
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
        }
    }
}
