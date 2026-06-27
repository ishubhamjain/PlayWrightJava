package Functionality.ScreenshotVideoTraceViewer;

import com.microsoft.playwright.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class ScreenShot {
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
                    new Browser.NewContextOptions()
                            .setViewportSize(null) // null for maximized window
            );
            page = context.newPage();
            page.navigate("http://the-internet.herokuapp.com/");
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./src/test/java/resources/files/screenshot.jpg")));
            page.locator("//h1[normalize-space()='Welcome to the-internet']").screenshot(new Locator.ScreenshotOptions().setPath(Paths.get("./src/test/java/resources/files/screenshot_element.jpg")));
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
