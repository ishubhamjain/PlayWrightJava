package Functionality;

import com.microsoft.playwright.*;

import java.util.Collections;

public class TestHandlingLinks {
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
            page.navigate("https://www.wikipedia.org/");
            Locator links = page.locator("a");

            System.out.println(links.count());

            for(int i=0; i<links.count(); i++) {

                System.out.println(links.nth(i).innerText()+"---URL: ----"+links.nth(i).getAttribute("href"));

            }

            Locator block = page.locator("//*[@id=\"www-wikipedia-org\"]/div[7]/div[3]");

            Locator blocklinks = block.locator("a");

            System.out.println("----Printing links from the block------");

            System.out.println(blocklinks.count());

            for(int i=0; i<blocklinks.count(); i++) {

                System.out.println(blocklinks.nth(i).innerText()+"---URL: ----"+blocklinks.nth(i).getAttribute("href"));

            }
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
//            if (page != null && !page.isClosed()) page.close();
//            if (browser != null) browser.close();
//            if (playwright != null) playwright.close();
        }
    }
}
