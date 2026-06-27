package Functionality;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.util.*;
public class AnalysingAutoCodeGeneration {
        public static void main(String[] args) {
            try (Playwright playwright = Playwright.create()) {
                Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(false));
                BrowserContext context = browser.newContext();
                Page page = context.newPage();
                page.navigate("https://www.wikipedia.org/");

               // page.wait(3000);
                page.getByRole(AriaRole.SEARCHBOX, new Page.GetByRoleOptions().setName("Search Wikipedia")).click();
                // page.wait(3000);
                page.getByRole(AriaRole.SEARCHBOX, new Page.GetByRoleOptions().setName("Search Wikipedia")).fill("software test");
                Thread.sleep(3000);
                page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Software testing Checking")).click();
                Thread.sleep(3000);
                page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("History").setExact(true)).click();
                Thread.sleep(3000);
                page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Main menu")).check();

                Thread.sleep(3000);
                page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Main page")).click();
                page.waitForURL("https://en.wikipedia.org/wiki/Main_Page");
               // page.wai
                Thread.sleep(3000);
                page.getByText("John Tonkin").click();
                Thread.sleep(3000);
                page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Donate")).click();
                page.locator("#input_amount_other_box").click();
                Thread.sleep(3000);
                page.locator("#input_amount_other_box").fill("800");
                Thread.sleep(3000);
                page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("I'll generously add ₹32 to")).check();
                Thread.sleep(3000);


            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

