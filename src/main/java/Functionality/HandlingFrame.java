package Functionality;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitUntilState;

import java.util.Collections;

public class HandlingFrame {

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
            page.navigate("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml5_input_type_submit");

            Locator locator = page.locator("iframe");
            int iframeCount = locator.count();
            System.out.println(iframeCount);

            for (int i = 0; i < iframeCount; i++) {
                System.out.println(locator.nth(i).getAttribute("class"));
            }

            page.frameLocator("//iframe[@id='iframeResult']").locator("//input[@type='submit']").click(new Locator.ClickOptions().setTimeout(2000));
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (page != null && !page.isClosed()) page.close();
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
        }
    }
}
