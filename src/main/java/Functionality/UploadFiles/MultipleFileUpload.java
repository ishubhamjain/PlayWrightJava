package Functionality.UploadFiles;

import com.microsoft.playwright.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class MultipleFileUpload {
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
            page.navigate("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml5_input_multiple");

            page.frameLocator("//iframe[@id='iframeResult']").locator("//input[@id='files']").setInputFiles(new Path[] { Paths.get("./src/test/java/resources/files/Oracle_Logo1.png"), Paths.get("./src/test/java/resources/files/Oracle_Logo4.jpeg") , Paths.get("./src/test/java/resources/files/Oracle_Logo3.png") });

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