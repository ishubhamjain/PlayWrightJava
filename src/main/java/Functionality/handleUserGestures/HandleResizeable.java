package Functionality.handleUserGestures;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.BoundingBox;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.Collections;

public class HandleResizeable {
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
            page.navigate("https://jqueryui.com/resizable/");

            Thread.sleep(3000);
            Locator slider = page.frameLocator("//iframe").locator("//div[contains(@class, 'ui-resizable-handle ui-resizable-e')]");

            // Wait for the slider to be visible and ready
            slider.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

            // Get slider position and size
            BoundingBox box = slider.boundingBox();

            // Move and drag the slider if found
            if (box != null) {
                page.mouse().move(box.x + box.width / 2, box.y + box.height / 2);
                page.mouse().down();
                page.mouse().move(box.x + box.width / 2 + 100, box.y + 400); // move right by 100px as example
                page.mouse().up();
            }
            Thread.sleep(2000);





        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (page != null && !page.isClosed()) page.close();
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
        }
    }
}
