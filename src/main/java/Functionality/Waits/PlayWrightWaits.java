package Functionality.Waits;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class PlayWrightWaits {
    static Playwright playwright;
    static Browser browser;
    static Page page;

    public static void main(String[] args) {
        try {
            playwright = Playwright.create();
            // Set the path to your Chrome executable below
            String chromeExecutablePath = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe"; // <-- Update this path

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
            page.navigate("https://rahulshettyacademy.com/client/");
            page.locator("#userEmail").fill("john.doe33@example.com");
            page.locator("#userPassword").fill("Password123!");
            page.locator("input#login").click();
            page.waitForLoadState(LoadState.NETWORKIDLE);
            List<String> allpage = page.locator(".card-body b").allTextContents();

            System.out.println(allpage.get(0));
            page.pause();

//            for(String value:allpage){
//                System.out.println(value);
//            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            if (page != null && !page.isClosed()) page.close();
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
        }
    }
}
