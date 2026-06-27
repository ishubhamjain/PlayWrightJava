// playwright-js/HandleResizeable.js
// Reimplementation of Java HandleResizeable using Playwright JS

const { chromium } = require('playwright');

(async () => {
  const browser = await chromium.launch({
    headless: false,
    args: ['--start-maximized']
  });
  // 'null' disables the viewport size limit, so window is maximized
  const context = await browser.newContext({ viewport: null });
  const page = await context.newPage();
  try {
    await page.goto('https://jqueryui.com/resizable/');

    // Wait and get the iframe.
    const frameHandle = await page.waitForSelector('iframe');
    const frame = await frameHandle.contentFrame();

    // Wait for resizable handle in iframe
    // The handle on the east (right) side usually has class ".ui-resizable-e"
    const slider = await frame.waitForSelector('.ui-resizable-handle.ui-resizable-e', { state: 'visible' });

    // Get bounding box to determine position
    const box = await slider.boundingBox();

    if (box) {
      // Move mouse to center of the resizable handle
      await page.mouse.move(
        frameHandle.boundingBox().then(fbox =>
          fbox
            ? fbox.x + box.x + box.width / 2
            : box.x + box.width / 2,
        ),
        frameHandle.boundingBox().then(fbox =>
          fbox
            ? fbox.y + box.y + box.height / 2
            : box.y + box.height / 2,
        )
      );
      await page.mouse.down();
      // Drag horizontally by +100px (rightwards), similar to the Java version
      await page.mouse.move(
        frameHandle.boundingBox().then(fbox =>
          fbox
            ? fbox.x + box.x + box.width / 2 + 100
            : box.x + box.width / 2 + 100,
        ),
        frameHandle.boundingBox().then(fbox =>
          fbox
            ? fbox.y + box.y + box.height / 2
            : box.y + box.height / 2,
        ),
        { steps: 10 }
      );
      await page.mouse.up();
    } else {
      console.error('Could not find the resizable handle bounding box.');
    }

    await page.waitForTimeout(2000); // Wait for visual verification
  } catch (e) {
    console.error(e);
  } finally {
    await browser.close();
  }
})();