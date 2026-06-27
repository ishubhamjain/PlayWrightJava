package Launches;

import com.microsoft.playwright.*;

import java.nio.file.Paths;
import java.util.Collections;
/**
 * The LocatorClass1 class demonstrates a basic usage of Playwright for browser automation.
 * It launches a Chromium browser instance, navigates to a specified URL, and prints the page title.
 *
 * @author [Shubham Jain]
 */
public class LocatorClass1 {

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
                            .setHeadless(false)
                            .setArgs(Collections.singletonList("--start-maximized"))
                            .setExecutablePath(Paths.get(chromeExecutablePath))
            );

            BrowserContext context = browser.newContext(
                    new Browser.NewContextOptions().setViewportSize(null) // null for maximized window
            );
            page = context.newPage();
            page.navigate("https://seleniumbase.io/demo_page");
            Thread.sleep(3000);
            System.out.println(page.title());

            //locator with cssSelector
            //locator with cssSelector
         //   page.locator("#myTextInput").fill("test");
         //   page.fill("#myTextInput", "test");

            //locator with id
            page.fill("id=myTextInput", "test");
            System.out.println(page.locator("id=myButton").getAttribute("style"));
            page.click("id=myButton");
            System.out.println("after click");
          //  System.out.println(page.locator("id=myButton").getAttribute("style"));

            System.out.println(page.locator("id=myLink2").textContent());
            System.out.println("get text above");
            page.locator("//input[@id='radioButton2']").click();
            page.locator("xpath=//input[@id='checkBox2']").click();
            
          //  page.fill("#myTextInput", "test", new Page.FillOptions().s .setTimeout(3000));
            Thread.sleep(3000);
        } catch (PlaywrightException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (page != null && !page.isClosed()) page.close();
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
        }
    }
}
