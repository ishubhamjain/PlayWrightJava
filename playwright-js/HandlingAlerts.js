// playwright-js/HandlingAlerts.js

const { chromium } = require('playwright');

(async () => {
  const browser = await chromium.launch({
    headless: false,
    args: ['--start-maximized'],
  });
  const context = await browser.newContext({
    viewport: null // maximized
  });
  const page = await context.newPage();

  try {
    await page.goto('https://mail.rediff.com/cgi-bin/login.cgi');
    page.on('dialog', async (dialog) => {
      // Simulating a 5s wait (not generally recommended, but matches Java logic)
      await new Promise(resolve => setTimeout(resolve, 5000));
      await dialog.accept();
      console.log(dialog.message());
    });
    await page.locator("[name='proceed']").click();
  } catch (e) {
    console.error(e);
  } finally {
    await page.close();
    await browser.close();
  }
})();