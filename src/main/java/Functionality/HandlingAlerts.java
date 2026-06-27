package Functionality;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitUntilState;

import java.util.Collections;

public class HandlingAlerts {

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
            page.navigate("https://mail.rediff.com/cgi-bin/login.cgi");
            page.onDialog(dialog -> {
                try {
                    Thread.sleep(Long.parseLong("5000"));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                dialog. accept();
                System.out.println(dialog.message());
            });

            page.locator("[name='proceed']").click();

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (page != null && !page.isClosed()) page.close();
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
        }
    }
}
