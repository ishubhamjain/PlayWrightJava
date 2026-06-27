package Functionality.assertions;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.Collections;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class HandlingWebTables {
    static Playwright playwright;
    static Browser browser;
    static Page page;

    //source: https://playwright.dev/docs/test-assertions
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
            page.navigate("https://money.rediff.com/indices/nse/NIFTY-50?src=moneyhome_nseIndices");

            //Row count

            System.out.println(page.locator(".dataTable > tbody").locator("tr").count());
            System.out.println(page.locator(".dataTable > tbody").locator("tr:first-child").locator("td").count());
            assertThat(page.locator(".dataTable > tbody").locator("tr:first-child").locator("td:nth-child(2)")).hasText("Nifty");

            page.locator(".dataTable > tbody").allInnerTexts().forEach(text -> System.out.println(text));

            page.locator(".dataTable > tbody").allTextContents().forEach(text -> System.out.println(text));


//            page.locator(".dataTable > tbody")
//            assertThat(page.locator(".dataTable > tbody")).hasText("Nifty");
//            assertThat(page.locator(".dataTable > tbody")).hasText("Nifty");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (page != null && !page.isClosed()) page.close();
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
        }
    }
}

