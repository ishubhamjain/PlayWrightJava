package Functionality.JavaScriptKeyboardActions;

import com.microsoft.playwright.*;

import java.util.Collections;

public class HandlingKeyboardEvents {
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
            page.navigate("https://login.yahoo.com/");

            page.locator("#login-username").fill("xyzsdaddw@yahoo.com");
            page.keyboard().press("Enter");
            Thread.sleep(2000);
            page.keyboard().press("Meta+A");
            Thread.sleep(2000);
            page.keyboard().press("Meta+X");
            Thread.sleep(2000);
            page.keyboard().press("Meta+V");
            Thread.sleep(1000);
            page.keyboard().down("Shift");

            for(int i=0; i<3; i++) {

                page.keyboard().press("ArrowLeft");
                Thread.sleep(500);

            }


            Thread.sleep(5000);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (page != null && !page.isClosed()) page.close();
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
        }
    }
}
