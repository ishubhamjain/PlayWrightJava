package Functionality.UploadFiles;

import com.microsoft.playwright.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class DownloadFiles {
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
            page.navigate("https://www.selenium.dev/downloads/");

            Download download = page.waitForDownload(()->{
                page.locator("div[class='row justify-content-center px-5 pb-5'] div:nth-child(3) div:nth-child(1) div:nth-child(2) p:nth-child(2) a:nth-child(1)").click();
            });

            download.saveAs(Paths.get("./src/test/java/resources/files/selenium.jar"));

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