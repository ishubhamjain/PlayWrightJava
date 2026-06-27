package Functionality.frameAlertsPopsUp;

import com.microsoft.playwright.*;

import java.util.Collections;

public class HandlingTabWindowsNPopUps {

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
            page.navigate("https://www.way2automation.com/way2auto_jquery/frames-and-windows.php#load_box");

            page.locator("a[href='#example-1-tab-2']").click();
            System.out.println(page.title());

            Page newWindow = page.waitForPopup(()->{
                page.frameLocator("[src*='defult2.html']").locator("a:visible").click();
            });

            System.out.println(newWindow.locator("a:visible").innerText());
            System.out.println("new window title = "+newWindow.title());
            newWindow.close();
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
