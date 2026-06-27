package Functionality;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.SelectOption;

import java.util.Collections;
import java.util.List;

public class TestHandlingDropDown {

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
            page.setViewportSize(1280,720);
            page.navigate("https://www.wikipedia.org/");
            page.selectOption("select", "hi");
            //   page.selectOption("select","Eesti");
            Thread.sleep(2000);
            page.selectOption("select", new SelectOption().setLabel("Eesti"));
            Thread.sleep(2000);
            page.selectOption("select", new SelectOption().setIndex(3));
            Thread.sleep(2000);

            Locator locator = page.locator("//select/option");
            System.out.println(locator.count());


            for (int i = 0; i < locator.count(); i++) {
                System.out.println("Inner text = "+locator.nth(i).innerText());
                System.out.println(locator.nth(i).getAttribute("lang"));
            }

            List<ElementHandle> queries =  page.querySelectorAll("//select/option");
            System.out.println("size of list is :"+queries.size());

            for(ElementHandle query:queries){
                System.out.println("Inner text = "+query.innerText());
                System.out.println(query.getAttribute("lang"));
            }

        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
//            if (page != null && !page.isClosed()) page.close();
//            if (browser != null) browser.close();
//            if (playwright != null) playwright.close();
        }
    }
}
