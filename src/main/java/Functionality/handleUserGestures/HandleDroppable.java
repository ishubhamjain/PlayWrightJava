package Functionality.handleUserGestures;

import com.microsoft.playwright.*;

import java.util.Collections;

public class HandleDroppable {
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
            page.navigate("https://jqueryui.com/droppable/");

            Locator draggable =  page.frameLocator("//iframe").locator("#draggable");
            Locator droppable =  page.frameLocator("//iframe").locator("#droppable");

//            draggable.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
//
//            // Get slider position and size
//            BoundingBox box = draggable.boundingBox();

            // Move and drag the slider if found
            if (draggable != null) {
                page.mouse().move(draggable.boundingBox().x + draggable.boundingBox().width / 2, draggable.boundingBox().y + draggable.boundingBox().height / 2);
                page.mouse().down();
                page.mouse().move(droppable.boundingBox().x + droppable.boundingBox().width / 2, droppable.boundingBox().y + droppable.boundingBox().height / 2);
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
