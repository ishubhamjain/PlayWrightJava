package Functionality;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitUntilState;

import java.nio.file.Paths;

/**
 * Demonstrates launching Google Chrome with the main (non-incognito) user profile using Playwright for Java.
 * <p>
 * This class starts Chrome using the system's default profile directory (persistent context), allowing access to:
 * <ul>
 *   <li>Browser session data such as history, bookmarks, and cookies.</li>
 *   <li>Personalized user experience as in a normal Chrome session (not incognito).</li>
 *   <li>Sites that require authentication without a new login (if available in the profile).</li>
 * </ul>
 * <p>
 * The <b>chromeExecutablePath</b> variable is set to the default Chrome binary on macOS.
 * You can adjust this path for other operating systems.
 * <p>
 * Note:
 * <ul>
 *   <li>Persistence context uses the browsing data of the main Chrome profile, so history and other session data will be visible and updated.</li>
 *   <li>This class can be extended to perform automation or validation leveraging an existing session.</li>
 * </ul>
 * 
 * Usage Example:
 * <pre>
 *   java NonIncognitoTest
 * </pre>
 * 
 * Prerequisites:
 * <ul>
 *   <li>Google Chrome installed at the specified path.</li>
 *   <li>Playwright dependencies in your project.</li>
 * </ul>
 */
public class NavigationClass {

    // The main Playwright instance
    static Playwright playwright;
    // Browser object is available if needed for non-persistent context
    static Browser browser;
    // The Page object for automation/navigation
    static Page page;

    // Absolute path to Chrome executable (for macOS by default). Change for your OS if needed.
    static String chromeExecutablePath = "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome";

    /**
     * Entry point: launches Chrome using the default user profile (non-incognito persistent context)
     * and navigates to Google.
     *
     * Browsing session is fully persistent—existing browsing history, logged in users, bookmarks etc.
     * will be available as in a regular session.
     *
     * Any navigation or automation here will reflect in the user's main Chrome profile.
     */
    public static void main(String[] args) {
        try {
            playwright = Playwright.create();

            // Creating a persistent browser context that uses Chrome's user data
            // This will give access to all personal browser data, history, extensions, cookies, and more.
            BrowserContext browserContext = playwright.chromium().launchPersistentContext(
                    Paths.get(""), // Empty path uses the system/user's default profile directory
                    new BrowserType.LaunchPersistentContextOptions()
                            .setHeadless(false) // Set to false to render the browser window
                            .setExecutablePath(Paths.get(chromeExecutablePath)) // Absolute path to Chrome binary
            );

            page = browserContext.newPage();
            page.navigate("https://www.google.com");
            // Prints the page title to stdout
            System.out.println(page.title());

            page.navigate("https://in.search.yahoo.com/");
            System.out.println(page.title());

           // page.goBack()
          //  page.goBack();
          //  page.goBack(new Page.GoBackOptions().setTimeout(5000));
            page.goBack(new Page.GoBackOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
            Thread.sleep(4000);
         //   page.goForward();
            page.goForward(new Page.GoForwardOptions().setTimeout(5000));
            Thread.sleep(4000);
            page.reload();

        } catch (Exception e) {
            // Re-throws as unchecked for visibility in calling environments
            throw new RuntimeException(e);
        } finally {
            if (page != null && !page.isClosed()) page.close();
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
        }
    }
}
