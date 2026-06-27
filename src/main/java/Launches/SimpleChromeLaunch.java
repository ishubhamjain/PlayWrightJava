package Launches;

import com.microsoft.playwright.*;

import java.util.Collections;

public class SimpleChromeLaunch {

    static Playwright playwright;
    static Browser browser;
    static Page page;

    public static void main(String[] args) {
        try {
            playwright = Playwright.create();
            // Set the path to your Chrome executable below
            String chromeExecutablePath = "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome"; // <-- Update this path

            browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setChannel("firefox")
                            .setHeadless(false)
                            .setArgs(Collections.singletonList("--start-maximized"))
                         //   .setExecutablePath(Paths.get(chromeExecutablePath))
            );

            BrowserContext context = browser.newContext(
                    new Browser.NewContextOptions().setViewportSize(null) // null for maximized window
            );
            page = context.newPage();
            page.navigate("https://www.way2automation.com/");
            Thread.sleep(3000);
            System.out.println(page.title());
        } catch (PlaywrightException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (page != null && !page.isClosed()) page.close();
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
        }
    }
}
