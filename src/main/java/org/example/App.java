package org.example;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.nio.file.Paths;
import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
//        Browser browser = playwright.chromium().launch(
//            new BrowserType.LaunchOptions()
//                .setHeadless(false)
//                .setArgs(Arrays.asList("--start-maximized"))
//        );

        Browser browser = playwright.firefox().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(Arrays.asList("--start-maximized"))
        );

        Page page = browser.newPage();
        page.setViewportSize(1420,1080); // Makes the page use full native window size, i.e. full screen
        page.navigate("https://www.google.com");
        //    page.navigate("https://playwright.dev");
        // page.navigate("https://global-dev2.internal.iot.ocs.oraclecloud.com/ecp/ui/#/settings?context=storageMgmt");
        System.out.println(page.title());
        //page.
        page.locator("textarea[name='q']").fill("Naveen AutomationLabs");
        page.click("(//input[@name='btnK' and @value='Google Search'])[2]");
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example.png")));
        System.out.println(Paths.get("example.png").toAbsolutePath());
        browser.close();
        playwright.close();
        System.out.println("script completed");
    }
}
