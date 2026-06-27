package Functionality;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.microsoft.playwright.options.WaitUntilState;
// import com.microsoft.playwright.options.WaitUntilState; // Not needed for string-based usage

import java.util.Collections;

public class WaitsInPlayWright {
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
            // Navigate and wait for the main content to load
            // Use string "domcontentloaded" for compatibility with different Playwright versions
            page.navigate("https://www.wikipedia.org/",
                new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));

            // Wait explicitly for the language dropdown to be visible before interacting (compatibility-safe)
            page.waitForSelector("#searchLanguage", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
            Locator languageDropdown = page.locator("#searchLanguage");

            // Once visible, interact with the dropdown
            languageDropdown.click();





        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
