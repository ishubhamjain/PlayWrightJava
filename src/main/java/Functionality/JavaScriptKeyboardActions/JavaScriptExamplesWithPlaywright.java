package Functionality.JavaScriptKeyboardActions;

import com.microsoft.playwright.*;

import java.util.Collections;

public class JavaScriptExamplesWithPlaywright {
   // refer codes: https://playwright.dev/java/docs/evaluating
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
            page.navigate("https://www.google.com/");
            String href = (String) page.evaluate("document.location.href");
            System.out.println(href);

            page.evaluate("() => {"
                    + "const textarea = document.createElement('textarea');"
                    + "document.body.append(textarea);"
                    + "textarea.focus();"
                    +"}");

            String text = "Hello World !!";
            page.keyboard().type(text);

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
