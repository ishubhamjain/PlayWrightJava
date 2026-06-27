package Launches;

import com.microsoft.playwright.*;

public class PlayWrightExecuteWIthFireFox {

    static Playwright playwright;
    static Browser browser;
    static Page page;

    public static void main(String[] args) {
        try {
            playwright = Playwright.create();
            browser = playwright.firefox().launch(
                    new BrowserType.LaunchOptions()
                        .setHeadless(false)
            );

            // Get the screen size using java.awt
            java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

            BrowserContext context = browser.newContext(
                    new Browser.NewContextOptions()
                        .setViewportSize((int) screenSize.getWidth(), (int) screenSize.getHeight())
            );
            page = context.newPage();
            page.navigate("https://www.way2automation.com/");
            Thread.sleep(3000);
            System.out.println(page.title());
        } catch (PlaywrightException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (page != null && !page.isClosed()) page.close();
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
        }
    }
}
