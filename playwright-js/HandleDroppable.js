// playwright-js/HandleDroppable.js

const { chromium } = require('playwright');

(async () => {
  const browser = await chromium.launch({
    headless: false,
    args: ['--start-maximized'],
  });
  const context = await browser.newContext({
    viewport: null // Maximized window
  });
  const page = await context.newPage();

  try {
    await page.goto('https://jqueryui.com/droppable/');

    // Access the iframe containing the demo elements
    const frame = page.frameLocator('iframe');
    const draggable = frame.locator('#draggable');
    const droppable = frame.locator('#droppable');

    // Wait for elements to be visible/stable
    await draggable.waitFor({ state: 'visible' });
    await droppable.waitFor({ state: 'visible' });

    // Get bounding boxes for center points
    const dragBox = await draggable.boundingBox();
    const dropBox = await droppable.boundingBox();

    if (dragBox && dropBox) {
      const dragCenter = {
        x: dragBox.x + dragBox.width / 2,
        y: dragBox.y + dragBox.height / 2,
      };
      const dropCenter = {
        x: dropBox.x + dropBox.width / 2,
        y: dropBox.y + dropBox.height / 2,
      };
      // Move mouse to drag source, mouse down, move to drop target, mouse up
      await page.mouse.move(dragCenter.x, dragCenter.y);
      await page.mouse.down();
      await page.mouse.move(dropCenter.x, dropCenter.y);
      await page.mouse.up();
    } else {
      throw new Error('Could not find draggable or droppable box');
    }

    // Wait 2 seconds so result is visible
    await page.waitForTimeout(2000);
  } catch (e) {
    console.error(e);
  } finally {
    await page.close();
    await browser.close();
  }
})();