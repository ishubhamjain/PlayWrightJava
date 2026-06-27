package Functionality.ScreenshotVideoTraceViewer;

import com.microsoft.playwright.*;

import java.nio.file.Paths;
import java.util.Collections;

public class TraceViewer {
    static Playwright playwright;
    static Browser browser;
    static Page page;

    // source: https://playwright.dev/docs/trace-viewer
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
            );
            // Start tracing before creating / navigating a page.
            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));
            page = context.newPage();
            page.navigate("http://the-internet.herokuapp.com/");
            page.locator("a[href='/checkboxes']").click();
            Thread.sleep(3000);
            // Stop tracing and export it into a zip archive.
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get("trace.zip")));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (page != null && !page.isClosed()) page.close();
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
        }
    }
}
