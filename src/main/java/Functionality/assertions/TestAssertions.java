package Functionality.assertions;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.Collections;

public class TestAssertions {
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
            page.navigate("https://dp2iadqadt03-test.internal.iot.ocs.oraclecloud.com/ecp/ui/",
                    new Page.NavigateOptions().setTimeout(90000));
            page.locator("#idcs-signin-basic-signin-form-username").fill("iot-cloudops_ww_grp@oracle.com");
            page.locator("input[id='idcs-signin-basic-signin-form-password|input']").fill("Welcome1234#");
            page.locator("//oj-button[@id='idcs-signin-basic-signin-form-submit']//div[@class='oj-button-label']").click();

            System.out.println(page.title());

            page.locator("//a[@id='oj-gbu-redwood_ao-brand']").click();
            page.locator("//span[normalize-space()='Add Hardware']").click();
            page.locator("button[aria-label='Start']").click();

            Locator firstOption = page.locator("(//span[contains(.,'Device Type')])[2]");

            firstOption.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
            firstOption.first().click();

            page.locator("//ul[@class='oj-listview-element oj-component-initnode' and @aria-labelledby='onboarding_stepTwo_deviceType-labelled-by']//span").nth(1).click();
            page.locator("//span[contains(@class,'RadioIconBaseTheme_baseTheme__1j9fixc0 RadioIconStyles_radioIconBase')]").nth(3).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Name")).fill("playwright");

            page.locator("//span[@id='deviceConnectivityType_0|hint' and contains(.,'Connectivity Type')]").fill("wifi");

            page.getByText("Wifi").click();
            page.locator("//span[contains(.,'No Authentication')]/parent::label//*[contains(@class,'IconStyle_')]").click();
            page.locator("//span[contains(.,'Continue')]").nth(1).click();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (page != null && !page.isClosed()) page.close();
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
        }
    }
}
