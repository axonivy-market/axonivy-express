package com.axonivy.utils.axonivyexpress.test.webtest;

import static com.codeborne.selenide.Selenide.open;

import org.junit.jupiter.api.AfterEach;

import com.axonivy.ivy.webtest.engine.EngineUrl;
import com.axonivy.ivy.webtest.engine.WebAppFixture;
import com.axonivy.utils.axonivyexpress.test.page.ExpressManagementPage;

public abstract class BaseTest {

  @AfterEach
  public void clean() {
    open(EngineUrl.createProcessUrl(
        "/axonivy-express-test/1943EF9CC7A1B392/cleanData.ivp"));
  }

  protected ExpressManagementPage navigateToExpressManagementPage() {
    open(EngineUrl.createProcessUrl(
        "/axonivy-express/17326FC2F133FBEA/expressManagement.ivp"));
    return new ExpressManagementPage();
  }

  protected void loginAsDeveloper(WebAppFixture fixture) {
    fixture.login("Developer", "Developer");
  }
}
