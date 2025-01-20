package com.axonivy.utils.axonivyexpress.test.webtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.axonivy.ivy.webtest.IvyWebTest;
import com.axonivy.ivy.webtest.engine.WebAppFixture;
import com.axonivy.utils.axonivyexpress.test.page.ExpressManagementPage;
import com.axonivy.utils.axonivyexpress.test.page.WorkflowDefinitionPage;

@IvyWebTest
public class ExpressManagementTest extends BaseTest {

  @BeforeEach
  public void setup(WebAppFixture fixture) {
    fixture.login("express", "express");
    navigateToExpressManagementPage();
    ExpressManagementPage page = new ExpressManagementPage();
    page.uploadExpressJsonFile("express-test.json");
  }

  @Test
  public void testImportProcess() {
    ExpressManagementPage page = new ExpressManagementPage();
    assertTrue(page.hasExpressProcessWithName("Express Test 1"));
  }

  @Test
  public void testDeleteProcess() {
    ExpressManagementPage page = new ExpressManagementPage();
    page.delete(0);
    assertEquals(2, page.countRows());
  }

  @Test
  public void testEditProcess() {
    ExpressManagementPage page = new ExpressManagementPage();
    WorkflowDefinitionPage definitionPage = page.edit(1);
    definitionPage.changeName("Test Name");
    page = definitionPage.proceedToFormDefinitionPage().clickFinish();
    assertTrue(page.hasExpressProcessWithName("Test Name"));
  }
}
