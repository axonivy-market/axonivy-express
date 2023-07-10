package com.axonivy.portal.test;

import static com.codeborne.selenide.Selenide.open;

import org.junit.jupiter.api.Test;

import com.axonivy.ivy.webtest.IvyWebTest;
import com.axonivy.ivy.webtest.engine.EngineUrl;

/**
 * This sample WebTest orchestrates a real browser to verify that your workflow
 * application and especially it's Html Dialogs are running as expected.
 * 
 * <p>
 * The test can either be run
 * <ul>
 * <li>in the Designer IDE ( <code>right click > run as > JUnit Test </code>
 * )</li>
 * <li>or in a Maven continuous integration build pipeline (
 * <code>mvn clean verify</code> )</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Detailed guidance on writing these kind of tests can be found in our <a href=
 * "https://developer.axonivy.com/doc/11.2/concepts/testing/web-testing.html">WebTesting
 * docs</a>
 * </p>
 */
@IvyWebTest(headless = false)
public class ExpressWebIT {

  @Test
  public void fillDialogForm() {
    open(EngineUrl.createProcessUrl("axonivy-express-test/1893F8F05259D636/start.ivp"));
  }

}