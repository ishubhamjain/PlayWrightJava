package Functionality.auth;

import com.microsoft.playwright.*;

import java.nio.file.Paths;
import java.util.Collections;

public class BasicAuth {
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
                    new Browser.NewContextOptions()
                        .setViewportSize(null) // null for maximized window
                        .setHttpCredentials("admin", "admin")
            );
            page = context.newPage();
            page.navigate("http://the-internet.herokuapp.com/basic_auth");

//            Response response = page.waitForResponse(
//                    resp -> resp.url().contains("/users")
//                            && resp.status() == 200,
//                    () -> {
//                        page.click("#loadUsers");
//                    });

            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (page != null && !page.isClosed()) page.close();
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
        }
    }
}
